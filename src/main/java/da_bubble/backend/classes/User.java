package da_bubble.backend.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @NotNull
    @Column(name = "first_name")
    @Min(value = 3, message = "First name to short!")
    private String firstName;

    @NotNull
    @Min(value = 3, message = "Last name to short!")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Min(value = 8, message = "Invalid Password!")
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "is_online")
    private boolean isOnline;

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "avatar_img")
    private Integer avatarImg;
}