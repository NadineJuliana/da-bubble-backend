package da_bubble.backend.config.refresh;

import jakarta.validation.constraints.NotBlank;

/**
 *  Returns the refreshToken */
public record RefreshRequestDTO(@NotBlank String refreshToken) {

}
