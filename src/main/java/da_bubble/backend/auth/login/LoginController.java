package da_bubble.backend.auth.login;

import da_bubble.backend.config.refresh.TokenResponseDTO;
import da_bubble.backend.classes.User;
import da_bubble.backend.config.JwtUtils.JwtUtil;
import da_bubble.backend.auth.register.RegisterUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginController {

    private final RegisterUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginController(RegisterUserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/login")
    public TokenResponseDTO login(@Valid @RequestBody LoginDTO body) {
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
