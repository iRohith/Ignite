package abc.ignite.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(unique = true)
    private String username;
    private String fullName;
    private String role; // Accepted values = (admin/user)

    public static User fromUsername(String username){ return new User(username, null, null); }
}
