package com.rvce.smarthostel.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private String studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String roomNo;
    private String hostelNo;
    private String status;
    private String phoneNumber;
}
