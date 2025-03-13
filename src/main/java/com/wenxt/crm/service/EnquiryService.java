package com.wenxt.crm.service;

import com.wenxt.crm.model.EnquiryModel;
import com.wenxt.crm.model.LeadModel;
import com.wenxt.crm.repository.EnquiryRepository;
import com.wenxt.crm.repository.LeadRepository;
import com.wenxt.crm.util.RemainderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EnquiryService {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private RemainderUtility remainderUtility;

    private Map<String, Object> buildResponse(String status, String message, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public ResponseEntity<Map<String, Object>> getAllEnquiries(
            String status, Boolean last7days, Boolean last30days, Boolean lastyear,
            LocalDate startDate, LocalDate endDate, Integer page, Integer size) {

        Date start = null;
        Date end = new Date();
        Calendar calendar = Calendar.getInstance();

        try {
            if (last7days != null && last7days) {
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                start = calendar.getTime();
            } else if (last30days != null && last30days) {
                calendar.add(Calendar.DAY_OF_YEAR, -30);
                start = calendar.getTime();
            } else if (lastyear != null && lastyear) {
                calendar.add(Calendar.YEAR, -1);
                start = calendar.getTime();
            } else if (startDate != null && endDate != null) {
                start = java.sql.Date.valueOf(startDate);
                end = java.sql.Date.valueOf(endDate);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "Invalid date format", null));
        }

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "enqDate"));
            Page<EnquiryModel> filteredEnquiries;

            if (status != null && start != null) {
                filteredEnquiries = enquiryRepository.findByEnqStatusAndEnqDateBetween(status, start, end, pageable);
            } else if (status != null) {
                filteredEnquiries = enquiryRepository.findByEnqStatus(status, pageable);
            } else if (start != null) {
                filteredEnquiries = enquiryRepository.findByEnqDateBetween(start, end, pageable);
            } else {
                filteredEnquiries = enquiryRepository.findAll(pageable);
            }

            Map<String, Object> pagination = new LinkedHashMap<>();
            pagination.put("currentPage", filteredEnquiries.getNumber());
            pagination.put("pageSize", filteredEnquiries.getSize());
            pagination.put("totalPages", filteredEnquiries.getTotalPages());
            pagination.put("totalRecords", filteredEnquiries.getTotalElements());
            pagination.put("isFirstPage", filteredEnquiries.isFirst());
            pagination.put("isLastPage", filteredEnquiries.isLast());
            pagination.put("hasNext", filteredEnquiries.hasNext());
            pagination.put("hasPrevious", filteredEnquiries.hasPrevious());

            Map<String, Object> response = buildResponse("success", "Records fetched successfully", filteredEnquiries.getContent());
            response.put("pagination", pagination);

            return ResponseEntity.ok(response);
        } else {
            List<EnquiryModel> filteredEnquiries;

            if (status != null && start != null) {
                filteredEnquiries = enquiryRepository.findByEnqStatusAndEnqDateBetween(status, start, end);
            } else if (status != null) {
                filteredEnquiries = enquiryRepository.findByEnqStatus(status);
            } else if (start != null) {
                filteredEnquiries = enquiryRepository.findByEnqDateBetween(start, end);
            } else {
                filteredEnquiries = enquiryRepository.findAll();
            }

            return ResponseEntity.ok(buildResponse("success", "Records fetched successfully", filteredEnquiries));
        }
    }

    public ResponseEntity<Map<String, Object>> createEnquiry(EnquiryModel enquiry) {
        try {
            LeadModel lead = leadRepository.findById(enquiry.getLead().getLeadSeqNo())
                    .orElseThrow(() -> new RuntimeException("Lead not found"));

            enquiry.setEnqName(lead.getLeadName());

            EnquiryModel savedEnquiry = enquiryRepository.save(enquiry);

            remainderUtility.sendRemainderAndLog(
                    "ENQUIRY_CREATED",
                    "Enquiry created: " + savedEnquiry.getEnqName(),
                    null
            );

            return ResponseEntity.ok(buildResponse("success", "Enquiry created successfully", savedEnquiry));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "Failed to create enquiry: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<Map<String, Object>> getEnquiryById(Integer id) {
        return enquiryRepository.findById(id)
        		//System.out.println("ID: " + id);
                .map(enquiry -> ResponseEntity.ok(buildResponse("success", "Record fetched successfully", enquiry)))
                .orElseGet(() -> ResponseEntity.status(404).body(buildResponse("error", "Enquiry not found", null)));
    }

    public ResponseEntity<Map<String, Object>> updateEnquiry(Integer id, EnquiryModel enquiry) {
        try {
            if (enquiryRepository.existsById(id)) {
                enquiry.setEnqSeqNo(id);
                EnquiryModel updatedEnquiry = enquiryRepository.save(enquiry);

                remainderUtility.sendRemainderAndLog(
                        "ENQUIRY_UPDATED",
                        "Enquiry updated: " + updatedEnquiry.getEnqName(),
                        null
                );

                return ResponseEntity.ok(buildResponse("success", "Record updated successfully", updatedEnquiry));
            } else {
                return ResponseEntity.status(404).body(buildResponse("error", "Enquiry not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "Failed to update enquiry: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<Map<String, Object>> updateEnquiryStatus(
            Integer id, String status, String enqDescription) {

        try {
            EnquiryModel enquiry = enquiryRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Enquiry not found"));

            if (!"accEnq".equals(status) && !"rejEnq".equals(status)) {
                return ResponseEntity.badRequest()
                        .body(buildResponse("error", "Invalid status value. Allowed values: accEnq, rejEnq", null));
            }

            enquiry.setEnqStatus(status);

            if ("accEnq".equals(status)) {
                enquiry.setEnqDescription(enqDescription);
            } else {
                enquiry.setEnqDescription(null);
            }

            enquiryRepository.save(enquiry);

            remainderUtility.sendRemainderAndLog(
                    "ENQUIRY_STATUS_UPDATED",
                    "Enquiry status updated: " + enquiry.getEnqName() + " to " + status,
                    null
            );

            return ResponseEntity.ok(buildResponse("success", "Enquiry status updated successfully", enquiry));

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(buildResponse("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(buildResponse("error", "Failed to update enquiry status: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<Map<String, Object>> updateEnquiryFields(
            Integer id, String leadComments, String salesComments, String enqStatus) {
        try {
            Optional<EnquiryModel> optionalEnquiry = enquiryRepository.findById(id);

            if (optionalEnquiry.isPresent()) {
                EnquiryModel enquiry = optionalEnquiry.get();

                if (leadComments != null) {
                    enquiry.setLeadComments(leadComments);
                }
                if (salesComments != null) {
                    enquiry.setSalesComments(salesComments);
                }
                if (enqStatus != null) {
                    enquiry.setEnqStatus(enqStatus);
                }

                enquiryRepository.save(enquiry);

                remainderUtility.sendRemainderAndLog(
                        "ENQUIRY_FIELDS_UPDATED",
                        "Enquiry fields updated: " + enquiry.getEnqName(),
                        null
                );

                return ResponseEntity.ok(buildResponse("success", "Enquiry fields updated successfully", enquiry));
            } else {
                return ResponseEntity.status(404).body(buildResponse("error", "Enquiry not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "Failed to update enquiry fields: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<Map<String, Object>> deleteEnquiry(Integer id) {
        try {
            if (enquiryRepository.existsById(id)) {
                EnquiryModel enquiry = enquiryRepository.findById(id).orElse(null);
                enquiryRepository.deleteById(id);

                if (enquiry != null) {
                    remainderUtility.sendRemainderAndLog(
                            "ENQUIRY_DELETED",
                            "Enquiry deleted: " + enquiry.getEnqName(),
                            null
                    );
                }

                return ResponseEntity.ok(buildResponse("success", "Enquiry deleted successfully", null));
            } else {
                return ResponseEntity.status(404).body(buildResponse("error", "Enquiry not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "Failed to delete enquiry: " + e.getMessage(), null));
        }
    }
}