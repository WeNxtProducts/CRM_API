package com.wenxt.crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;
//import java.util.List;

//import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "WN_LEAD")
public class LeadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEAD_SEQ_NO")
    private Integer leadSeqNo;

    @Column(name = "LEAD_REF_ID", unique = true, nullable = false, length = 50)
    private String leadRefId;

    @Column(name = "LEAD_FLAG")
    private String leadFlag; // 'e' for existing customer, 'l' for new lead

    @Column(name = "LEAD_NAME", length = 50)
    private String leadName;
//    
//    @OneToOne(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true)
//    private EnquiryModel enquiry;

    @Column(name = "LEAD_FIRST_NAME", length = 50)
    private String leadFirstName;

    @Column(name = "LEAD_LAST_NAME", length = 50)
    private String leadLastName;

    @Email(message = "Invalid email format")
    @Column(name = "LEAD_EMAIL", length = 255)
    private String leadEmail;

    @Size(max = 50, message = "Phone number must be at most 50 characters")
    @Column(name = "LEAD_PHONE_NO", length = 50)
    private String leadPhoneNo;

    @Size(max = 50, message = "Mobile number must be at most 50 characters")
    @Column(name = "LEAD_MOBILE_NO", length = 50)
    private String leadMobileNo;

    @Column(name = "LEAD_ADDR_LINE1", length = 200)
    private String leadAddrLine1;

    @Column(name = "LEAD_ADDR_LINE2", length = 200)
    private String leadAddrLine2;

    @Column(name = "LEAD_COUNTRY", length = 100)
    private String leadCountry;

    @Column(name = "LEAD_TYPE", length = 50)
    private String leadType;

    @Column(name = "LEAD_STATUS", length = 20)
    private String leadStatus = "Todo";

    @Column(name = "LEAD_PRIORITY", length = 20)
    private String leadPriority;

    @Column(name = "LEAD_SOURCE")
    private String leadSource;

    @Column(name = "REMAINDER_COUNT")
    private Integer remainderCount = 0;

    @Column(name = "LEAD_ASSIGNED_BY", length = 50)
    private String leadAssignedBy;

    @Column(name = "LEAD_ASSIGNED_TO", length = 50)
    private String leadAssignedTo;

    @Column(name = "LEAD_CREATED_BY", length = 50)
    private String leadCreatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LEAD_CREATED_DATE")
    private Date leadCreatedDate;

    @Column(name = "LEAD_UPDATED_BY", length = 50)
    private String leadUpdatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LEAD_UPDATED_DATE")
    private Date leadUpdatedDate; 

    @Lob
    @Column(name = "LEAD_DESCRIPTION")
    private String leadDescription;

    @Lob
    @Column(name = "LEAD_COMMENTS")
    private String leadComments;

    @Lob
    @Column(name = "LEAD_INTERNAL_NOTES")
    private String leadInternalNotes;

//    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<AppointmentsModel> appointments;

    // Auto-set-timestamps
    @PrePersist
    public void prePersist() {
        this.leadCreatedDate = new Date();
        this.leadUpdatedDate = new Date();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.leadUpdatedDate = new Date();
    }

    @PostPersist
    public void postPersist() {
        if (this.leadRefId == null) {
            this.leadRefId = "LEAD-" + String.format("%05d", this.leadSeqNo);
        }
    }

   

    // Default Constructor
    public LeadModel() {
        this.remainderCount = 0; // Default to 0
    }

    // Getters and Setters   
    
    public LeadModel(Integer leadSeqNo, String leadRefId, String leadFlag, String leadName, String leadFirstName,
			String leadLastName, @Email(message = "Invalid email format") String leadEmail,
			@Size(max = 50, message = "Phone number must be at most 50 characters") String leadPhoneNo,
			@Size(max = 50, message = "Mobile number must be at most 50 characters") String leadMobileNo,
			String leadAddrLine1, String leadAddrLine2, String leadCountry, String leadType, String leadStatus,
			String leadPriority, String leadSource, Integer remainderCount, String leadAssignedBy, String leadAssignedTo,
			String leadCreatedBy, Date leadCreatedDate, String leadUpdatedBy, Date leadUpdatedDate,
			String leadDescription, String leadComments, String leadInternalNotes) {
		super();
		this.leadSeqNo = leadSeqNo;
		this.leadRefId = leadRefId;
		this.leadFlag = leadFlag;
		this.leadName = leadName;
		this.leadFirstName = leadFirstName;
		this.leadLastName = leadLastName;
		this.leadEmail = leadEmail;
		this.leadPhoneNo = leadPhoneNo;
		this.leadMobileNo = leadMobileNo;
		this.leadAddrLine1 = leadAddrLine1;
		this.leadAddrLine2 = leadAddrLine2;
		this.leadCountry = leadCountry;
		this.leadType = leadType;
		this.leadStatus = leadStatus;
		this.leadPriority = leadPriority;
		this.leadSource = leadSource;
		this.remainderCount = remainderCount;
		this.leadAssignedBy = leadAssignedBy;
		this.leadAssignedTo = leadAssignedTo;
		this.leadCreatedBy = leadCreatedBy;
		this.leadCreatedDate = leadCreatedDate;
		this.leadUpdatedBy = leadUpdatedBy;
		this.leadUpdatedDate = leadUpdatedDate;
		this.leadDescription = leadDescription;
		this.leadComments = leadComments;
		this.leadInternalNotes = leadInternalNotes;
	}

	// Getters and Setters   
    public Integer getRemainderCount() {
        return remainderCount;
    }
    

	public void setRemainderCount(Integer remainderCount) {
        this.remainderCount = remainderCount;
    }
	    
	public Integer getLeadSeqNo() {
		return leadSeqNo;
	}

	public void setLeadSeqNo(Integer leadSeqNo) {
		this.leadSeqNo = leadSeqNo;
	}

	public String getLeadRefId() {
		return leadRefId;
	}

	public void setLeadRefId(String leadRefId) {
		this.leadRefId = leadRefId;
	}

	public String getLeadFlag() {
		return leadFlag;
	}

	public void setLeadFlag(String leadFlag) {
		this.leadFlag = leadFlag;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

//    public EnquiryModel getEnquiry() {
//        return enquiry;
//    }
//
//    public void setEnquiry(EnquiryModel enquiry) {
//        this.enquiry = enquiry;
//    }

	public String getLeadFirstName() {
		return leadFirstName;
	}

	public void setLeadFirstName(String leadFirstName) {
		this.leadFirstName = leadFirstName;
	}

	public String getLeadLastName() {
		return leadLastName;
	}

	public void setLeadLastName(String leadLastName) {
		this.leadLastName = leadLastName;
	}

	public String getLeadEmail() {
		return leadEmail;
	}

	public void setLeadEmail(String leadEmail) {
		this.leadEmail = leadEmail;
	}

	public String getLeadPhoneNo() {
		return leadPhoneNo;
	}

	public void setLeadPhoneNo(String leadPhoneNo) {
		this.leadPhoneNo = leadPhoneNo;
	}

	public String getLeadMobileNo() {
		return leadMobileNo;
	}

	public void setLeadMobileNo(String leadMobileNo) {
		this.leadMobileNo = leadMobileNo;
	}

	public String getLeadAddrLine1() {
		return leadAddrLine1;
	}

	public void setLeadAddrLine1(String leadAddrLine1) {
		this.leadAddrLine1 = leadAddrLine1;
	}

	public String getLeadAddrLine2() {
		return leadAddrLine2;
	}

	public void setLeadAddrLine2(String leadAddrLine2) {
		this.leadAddrLine2 = leadAddrLine2;
	}

	public String getLeadCountry() {
		return leadCountry;
	}

	public void setLeadCountry(String leadCountry) {
		this.leadCountry = leadCountry;
	}

	public String getLeadType() {
		return leadType;
	}

	public void setLeadType(String leadType) {
		this.leadType = leadType;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getLeadPriority() {
		return leadPriority;
	}

	public void setLeadPriority(String leadPriority) {
		this.leadPriority = leadPriority;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getLeadAssignedBy() {
		return leadAssignedBy;
	}

	public void setLeadAssignedBy(String leadAssignedBy) {
		this.leadAssignedBy = leadAssignedBy;
	}

	public String getLeadAssignedTo() {
		return leadAssignedTo;
	}

	public void setLeadAssignedTo(String leadAssignedTo) {
		this.leadAssignedTo = leadAssignedTo;
	}

	public String getLeadCreatedBy() {
		return leadCreatedBy;
	}

	public void setLeadCreatedBy(String leadCreatedBy) {
		this.leadCreatedBy = leadCreatedBy;
	}

	public Date getLeadCreatedDate() {
		return leadCreatedDate;
	}

	public void setLeadCreatedDate(Date leadCreatedDate) {
		this.leadCreatedDate = leadCreatedDate;
	}

	public String getLeadUpdatedBy() {
		return leadUpdatedBy;
	}

	public void setLeadUpdatedBy(String leadUpdatedBy) {
		this.leadUpdatedBy = leadUpdatedBy;
	}

	public Date getLeadUpdatedDate() {
		return leadUpdatedDate;
	}

	public void setLeadUpdatedDate(Date leadUpdatedDate) {
		this.leadUpdatedDate = leadUpdatedDate;
	}

	public String getLeadDescription() {
		return leadDescription;
	}

	public void setLeadDescription(String leadDescription) {
		this.leadDescription = leadDescription;
	}

	public String getLeadComments() {
		return leadComments;
	}

	public void setLeadComments(String leadComments) {
		this.leadComments = leadComments;
	}

	public String getLeadInternalNotes() {
		return leadInternalNotes;
	}

	public void setLeadInternalNotes(String leadInternalNotes) {
		this.leadInternalNotes = leadInternalNotes;
	}
	

}

    