package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ROOMASSIGNEDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoomNo", nullable = false)
    private Room room;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID", nullable = false)
    private Student student;
    
    @Column(name = "AssignedDate")
    private LocalDate assignedDate;
    
    @Column(name = "IsActive")
    private Boolean isActive = true;
}
