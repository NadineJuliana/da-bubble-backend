package da_bubble.backend.auth.register;

import da_bubble.backend.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private RegisterUserRepository registerUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(RegisterUerDTO body){
        User resp = registerUserRepository.findByEmail(body.email());

        if(resp == null){
            User u = new User();
            u.setFirstName(body.firstName());
            u.setLastName(body.lastName());
            u.setEmail(body.email());
            u.setPassword(passwordEncoder.encode(body.password()));
            u.setAvatarImg(body.avatarImg());
            u.setActive(false);

            registerUserRepository.save(u);

            return true;
        }

        return false;
    }
}
