package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    private String roomNumber;
    
    // Link to Student if role is STUDENT
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    
    // Link to Employee if role is ADMIN or MESS_STAFF
    @OneToOne
    @JoinColumn(name = "employee_ssn")
    private Employee employee;
    
    public enum Role {
        STUDENT, ADMIN, MESS_STAFF
    }
}
