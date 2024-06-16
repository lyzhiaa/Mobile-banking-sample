package co.istad.mobilebanking.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record VerificationRequest(
        @NotBlank(message = "Email is require!!")
        String email,
        @NotBlank(message = "Verified code is require!!")
        String verifiedCode
) {
}
