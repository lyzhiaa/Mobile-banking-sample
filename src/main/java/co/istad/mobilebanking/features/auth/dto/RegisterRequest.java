package co.istad.mobilebanking.features.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.PathVariable;

public record RegisterRequest(
        @NotBlank(message = "Phone number is require!")
                @Size(min = 9, max = 10, message = "Phone number must be 9 or 10 digits!")
        String phoneNumber,
        @NotBlank(message = "Mail is require!")
        String email,
        @NotBlank(message = "Pin is require!")
                @Size(min = 3, max = 4, message = "Pin must be 3 or 4 digits!")
        String pin,
        @NotBlank(message = "Password is require!")
                @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
                        message = "Password must be at least 8 characters long," +
                                " include an uppercase letter," +
                                " a lowercase letter, a number," +
                                " and a special character.")// regular expression
        String password,
        @NotBlank(message = "ConfirmPassword is require!")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
                message = "Password must be at least 8 characters long," +
                        " include an uppercase letter," +
                        " a lowercase letter, a number," +
                        " and a special character.")// regular expression
        String confirmedPassword,
        @NotBlank(message = "National card ID is require!")
        String nationalCardId,
        @NotBlank(message = "Name is require!")
        String name,
        @NotBlank(message = "Gender is require!")
        String gender,
        @NotNull(message = "Term must be accept!")
        Boolean acceptTerm
) {
}
