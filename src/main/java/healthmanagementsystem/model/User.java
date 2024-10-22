package healthmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    private String id;
    private String staffNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean temporaryPassword;
    private Role role;
    private int phoneNumber;
    private String resetToken;
    private LocalDateTime resetTokenExpiry;
    private boolean isEnabled;
    private String specialization;

    @OneToMany(mappedBy = "checkedInBy")  // Relationship with CheckIn
    private List<CheckIn> checkIns;
}
