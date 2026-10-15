package com.practice.taskManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @Column(name="task-name",nullable=false)
    private String taskName;
    @Column(name="requiredSkill",nullable=false)
    private String requiredSkill;
    @Column(name="priority", nullable = false)
    private String priority;
    @Column(name="status",nullable=false)
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id",nullable = true)
    private Employee employee;

}
