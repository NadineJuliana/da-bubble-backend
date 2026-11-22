package da_bubble.backend.config.refresh;

import da_bubble.backend.classes.User;
import da_bubble.backend.config.JwtUtils.JwtUtil;
import da_bubble.backend.auth.register.RegisterUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RefreshController {

    private final RegisterUserRepository userRepository;
    private final JwtUtil jwtUtil;

    public RefreshController(RegisterUserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // accessToken": "<new, 15 min>", "refreshToken": "<the same one, still valid until its 1 day is over>"
    // Once the refresh token has expired, this returns 401 and the user has to log in again.
    @PostMapping("/auth/refresh")
    public TokenResponseDTO RefreshToken(@Valid @RequestBody RefreshRequestDTO body) {
        String refreshToken = body.refreshToken();

        if (!jwtUtil.isValidRefreshToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        // Load the user: needed for the name in the new access token,
        // and the user could have been deleted after the refresh token was issued
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getFirstName(), user.getLastName());
        return new TokenResponseDTO(accessToken, refreshToken);
    }
}
