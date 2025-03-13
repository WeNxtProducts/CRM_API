package com.wenxt.crm.repository;

import com.wenxt.crm.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    List<TaskModel> findAllByOrderByTaskDueDateAsc();
    
    //List<TaskModel> findByTaskDueDateBetween(Date startDate, Date endDate);

	List<TaskModel> findByTaskDueDateBetween(java.util.Date startDate, java.util.Date endDate);
}