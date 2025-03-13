package com.wenxt.crm.repository;

import com.wenxt.crm.model.ActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityModel, Integer> {

    @Query("SELECT COUNT(a) FROM ActivityModel a WHERE a.enquiry.enqSeqNo = :enqSeqNo")
    Long countByEnquiryId(@Param("enqSeqNo") Integer enqSeqNo);

    List<ActivityModel> findByActivityTypeOrderByActivityDateAsc(String activityType);

    List<ActivityModel> findByActivityDateBetweenAndActivityType(Date startDate, Date endDate, String activityType);

	List<ActivityModel> findByActivityDateBetween(java.util.Date startDate, java.util.Date endDate);
}