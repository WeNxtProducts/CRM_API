package com.wenxt.crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "WN_ENQUIRY")
public class EnquiryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENQ_SEQ_NO")
    private Integer enqSeqNo;

    @Column(name = "ENQ_REF_ID", length = 50, unique=true)
    private String enqRefId;
    
    @OneToOne
    @JoinColumn(name = "LEAD_SEQ_NO", referencedColumnName = "LEAD_SEQ_NO", unique = true, nullable = false)
    private LeadModel lead;

    @Size(max = 100, message = "Name must be at most 100 characters")
    @Column(name = "ENQ_NAME", length = 100)
    private String enqName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENQ_DATE")
    private Date enqDate;

    @Column(name = "ENQ_UPDATED_BY", length = 20)
    private String enqUpdatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENQ_UPDATED_DATE")
    private Date enqUpdatedDate = new Date();

    @Column(name = "ENQ_LOB_CODE", length = 10)
    private String enqLobCode;

    @Column(name = "ENQ_LOB_NAME", length = 100)
    private String enqLobName;

    @Column(name = "ENQ_PROD_CODE", length = 200)
    private String enqProdCode;

    @Column(name = "ENQ_PROD_NAME", length = 100)
    private String enqProdName;

    @Column(name = "ENQ_BUS_TYPE", length = 50)
    private String enqBusType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENQ_DATE_RECEIVED")
    private Date enqDateReceived;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENQ_EXP_DATE")
    private Date enqExpDate;

    @Column(name = "ENQ_SUM_INSURED", precision = 18, scale = 3)
    private BigDecimal enqSumInsured;

    @Column(name = "ENQ_SUGGESTED_PREM", precision = 18, scale = 3)
    private BigDecimal enqSuggestedPrem;

    @Column(name = "ENQ_SUGGESTED_RATE", precision = 8, scale = 5)
    private BigDecimal enqSuggestedRate;

    @Column(name = "ENQ_INTERMED_CODE", length = 50)
    private String enqIntermedCode;

    @Column(name = "ENQ_INTERMED_NAME", length = 100)
    private String enqIntermedName;

    @Column(name = "ENQ_UNDERWRITER_CODE", length = 50)
    private String enqUnderwriterCode;

    @Column(name = "ENQ_UNDERWRITER", length = 100)
    private String enqUnderwriter;

    @Column(name = "ENQ_QUOTE_FLAG", length = 50)
    private String enqQuoteFlag;

    @Lob
    @Column(name = "ENQ_RISK_DESC")
    private String enqRiskDesc;

    @Lob
    @Column(name = "ENQ_REMARKS")
    private String enqRemarks;

    @Column(name = "ENQ_STATUS", length = 30)
    private String enqStatus = "pending";

    @Column(name = "ENQ_ASSIGNED_TO", length = 20)
    private String enqAssignedTo;

    @Column(name = "ENQ_FIRST_NAME", length = 50)
    private String enqFirstName;

    @Column(name = "ENQ_LAST_NAME", length = 50)
    private String enqLastName;

    @Email(message = "Invalid email format")
    @Column(name = "ENQ_EMAIL", length = 255)
    private String enqEmail;

    @Size(max = 20, message = "Phone number must be at most 20 characters")
    @Column(name = "ENQ_PHONE", length = 20)
    private String enqPhone;

    @Size(max = 20, message = "Mobile number must be at most 20 characters")
    @Column(name = "ENQ_MOBILE", length = 20)
    private String enqMobile;

    @Column(name = "ENQ_ADDR_LINE1", length = 200)
    private String enqAddrLine1;

    @Column(name = "ENQ_ADDR_LINE2", length = 200)
    private String enqAddrLine2;

    @Column(name = "ENQ_COUNTRY", length = 100)
    private String enqCountry;

    @Column(name = "ENQ_TYPE", length = 50)
    private String enqType;

    @Column(name = "ENQ_EXPECTED_VALUE", precision = 18, scale = 3)
    private BigDecimal enqExpectedValue;

    @Lob
    @Column(name = "ENQ_DESCRIPTION")
    private String enqDescription;

    @Lob
    @Column(name = "ENQ_INTERNAL_NOTES")
    private String enqInternalNotes;
    
    @Lob
    @Column(name = "ENQ_LEAD_COMMENTS")
    private String leadComments;
    
    @Lob
    @Column(name = "ENQ_SALES_COMMENTS")
    private String salesComments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENQ_NEXT_FOLLOW_UP_DATE")
    private Date enqNextFollowUpDate;

    @Column(name = "ENQ_CREATED_BY", length = 20)
    private String enqCreatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENQ_CREATED_DATE")
    private Date enqCreatedDate = new Date();

    // ✅ Add a default constructor
    public EnquiryModel() {
    	
    }
    
    
    public EnquiryModel(Integer enqSeqNo, String enqRefId, LeadModel lead,
			@Size(max = 100, message = "Name must be at most 100 characters") String enqName, Date enqDate, Integer leadSeqNo,
			String enqUpdatedBy, Date enqUpdatedDate, String enqLobCode, String enqLobName, String enqProdCode,
			String enqProdName, String enqBusType, Date enqDateReceived, Date enqExpDate, BigDecimal enqSumInsured,
			BigDecimal enqSuggestedPrem, BigDecimal enqSuggestedRate, String enqIntermedCode, String enqIntermedName,
			String enqUnderwriterCode, String enqUnderwriter, String enqQuoteFlag, String enqRiskDesc,
			String enqRemarks, String enqStatus, String enqAssignedTo, String enqFirstName, String enqLastName,
			@Email(message = "Invalid email format") String enqEmail,
			@Size(max = 20, message = "Phone number must be at most 20 characters") String enqPhone,
			@Size(max = 20, message = "Mobile number must be at most 20 characters") String enqMobile,
			String enqAddrLine1, String enqAddrLine2, String enqCountry, String enqType, BigDecimal enqExpectedValue,
			String enqDescription, String enqInternalNotes, Date enqNextFollowUpDate, String enqCreatedBy,
			Date enqCreatedDate, String leadComments, String salesComments) {
		super();
		this.enqSeqNo = enqSeqNo;
		this.enqRefId = enqRefId;
		this.lead = lead;
		this.enqName = enqName;
		this.enqDate = enqDate;
		this.enqUpdatedBy = enqUpdatedBy;
		this.enqUpdatedDate = enqUpdatedDate;
		this.enqLobCode = enqLobCode;
		this.enqLobName = enqLobName;
		this.enqProdCode = enqProdCode;
		this.enqProdName = enqProdName;
		this.enqBusType = enqBusType;
		this.enqDateReceived = enqDateReceived;
		this.enqExpDate = enqExpDate;
		this.enqSumInsured = enqSumInsured;
		this.enqSuggestedPrem = enqSuggestedPrem;
		this.enqSuggestedRate = enqSuggestedRate;
		this.enqIntermedCode = enqIntermedCode;
		this.enqIntermedName = enqIntermedName;
		this.enqUnderwriterCode = enqUnderwriterCode;
		this.enqUnderwriter = enqUnderwriter;
		this.enqQuoteFlag = enqQuoteFlag;
		this.enqRiskDesc = enqRiskDesc;
		this.enqRemarks = enqRemarks;
		this.enqStatus = enqStatus;
		this.enqAssignedTo = enqAssignedTo;
		this.enqFirstName = enqFirstName;
		this.enqLastName = enqLastName;
		this.enqEmail = enqEmail;
		this.enqPhone = enqPhone;
		this.enqMobile = enqMobile;
		this.enqAddrLine1 = enqAddrLine1;
		this.enqAddrLine2 = enqAddrLine2;
		this.enqCountry = enqCountry;
		this.enqType = enqType;
		this.enqExpectedValue = enqExpectedValue;
		this.enqDescription = enqDescription;
		this.enqInternalNotes = enqInternalNotes;
		this.enqNextFollowUpDate = enqNextFollowUpDate;
		this.enqCreatedBy = enqCreatedBy;
		this.enqCreatedDate = enqCreatedDate;
		this.leadComments = leadComments;
		this.salesComments = salesComments;
	}
    
 // PrePersist and PreUpdate methods
    @PrePersist
    @PreUpdate
    protected void onSaveOrUpdate() {
    	
        if (this.lead != null) {
            this.enqName = this.lead.getLeadName();
        }
    }

//    @PrePersist
//    protected void onCreate() {
//        this.enqCreatedDate = new Date();
//        this.enqDate = new Date();
//        onSaveOrUpdate();  // Call the common method
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.enqUpdatedDate = new Date();
//        onSaveOrUpdate();  // Call the common method
//    }

    
    // Getters and setters for all fields including 'lead'
    public LeadModel getLead() {
        return lead;
    }

    public void setLead(LeadModel lead) {
        this.lead = lead;
    }

	public Integer getEnqSeqNo() {
		return enqSeqNo;
	}

	public void setEnqSeqNo(Integer enqSeqNo) {
		this.enqSeqNo = enqSeqNo;
	}

	public String getEnqRefId() {
		return enqRefId;
	}

	public void setEnqRefId(String enqRefId) {
		this.enqRefId = enqRefId;
	}  
	    
	public String getEnqName() {
		return enqName;
	}

	public void setEnqName(String enqName) {
		this.enqName = enqName;
	}

	public Date getEnqDate() {
		return enqDate;
	}

	public void setEnqDate(Date enqDate) {
		this.enqDate = enqDate;
	}

	public String getEnqUpdatedBy() {
		return enqUpdatedBy;
	}

	public void setEnqUpdatedBy(String enqUpdatedBy) {
		this.enqUpdatedBy = enqUpdatedBy;
	}

	public Date getEnqUpdatedDate() {
		return enqUpdatedDate;
	}

	public void setEnqUpdatedDate(Date enqUpdatedDate) {
		this.enqUpdatedDate = enqUpdatedDate;
	}

	public String getEnqLobCode() {
		return enqLobCode;
	}

	public void setEnqLobCode(String enqLobCode) {
		this.enqLobCode = enqLobCode;
	}

	public String getEnqLobName() {
		return enqLobName;
	}

	public void setEnqLobName(String enqLobName) {
		this.enqLobName = enqLobName;
	}

	public String getEnqProdCode() {
		return enqProdCode;
	}

	public void setEnqProdCode(String enqProdCode) {
		this.enqProdCode = enqProdCode;
	}

	public String getEnqProdName() {
		return enqProdName;
	}

	public void setEnqProdName(String enqProdName) {
		this.enqProdName = enqProdName;
	}

	public String getEnqBusType() {
		return enqBusType;
	}

	public void setEnqBusType(String enqBusType) {
		this.enqBusType = enqBusType;
	}

	public Date getEnqDateReceived() {
		return enqDateReceived;
	}

	public void setEnqDateReceived(Date enqDateReceived) {
		this.enqDateReceived = enqDateReceived;
	}

	public Date getEnqExpDate() {
		return enqExpDate;
	}

	public void setEnqExpDate(Date enqExpDate) {
		this.enqExpDate = enqExpDate;
	}

	public BigDecimal getEnqSumInsured() {
		return enqSumInsured;
	}

	public void setEnqSumInsured(BigDecimal enqSumInsured) {
		this.enqSumInsured = enqSumInsured;
	}

	public BigDecimal getEnqSuggestedPrem() {
		return enqSuggestedPrem;
	}

	public void setEnqSuggestedPrem(BigDecimal enqSuggestedPrem) {
		this.enqSuggestedPrem = enqSuggestedPrem;
	}

	public BigDecimal getEnqSuggestedRate() {
		return enqSuggestedRate;
	}

	public void setEnqSuggestedRate(BigDecimal enqSuggestedRate) {
		this.enqSuggestedRate = enqSuggestedRate;
	}

	public String getEnqIntermedCode() {
		return enqIntermedCode;
	}

	public void setEnqIntermedCode(String enqIntermedCode) {
		this.enqIntermedCode = enqIntermedCode;
	}

	public String getEnqIntermedName() {
		return enqIntermedName;
	}

	public void setEnqIntermedName(String enqIntermedName) {
		this.enqIntermedName = enqIntermedName;
	}

	public String getEnqUnderwriterCode() {
		return enqUnderwriterCode;
	}

	public void setEnqUnderwriterCode(String enqUnderwriterCode) {
		this.enqUnderwriterCode = enqUnderwriterCode;
	}

	public String getEnqUnderwriter() {
		return enqUnderwriter;
	}

	public void setEnqUnderwriter(String enqUnderwriter) {
		this.enqUnderwriter = enqUnderwriter;
	}

	public String getEnqQuoteFlag() {
		return enqQuoteFlag;
	}

	public void setEnqQuoteFlag(String enqQuoteFlag) {
		this.enqQuoteFlag = enqQuoteFlag;
	}

	public String getEnqRiskDesc() {
		return enqRiskDesc;
	}

	public void setEnqRiskDesc(String enqRiskDesc) {
		this.enqRiskDesc = enqRiskDesc;
	}

	public String getEnqRemarks() {
		return enqRemarks;
	}

	public void setEnqRemarks(String enqRemarks) {
		this.enqRemarks = enqRemarks;
	}

	public String getEnqStatus() {
		return enqStatus;
	}

	public void setEnqStatus(String enqStatus) {
		this.enqStatus = enqStatus;
	}

	public String getEnqAssignedTo() {
		return enqAssignedTo;
	}

	public void setEnqAssignedTo(String enqAssignedTo) {
		this.enqAssignedTo = enqAssignedTo;
	}

	public String getEnqFirstName() {
		return enqFirstName;
	}

	public void setEnqFirstName(String enqFirstName) {
		this.enqFirstName = enqFirstName;
	}

	public String getEnqLastName() {
		return enqLastName;
	}

	public void setEnqLastName(String enqLastName) {
		this.enqLastName = enqLastName;
	}

	public String getEnqEmail() {
		return enqEmail;
	}

	public void setEnqEmail(String enqEmail) {
		this.enqEmail = enqEmail;
	}

	public String getEnqPhone() {
		return enqPhone;
	}

	public void setEnqPhone(String enqPhone) {
		this.enqPhone = enqPhone;
	}

	public String getEnqMobile() {
		return enqMobile;
	}

	public void setEnqMobile(String enqMobile) {
		this.enqMobile = enqMobile;
	}

	public String getEnqAddrLine1() {
		return enqAddrLine1;
	}

	public void setEnqAddrLine1(String enqAddrLine1) {
		this.enqAddrLine1 = enqAddrLine1;
	}

	public String getEnqAddrLine2() {
		return enqAddrLine2;
	}

	public void setEnqAddrLine2(String enqAddrLine2) {
		this.enqAddrLine2 = enqAddrLine2;
	}

	public String getEnqCountry() {
		return enqCountry;
	}

	public void setEnqCountry(String enqCountry) {
		this.enqCountry = enqCountry;
	}

	public String getEnqType() {
		return enqType;
	}

	public void setEnqType(String enqType) {
		this.enqType = enqType;
	}

	public BigDecimal getEnqExpectedValue() {
		return enqExpectedValue;
	}

	public void setEnqExpectedValue(BigDecimal enqExpectedValue) {
		this.enqExpectedValue = enqExpectedValue;
	}

	public String getEnqDescription() {
		return enqDescription;
	}

	public void setEnqDescription(String enqDescription) {
		this.enqDescription = enqDescription;
	}

	public String getEnqInternalNotes() {
		return enqInternalNotes;
	}

	public void setEnqInternalNotes(String enqInternalNotes) {
		this.enqInternalNotes = enqInternalNotes;
	}

	public Date getEnqNextFollowUpDate() {
		return enqNextFollowUpDate;
	}

	public void setEnqNextFollowUpDate(Date enqNextFollowUpDate) {
		this.enqNextFollowUpDate = enqNextFollowUpDate;
	}

	public String getEnqCreatedBy() {
		return enqCreatedBy;
	}

	public void setEnqCreatedBy(String enqCreatedBy) {
		this.enqCreatedBy = enqCreatedBy;
	}

	public Date getEnqCreatedDate() {
		return enqCreatedDate;
	}

	public void setEnqCreatedDate(Date enqCreatedDate) {
		this.enqCreatedDate = enqCreatedDate;
	}


	public String getLeadComments() {
		return leadComments;
	}


	public void setLeadComments(String leadComments) {
		this.leadComments = leadComments;
	}


	public String getSalesComments() {
		return salesComments;
	}


	public void setSalesComments(String salesComments) {
		this.salesComments = salesComments;
	}
    
	
    // ✅ Ensure there are getters and setters for all fields
    
    
    
    
}
