    package com.Management_Risk_PFE.services.auth.admin;

    import com.Management_Risk_PFE.dto.UserDto;
    import io.jsonwebtoken.io.IOException;

    import java.util.List;

    public interface AdminService {

        List<UserDto> getAllUsers();

        UserDto getUserById(Long id);

        void deleteUser(Long id);

        boolean updateUser(Long id, UserDto userDto) throws IOException;

    }
