package da_bubble.backend.classes;

import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User {

    //TODO: nach dem verknüpfen der DB die richtigen anotation setzen
    private Number id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    public User() {
    }

}
