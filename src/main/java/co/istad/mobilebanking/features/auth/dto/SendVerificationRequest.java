package co.istad.mobilebanking.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SendVerificationRequest(
        @NotBlank(message = "Email is require!!!")
        String email
) {
}
