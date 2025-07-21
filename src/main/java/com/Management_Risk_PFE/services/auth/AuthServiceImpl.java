package com.Management_Risk_PFE.services.auth;

import com.Management_Risk_PFE.dto.SignupRequest;
import com.Management_Risk_PFE.dto.UserDto;
import com.Management_Risk_PFE.entity.User;
import com.Management_Risk_PFE.enums.AssetType;
import com.Management_Risk_PFE.enums.UserRole;
import com.Management_Risk_PFE.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    @PostConstruct
    public void createAdminAccounts() {
        createAdminIfNotExists("dorra.mejri@coficab.com", "Dorra Mejri", "dorra123", "static/uploads/dorra.jpg");
        createAdminIfNotExists("karim.bousnina@coficab.com", "Karim Bousnina", "karim123", "static/uploads/karim.jpg");
    }

    private void createAdminIfNotExists(String email, String name, String rawPassword, String imagePath) {
        if (userRepository.findFirstByEmail(email).isEmpty()) {
            User newAdmin = new User();
            newAdmin.setName(name);
            newAdmin.setEmail(email);
            newAdmin.setPassword(new BCryptPasswordEncoder().encode(rawPassword));
            newAdmin.setUserRole(UserRole.ADMIN);
            newAdmin.setImage(imagePath);
            newAdmin.setPermissions(AssetType.allPermissions());
            userRepository.save(newAdmin);
            System.out.println("Admin account for " + name + " created with image " + imagePath);
        } else {
            System.out.println("Admin account with email " + email + " already exists.");
        }
    }

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());

        // Utiliser le mot de passe réel donné par l'utilisateur
        String rawPassword = signupRequest.getPassword();  // Mot de passe réel
        user.setPassword(new BCryptPasswordEncoder().encode(rawPassword));  // Hacher le mot de passe

        user.setUserRole(UserRole.CUSTOMER);
        user.setImage(signupRequest.getImage());
        user.setDepartment(signupRequest.getDepartment());


        // ✅ utiliser les permissions reçues
        user.setPermissions(signupRequest.getPermissions() != null ? signupRequest.getPermissions() : new HashSet<>());


        // Sauvegarder l'utilisateur dans la base de données
        User createdUser = userRepository.save(user);

        // Envoyer l'email avec les informations de connexion
        emailService.sendAccountCreationEmail(createdUser.getEmail(), createdUser.getName(), rawPassword);

        // Créer un DTO utilisateur à retourner
        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setName(createdUser.getName());
        userDto.setEmail(createdUser.getEmail());
        userDto.setUserRole(createdUser.getUserRole());
        userDto.setImage(user.getImage());
        userDto.setDepartment(createdUser.getDepartment());
        userDto.setCreatedAt(createdUser.getCreatedAt());
        userDto.setPermissions(createdUser.getPermissions());

        return userDto;
    }
    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
