package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "ROOM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @Column(name = "RoomNo")
    private String roomNo;
    
    @Column(name = "Capacity", nullable = false)
    private Integer capacity;
    
    @Column(name = "HostelNo", nullable = false)
    private String hostelNo;
    
    @Column(name = "Floor")
    private Integer floor;
    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomAssignment> assignments;
}
