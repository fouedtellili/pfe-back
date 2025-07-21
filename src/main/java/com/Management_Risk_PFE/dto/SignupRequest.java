package com.Management_Risk_PFE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.Management_Risk_PFE.enums.AssetType;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String name;
    private String password;
    private String image;
    private String department;
    private Set<AssetType> permissions;
}
