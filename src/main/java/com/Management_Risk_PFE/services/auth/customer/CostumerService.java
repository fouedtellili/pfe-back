package com.Management_Risk_PFE.services.auth.customer;

import com.Management_Risk_PFE.dto.UserDto;

import java.util.List;

public interface CostumerService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);
}
