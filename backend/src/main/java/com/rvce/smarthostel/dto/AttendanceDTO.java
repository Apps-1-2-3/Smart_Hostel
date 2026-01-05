package com.rvce.smarthostel.dto;

import com.rvce.smarthostel.entity.Attendance;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private Long id;
    private String studentId;
    private String studentName;
    private LocalDateTime timestamp;
    private String type;
    private String location;
    private String verifiedBy;
}
