package com.Management_Risk_PFE.services.auth;

import com.Management_Risk_PFE.dto.SignupRequest;
import com.Management_Risk_PFE.dto.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);
}
