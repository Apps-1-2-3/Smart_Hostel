package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "STUDENTPHONENO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPhoneNo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID", nullable = false)
    private Student student;
    
    @Column(name = "PhoneNo", nullable = false)
    private String phoneNo;
    
    @Column(name = "IsPrimary")
    private Boolean isPrimary = false;
}
