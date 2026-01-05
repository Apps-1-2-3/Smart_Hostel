package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HOSTELS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hostel {
    @Id
    @Column(name = "HostelNo")
    private String hostelNo;
    
    @Column(name = "HostelName", nullable = false)
    private String hostelName;
    
    @Column(name = "TotalRooms")
    private Integer totalRooms;
    
    @Column(name = "TotalFloors")
    private Integer totalFloors;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
    private HostelType type;
    
    public enum HostelType {
        BOYS, GIRLS
    }
}
