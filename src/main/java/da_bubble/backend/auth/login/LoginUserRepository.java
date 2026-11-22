package da_bubble.backend.auth.login;

import da_bubble.backend.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}
