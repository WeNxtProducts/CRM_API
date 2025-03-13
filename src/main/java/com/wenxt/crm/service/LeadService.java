package com.wenxt.crm.service;

import com.wenxt.crm.model.LeadModel;
import com.wenxt.crm.repository.LeadRepository;
import com.wenxt.crm.util.RemainderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Service
public class LeadService {

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

    public ResponseEntity<Map<String, Object>> getAllLeads(
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

        // Handle pagination
        if (page != null && size != null) {
            // Paginated response
            Pageable pageable = PageRequest.of(page, size, (Sort.by(Sort.Direction.DESC, "leadCreatedDate")));
            Page<LeadModel> filteredLeads;

            if (status != null && start != null) {
                filteredLeads = leadRepository.findByLeadStatusAndLeadCreatedDateBetween(status, start, end, pageable);
            } else if (status != null) {
                filteredLeads = leadRepository.findByLeadStatus(status, pageable);
            } else if (start != null) {
                filteredLeads = leadRepository.findByLeadCreatedDateBetween(start, end, pageable);
            } else {
                filteredLeads = leadRepository.findAll(pageable);
            }

            Map<String, Object> pagination = new LinkedHashMap<>();
            pagination.put("currentPage", filteredLeads.getNumber());
            pagination.put("pageSize", filteredLeads.getSize());
            pagination.put("totalPages", filteredLeads.getTotalPages());
            pagination.put("totalRecords", filteredLeads.getTotalElements());
            pagination.put("isFirstPage", filteredLeads.isFirst());
            pagination.put("isLastPage", filteredLeads.isLast());
            pagination.put("hasNext", filteredLeads.hasNext());
            pagination.put("hasPrevious", filteredLeads.hasPrevious());

            Map<String, Object> response = buildResponse("success", "Records fetched successfully", filteredLeads.getContent());
            response.put("pagination", pagination);

            return ResponseEntity.ok(response);
        } else {
            // Non-paginated response
            List<LeadModel> filteredLeads;

            if (status != null && start != null) {
                filteredLeads = leadRepository.findByLeadStatusAndLeadCreatedDateBetween(status, start, end);
            } else if (status != null) {
                filteredLeads = leadRepository.findByLeadStatus(status);
            } else if (start != null) {
                filteredLeads = leadRepository.findByLeadCreatedDateBetween(start, end);
            } else {
                filteredLeads = leadRepository.findAll(Sort.by(Sort.Direction.DESC, "leadCreatedDate"));
            }

            return ResponseEntity.ok(buildResponse("success", "Records fetched successfully", filteredLeads));
        }
    }

    public ResponseEntity<Map<String, Object>> createLead(LeadModel lead) {
        try {
            LeadModel savedLead = leadRepository.save(lead);

            // Log the lead creation
            remainderUtility.sendRemainderAndLog(
                    "LEAD_CREATED",
                    "Lead created: " + lead.getLeadName(),
                    null // No remainder for lead creation unless specified
            );

            return ResponseEntity.ok(buildResponse("success", "Lead created successfully", savedLead));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "Failed to create lead: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<Map<String, Object>> getLeadById(Integer id) {
        return leadRepository.findById(id)
                .map(lead -> ResponseEntity.ok(buildResponse("success", "Record fetched successfully", lead)))
                .orElseGet(() -> ResponseEntity.status(404).body(buildResponse("error", "Lead not found", null)));
    }

    public ResponseEntity<Map<String, Object>> updateLead(Integer id, LeadModel lead) {
        try {
            // Check if the lead with the given ID exists
            return leadRepository.findById(id)
                    .map(existingLead -> {
                        // Update the existing lead with the new values
                        existingLead.setLeadRefId(lead.getLeadRefId());
                        existingLead.setLeadFlag(lead.getLeadFlag());
                        existingLead.setLeadName(lead.getLeadName());
                        existingLead.setLeadFirstName(lead.getLeadFirstName());
                        existingLead.setLeadLastName(lead.getLeadLastName());
                        existingLead.setLeadEmail(lead.getLeadEmail());
                        existingLead.setLeadPhoneNo(lead.getLeadPhoneNo());
                        existingLead.setLeadMobileNo(lead.getLeadMobileNo());
                        existingLead.setLeadAddrLine1(lead.getLeadAddrLine1());
                        existingLead.setLeadAddrLine2(lead.getLeadAddrLine2());
                        existingLead.setLeadCountry(lead.getLeadCountry());
                        existingLead.setLeadType(lead.getLeadType());
                        existingLead.setLeadStatus(lead.getLeadStatus());
                        existingLead.setLeadPriority(lead.getLeadPriority());
                        existingLead.setLeadSource(lead.getLeadSource());
                        existingLead.setLeadAssignedBy(lead.getLeadAssignedBy());
                        existingLead.setLeadAssignedTo(lead.getLeadAssignedTo());
                        existingLead.setLeadUpdatedBy(lead.getLeadUpdatedBy());
                        existingLead.setLeadDescription(lead.getLeadDescription());
                        existingLead.setLeadComments(lead.getLeadComments());
                        existingLead.setLeadInternalNotes(lead.getLeadInternalNotes());

                        // Ensure leadCreatedDate is not overwritten
                        existingLead.setLeadCreatedDate(existingLead.getLeadCreatedDate());

                        // Save the updated lead
                        LeadModel updatedLead = leadRepository.save(existingLead);

                        // Log the lead update
                        remainderUtility.sendRemainderAndLog(
                                "LEAD_UPDATED",
                                "Lead updated: " + updatedLead.getLeadName(),
                                null // No remainder for lead update unless specified
                        );

                        return ResponseEntity.ok(buildResponse("success", "Record updated successfully", updatedLead));
                    })
                    .orElseGet(() -> ResponseEntity.status(404).body(buildResponse("error", "Lead not found", null)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "Failed to update lead: " + e.getMessage(), null));
        }
    }

    public ResponseEntity<Map<String, Object>> incrementRemainderForContact(
            Integer leadSeqNo, String leadSource, String leadDescription) { // Updated to String
    	

        try {
        	
            return leadRepository.findById(leadSeqNo)
            		
                    .map(lead -> {
                    	
                    	// Store the originally saved leadSource
                        @SuppressWarnings("unused")
						String originalLeadSource = lead.getLeadSource();

                        // Update leadSource with the provided value
                        lead.setLeadSource(leadSource);
                    	System.out.println(leadSource);
                    	
                    	 // Define allowed lead sources
                        Set<String> validLeadSources = Set.of("mail", "phone", "whatsapp");
                        

                        // Validate leadSource from request
                        if (leadSource == null || !validLeadSources.contains(leadSource.toLowerCase())) {
                            return ResponseEntity.badRequest().body(
                                    buildResponse("error", "Invalid leadSource provided", null)
                            );
                        }

                        // **STRICTLY CHECK** if the lead's stored leadSource matches the request
                        if (!lead.getLeadSource().equalsIgnoreCase(leadSource)) {
                            return ResponseEntity.badRequest().body(
                                    buildResponse("error", "Lead source mismatch. Update not allowed!", null)
                            );
                        }

                        // Increment remainderCount
                        lead.setRemainderCount(lead.getRemainderCount() == null ? 1 : lead.getRemainderCount() + 1);

                        // Update leadDescription if provided
                        if (leadDescription != null) {
                            lead.setLeadDescription(leadDescription); // Directly set the string
                        }

                        // Save lead
                        LeadModel updatedLead = leadRepository.save(lead);

                        // Build response
                        return ResponseEntity.ok(
                                buildResponse("success", "Remainder incremented successfully", updatedLead)
                        );
                    })
                    .orElseGet(() -> ResponseEntity.status(404).body(
                            buildResponse("error", "Lead not found", null)
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    buildResponse("error", "Failed to increment remainder: " + e.getMessage(), null)
            );
        }
    }

   

    public ResponseEntity<Map<String, Object>> deleteLead(Integer id) {
        try {
            if (leadRepository.existsById(id)) {
                LeadModel lead = leadRepository.findById(id).orElse(null);
                leadRepository.deleteById(id);

                // Log the lead deletion
                if (lead != null) {
                    remainderUtility.sendRemainderAndLog(
                            "LEAD_DELETED",
                            "Lead deleted: " + lead.getLeadName(),
                            null // No remainder for lead deletion unless specified
                    );
                }

                return ResponseEntity.ok(buildResponse("success", "Lead deleted successfully", null));
            } else {
                return ResponseEntity.status(404).body(buildResponse("error", "Lead not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "Failed to delete lead: " + e.getMessage(), null));
        }
    }
}