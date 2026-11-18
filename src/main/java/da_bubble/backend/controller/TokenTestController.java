package da_bubble.backend.controller;

import da_bubble.backend.config.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: only for testing the JWT setup. delete once a real /auth/login exists
@RestController
public class TokenTestController {

    private final JwtUtil jwtUtil;

    public TokenTestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //GET /auth/test-token?email=max@test.de
    @GetMapping("/auth/test-token")
    public String testToken(@RequestParam(defaultValue = "test@test.de") String email) {
        return jwtUtil.generateToken(email);
    }

    @GetMapping("/user/me")
    public String me(Authentication authentication) {
        return authentication.getName();
    }
}
