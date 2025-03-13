package com.wenxt.crm.repository;

import com.wenxt.crm.model.ActivityLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLogModel, Long> {
    List<ActivityLogModel> findAllByOrderByActivityDateDesc();
}