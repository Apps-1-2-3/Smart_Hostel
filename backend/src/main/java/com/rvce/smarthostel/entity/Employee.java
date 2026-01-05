package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EMPLOYEES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @Column(name = "SSN")
    private String ssn;
    
    @Column(name = "FirstName", nullable = false)
    private String firstName;
    
    @Column(name = "MiddleName")
    private String middleName;
    
    @Column(name = "LastName", nullable = false)
    private String lastName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private EmployeeRole role;
    
    public enum EmployeeRole {
        ADMIN, WARDEN, MESS_STAFF, SECURITY
    }
    
    @Transient
    public String getFullName() {
        StringBuilder name = new StringBuilder(firstName);
        if (middleName != null && !middleName.isEmpty()) {
            name.append(" ").append(middleName);
        }
        name.append(" ").append(lastName);
        return name.toString();
    }
}
