package com.rvce.smarthostel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Pattern(regexp = ".*@rvce\\.edu\\.in$", message = "Only @rvce.edu.in emails are allowed")
    private String email;
    
    @NotBlank(message = "Password is required")
    private String password;
}
