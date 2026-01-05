package com.rvce.smarthostel.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private String roomNo;
    private Integer capacity;
    private String hostelNo;
    private Integer floor;
    private Long currentOccupancy;
    private String status;
}
