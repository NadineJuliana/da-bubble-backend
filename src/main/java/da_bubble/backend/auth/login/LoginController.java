package da_bubble.backend.auth.login;

import da_bubble.backend.config.refresh.TokenResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    public LoginController() {

    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginUserRespDTO> login(@Valid @RequestBody LoginUserDTO body) {
        LoginUserRespDTO trDTO = loginService.loginUser(body);

        if (trDTO != null){
            return new ResponseEntity<LoginUserRespDTO>(trDTO, HttpStatus.OK);
        }
        return new ResponseEntity<LoginUserRespDTO>((LoginUserRespDTO) null, HttpStatus.FAILED_DEPENDENCY);
    }
}
