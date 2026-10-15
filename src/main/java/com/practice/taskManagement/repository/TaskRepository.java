package com.practice.taskManagement.repository;

import com.practice.taskManagement.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Tasks,Long> {
    Optional<Tasks> findByStatus(String status);
    long countByEmployee_EmpIdAndStatus(Long empId, String status);
    List<Tasks> findByStatusOrderByPriorityAsc(String status);

}
