package com.Management_Risk_PFE.dto;
import com.Management_Risk_PFE.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;

    private UserRole userRole;

    private Long userId;
}
