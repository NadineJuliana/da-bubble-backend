package da_bubble.backend.auth.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUerDTO(

        @NotBlank(message = "First name is required!")
        @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters!")
        String firstName,

        @NotBlank(message = "Last name is required!")
        @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters!")
        String lastName,

        @NotBlank(message = "Email is required!")
        @Email(message = "Invalid email!")
        @Size(max = 255, message = "Email is too long!")
        String email,

        @NotBlank(message = "Password is required!")
        @Size(min = 8, max = 60, message = "Password must be between 8 and 50 characters!")
        String password
) {
}
