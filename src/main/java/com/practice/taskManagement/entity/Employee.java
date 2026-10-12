package com.practice.taskManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id", nullable = false)
    private Long empId;
    @Column(name="first-name", nullable = false)
    private String firstName;
    @Column(name="last-name", nullable = false)
    private String lastName;
//    @Column(name="email", nullable = false)
//    private String email;
    @Column(name="skills", nullable = false)
    private String skills;
    @Column(name="max-capacity", nullable = false)
    private Long maxTaskCapacity;
    @Column(nullable = false)
    private boolean active=true;
}
