package com.employee.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.PreRemove;
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
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    //
    @OneToMany(orphanRemoval = true, mappedBy = "department_id")
    private Set<Position> positions;

    //, orphanRemoval = true
    @OneToMany(orphanRemoval = true, mappedBy = "department_id")
    private Set<Employee> employees;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @PreRemove
    private void preRemove() {
        for (Position position : positions) {
            position.setDepartment_id(null);
        }
        for (Employee employee : employees) {
            employee.setDepartment_id(null);
        }
    }


}
