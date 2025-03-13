package com.wenxt.crm.controller;

//import com.wenxt.crm.model.EnquiryModel;
import com.wenxt.crm.model.LeadModel;
import com.wenxt.crm.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    @Autowired
    private LeadService leadService;
    
    @GetMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAllLeads(
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

        return leadService.getAllLeads(status, last7days, last30days, lastyear, startDate, endDate, page, size);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createLead(@RequestBody LeadModel lead) {
        return leadService.createLead(lead);
    }
    

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getLeadById(@PathVariable Integer id) {
        return leadService.getLeadById(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Map<String, Object>> updateLead(@PathVariable Integer id, @RequestBody LeadModel lead) {
        return leadService.updateLead(id, lead);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteLead(@PathVariable Integer id) {
        return leadService.deleteLead(id);
    }
    
    @PostMapping("/increment-remainder")
    public ResponseEntity<Map<String, Object>> incrementRemainderForContact(
            @RequestBody Map<String, Object> requestBody) {
        
        // Extract values from the request body
        Integer leadSeqNo = (Integer) requestBody.get("leadSeqNo");
        String leadSource = (String) requestBody.get("leadSource");
        String leadDescription = (String) requestBody.get("leadDescription"); // Updated to String

        // Call the service method
        return leadService.incrementRemainderForContact(leadSeqNo, leadSource, leadDescription);
    }
}