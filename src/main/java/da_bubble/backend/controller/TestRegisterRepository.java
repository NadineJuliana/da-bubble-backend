package da_bubble.backend.controller;

import da_bubble.backend.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRegisterRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
