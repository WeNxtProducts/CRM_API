package com.wenxt.crm.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wenxt.crm.model.EnquiryModel;
import com.wenxt.crm.service.EnquiryService;

@RestController
@RequestMapping("/api/enquiries")
@CrossOrigin(origins = "http://localhost:8081") // Allow only specific origin
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    @GetMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAllEnquiries(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "last7days", required = false) Boolean last7days,
            @RequestParam(value = "last30days", required = false) Boolean last30days,
            @RequestParam(value = "lastyear", required = false) Boolean lastyear,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
    	
    	// Validate pagination parameters
        if ((page != null && size == null) || (page == null && size != null)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Both 'page' and 'size' must be provided together for pagination."
            ));
        }

        return enquiryService.getAllEnquiries(status, last7days, last30days, lastyear, startDate, endDate, page, size);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createEnquiry(@RequestBody EnquiryModel enquiry) {
        return enquiryService.createEnquiry(enquiry);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getEnquiryById(@PathVariable Integer id) {
        return enquiryService.getEnquiryById(id);
    }
    
//    @GetMapping("/stats/{enqSeqNo}")
//    public ResponseEntity<Map<String, Object>> getEnquiryStats(@PathVariable Integer enqSeqNo) {
//        return enquiryService.getEnquiryStats(enqSeqNo);
//    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Map<String, Object>> updateEnquiry(@PathVariable Integer id, @RequestBody EnquiryModel enquiry) {
        return enquiryService.updateEnquiry(id, enquiry);
    }
    
    @PutMapping(value = "/updateStatus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateEnquiryStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) { // Accept JSON body

        // Extract status and description from the request body
        String status = requestBody.get("status");
        String enqDescription = requestBody.get("enqDescription");

        return enquiryService.updateEnquiryStatus(id, status, enqDescription);
    }

    
    @PutMapping("/updateFields/{id}")
    public ResponseEntity<Map<String, Object>> updateEnquiryFields(
            @PathVariable Integer id,
            @RequestParam(required = false) String leadComments,
            @RequestParam(required = false) String salesComments,
            @RequestParam(required = false) String enqStatus) {

        return enquiryService.updateEnquiryFields(id, leadComments, salesComments, enqStatus);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteEnquiry(@PathVariable Integer id) {
        return enquiryService.deleteEnquiry(id);
    }
}