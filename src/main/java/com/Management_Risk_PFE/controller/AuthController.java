package com.Management_Risk_PFE.controller;


import com.Management_Risk_PFE.dto.AuthenticationRequest;
import com.Management_Risk_PFE.dto.AuthenticationResponse;
import com.Management_Risk_PFE.dto.SignupRequest;
import com.Management_Risk_PFE.dto.UserDto;
import com.Management_Risk_PFE.entity.User;
import com.Management_Risk_PFE.enums.AssetType;
import com.Management_Risk_PFE.repository.UserRepository;
import com.Management_Risk_PFE.services.auth.AuthService;
import com.Management_Risk_PFE.services.auth.jwt.UserService;
import com.Management_Risk_PFE.utils.JWTUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JWTUtil jwtUtil;

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestParam("name") String name,
                                            @RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("image") MultipartFile image,
                                            @RequestParam("department") String department,
                                            @RequestParam("permissions") Set<AssetType> permissions) {

        if (authService.hasCustomerWithEmail(email)) {
            return new ResponseEntity<>("Customer already exists with this email", HttpStatus.NOT_ACCEPTABLE);
        }

        String fileName = null;
        if (image != null && !image.isEmpty()) {
            fileName = StringUtils.cleanPath(image.getOriginalFilename());
            try {
                Path uploadPath = Paths.get("static/uploads");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path targetLocation = uploadPath.resolve(fileName);
                Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                return new ResponseEntity<>("Image upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        SignupRequest signupRequest = new SignupRequest(email, name, password, "static/uploads/" + fileName, department,permissions);
        UserDto createdCustomerDto = authService.createCustomer(signupRequest);

        if (createdCustomerDto == null) {
            return new ResponseEntity<>("Customer not created, Come again later", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!!");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser= userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt= jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse= new AuthenticationResponse();
        if (optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;

    }




}