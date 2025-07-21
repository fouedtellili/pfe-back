package com.Management_Risk_PFE.controller;


import com.Management_Risk_PFE.dto.UserDto;
import com.Management_Risk_PFE.services.auth.admin.AdminService;
import com.Management_Risk_PFE.services.auth.customer.CostumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    private final CostumerService costumerService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return costumerService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto userDto = costumerService.getUserById(id);
        if (userDto != null) {
            return ResponseEntity.ok(userDto); // Si l'utilisateur est trouvé
        } else {
            return ResponseEntity.notFound().build(); // Si l'utilisateur n'est pas trouvé
        }
    }
}
