package com.Management_Risk_PFE.repository;

import com.Management_Risk_PFE.entity.User;
import com.Management_Risk_PFE.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String email);

    User findByUserRole(UserRole userRole);

    List<User> findByDepartment(String department);
}
