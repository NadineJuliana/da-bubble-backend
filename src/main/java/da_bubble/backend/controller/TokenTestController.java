package da_bubble.backend.controller;

import da_bubble.backend.config.JwtUtils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: only for testing the JWT setup. delete once registration works
// The real login is in auth/login/LoginController
@RestController
public class TokenTestController {

    private final JwtUtil jwtUtil;

    public TokenTestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //GET /auth/test-token?email=max@test.de&firstName=Max&lastName=Muster  -> access token without login
    @GetMapping("/auth/test-token")
    public String testToken(@RequestParam(defaultValue = "test@test.de") String email,
                            @RequestParam(defaultValue = "Test") String firstName,
                            @RequestParam(defaultValue = "User") String lastName) {
        return jwtUtil.generateAccessToken(email, firstName, lastName);
    }

    @GetMapping("/user/me")
    public String me(Authentication authentication) {
        return authentication.getName();
    }
}
