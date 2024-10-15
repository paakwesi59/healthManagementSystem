package healthmanagementsystem.repository;

import healthmanagementsystem.model.Role;
import healthmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByStaffNumber(String staffNumber); // New method for finding user by staff number

    int countByRole(Role role); // Method to count users by role for generating staff number
}
