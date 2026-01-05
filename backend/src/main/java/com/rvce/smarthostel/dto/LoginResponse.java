package com.rvce.smarthostel.dto;

import com.rvce.smarthostel.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String email;
    private String name;
    private String role;
    private String roomNumber;
    private String studentId;
}
