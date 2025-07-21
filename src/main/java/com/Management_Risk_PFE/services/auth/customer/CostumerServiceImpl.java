package com.Management_Risk_PFE.services.auth.customer;

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
public class CostumerServiceImpl implements CostumerService{

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
}
