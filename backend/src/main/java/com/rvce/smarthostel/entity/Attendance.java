package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ATTENDANCE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID", nullable = false)
    private Student student;
    
    @Column(name = "TimeStamp", nullable = false)
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false)
    private AttendanceType type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeSSN")
    private Employee verifiedBy;
    
    @Column(name = "Location")
    private String location;
    
    public enum AttendanceType {
        IN, OUT
    }
}
