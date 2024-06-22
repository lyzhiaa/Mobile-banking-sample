package co.istad.mobilebanking.features.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        //Token type
        String tokenType,
        String accessToken,
        String refreshToken
) {
}
