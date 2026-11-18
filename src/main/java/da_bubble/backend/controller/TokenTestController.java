package da_bubble.backend.controller;

import da_bubble.backend.auth.login.LoginDTO;
import da_bubble.backend.classes.User;
import da_bubble.backend.config.JwtUtil;
import da_bubble.backend.config.SecurityConfig;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.callback.UnsupportedCallbackException;

import java.io.IOException;


// TODO: only for testing the JWT setup. delete once a real /auth/login exists
@RestController
public class TokenTestController {

    private final JwtUtil jwtUtil;

    private boolean loginSucceeded = false;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private TestRegisterRepository testRegisterRepository;

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


    @PostMapping("/auth/login")
    public String login(@Valid @RequestBody LoginDTO body) {
        User u = testRegisterRepository.findByEmail(body.email());

        if (u != null && securityConfig.passwordEncoder().matches(body.password(), u.getPassword())) {
            return jwtUtil.generateToken(u.getEmail());
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }
}
