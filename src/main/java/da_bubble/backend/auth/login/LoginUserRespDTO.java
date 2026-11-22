package da_bubble.backend.auth.login;

import jakarta.validation.constraints.NotBlank;

public record LoginUserRespDTO(String lastName,
                               String firstName,
                               String accessToken,
                               String refreshToken) {
}
