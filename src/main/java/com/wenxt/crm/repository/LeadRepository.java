package com.wenxt.crm.repository;

import com.wenxt.crm.model.LeadModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface LeadRepository extends JpaRepository<LeadModel, Integer> {
	Optional<LeadModel> findByLeadRefId(String leadRefId);

    // Paginated methods
    Page<LeadModel> findByLeadStatus(String leadStatus, Pageable pageable);
    Page<LeadModel> findByLeadStatusAndLeadCreatedDateBetween(String leadStatus, Date startDate, Date endDate, Pageable pageable);
    Page<LeadModel> findByLeadCreatedDateBetween(Date startDate, Date endDate, Pageable pageable);
    Page<LeadModel> findAll(Pageable pageable);

    // Non-paginated methods
    List<LeadModel> findByLeadStatus(String leadStatus);
    List<LeadModel> findByLeadStatusAndLeadCreatedDateBetween(String leadStatus, Date startDate, Date endDate);
    List<LeadModel> findByLeadCreatedDateBetween(Date startDate, Date endDate);
    List<LeadModel> findAll();
    
 // Count total leads
    long count();

    // Count leads created between two dates
    long countByLeadCreatedDateBetween(Date startDate, Date endDate);

    // Count leads by status
    long countByLeadStatus(String leadStatus);
    long countByLeadPriority(String leadPriority);
    
    @Query("SELECT MONTH(l.leadCreatedDate) as month, COUNT(l) as leads " +
    	       "FROM LeadModel l " +
    	       "WHERE YEAR(l.leadCreatedDate) = YEAR(CURRENT_DATE) " +
    	       "GROUP BY MONTH(l.leadCreatedDate)")
    	List<Object[]> countLeadsByMonth();
	
}
