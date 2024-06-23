package co.istad.mobilebanking.features.auth;

import co.istad.mobilebanking.features.auth.dto.*;
import jakarta.mail.MessagingException;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    void sendVerification(String email) throws MessagingException;

    void reSentVerification(String email) throws MessagingException;
    void verify(VerificationRequest verificationRequest);
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(RefreshTokenCreateRequest refreshTokenRequest);
}
