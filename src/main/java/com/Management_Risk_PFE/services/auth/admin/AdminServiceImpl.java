package com.Management_Risk_PFE.services.auth.admin;

import com.Management_Risk_PFE.dto.UserDto;
import com.Management_Risk_PFE.entity.User;
import com.Management_Risk_PFE.repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setUserRole(user.getUserRole());
        userDto.setDepartment(user.getDepartment());   // <-- ajout
        userDto.setCreatedAt(user.getCreatedAt());     // <-- ajout
        // Ajouter les permissions de l'utilisateur
        if (user.getPermissions() != null) {
            // Transmettre les permissions sous forme de Set<AssetType> dans le DTO
            userDto.setPermissions(user.getPermissions());
        }

        if (user.getImage() != null) {
            try {
                Path imagePath;

                // Si c'est une image admin stockée dans static.uploads
                if (user.getImage().startsWith("static.uploads")) {
                    // Résoudre à partir de src/main/resources
                    imagePath = Paths.get("src/main/resources", user.getImage());
                } else {
                    // Sinon, probablement uploadée dans un répertoire du projet (uploads/)
                    imagePath = Paths.get(user.getImage());
                }

                if (Files.exists(imagePath)) {
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    userDto.setImage(base64Image);
                } else {
                    System.err.println("Image file not found at: " + imagePath.toAbsolutePath());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }

        return userDto;
    }


    @Override
    public UserDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(this::mapToUserDto).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean updateUser(Long id, UserDto userDto) throws IOException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            if (userDto.getImage() != null)
                existingUser.setImage(userDto.getImage());
            existingUser.setName(userDto.getName());
            existingUser.setUserRole(userDto.getUserRole());
            existingUser.setPermissions(userDto.getPermissions());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setDepartment(userDto.getDepartment());
            existingUser.setCreatedAt(userDto.getCreatedAt());
            userRepository.save(existingUser);
            return true;
        }else {
            return false;
        }
    }

    public List<UserDto> getUsersByDepartment(String department) {
        return userRepository.findByDepartment(department)
                .stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }


}