package com.employee.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    //, mappedBy = "department_id"
    @OneToMany(orphanRemoval = true)
    private Set<Position> positions;

    //, mappedBy = "department_id"
    @OneToMany(orphanRemoval = true)
    private Set<Employee> employees;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @PreRemove
    private void preRemove() {
        for (Position position : positions) {
            position.setDepartment_id(null); // Remove the association to avoid constraint violations
        }
        for (Employee employee : employees) {
            employee.setDepartment_id(null); // Remove the association to avoid constraint violations
        }
    }



}
