package da_bubble.backend.auth.login;

import da_bubble.backend.auth.register.RegisterUserRepository;
import da_bubble.backend.classes.User;
import da_bubble.backend.config.JwtUtils.JwtUtil;
import da_bubble.backend.config.refresh.TokenResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginService {

    @Autowired
    private RegisterUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;




    public TokenResponseDTO loginUser(LoginDTO body){
        User user = userRepository.findByEmail(body.email());

        if (user == null || !passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return new TokenResponseDTO(
                jwtUtil.generateAccessToken(user.getEmail(), user.getFirstName(), user.getLastName()),
                jwtUtil.generateRefreshToken(user.getEmail())
        );
    }
}
