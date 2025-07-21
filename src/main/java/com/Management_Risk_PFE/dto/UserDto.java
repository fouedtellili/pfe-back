package com.Management_Risk_PFE.dto;

import com.Management_Risk_PFE.enums.AssetType;
import com.Management_Risk_PFE.enums.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private UserRole userRole;
    private String image;
    private String department;
    private LocalDateTime createdAt;
    private Set<AssetType> permissions; // ✅ Ajouter ça
}