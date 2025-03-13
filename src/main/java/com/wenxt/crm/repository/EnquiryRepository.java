package com.wenxt.crm.repository;

import com.wenxt.crm.model.EnquiryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EnquiryRepository extends JpaRepository<EnquiryModel, Integer> {

    // Paginated methods
    Page<EnquiryModel> findByEnqStatus(String enqStatus, Pageable pageable);
    Page<EnquiryModel> findByEnqStatusAndEnqDateBetween(String enqStatus, Date startDate, Date endDate, Pageable pageable);
    Page<EnquiryModel> findByEnqDateBetween(Date startDate, Date endDate, Pageable pageable);
    Page<EnquiryModel> findAll(Pageable pageable);

    // Non-paginated methods
    List<EnquiryModel> findByEnqStatus(String enqStatus);
    List<EnquiryModel> findByEnqStatusAndEnqDateBetween(String enqStatus, Date startDate, Date endDate);
    List<EnquiryModel> findByEnqDateBetween(Date startDate, Date endDate);
    List<EnquiryModel> findAll();
}