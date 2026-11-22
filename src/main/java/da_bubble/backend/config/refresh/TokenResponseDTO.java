package da_bubble.backend.config.refresh;

/**
 * Return the tokens
 * @param accessToken
 * @param refreshToken
 */
public record TokenResponseDTO(String accessToken, String refreshToken) {
}
