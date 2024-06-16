package co.istad.mobilebanking.features.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String email,
        String message
) {
}
