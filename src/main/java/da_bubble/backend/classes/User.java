package da_bubble.backend.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters!")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters!")
    @Column(name = "last_name")
    private String lastName;


    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
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