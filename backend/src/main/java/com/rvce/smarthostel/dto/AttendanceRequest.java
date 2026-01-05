package com.rvce.smarthostel.dto;

import com.rvce.smarthostel.entity.Attendance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceRequest {
    @NotBlank(message = "Student ID is required")
    private String studentId;
    
    @NotNull(message = "Attendance type is required")
    private Attendance.AttendanceType type;
    
    private String location;
    private String employeeSSN;
}
