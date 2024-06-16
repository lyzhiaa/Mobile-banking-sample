package co.istad.mobilebanking.features.auth;

import co.istad.mobilebanking.features.auth.dto.RegisterRequest;
import co.istad.mobilebanking.features.auth.dto.RegisterResponse;
import co.istad.mobilebanking.features.auth.dto.VerificationRequest;
import jakarta.mail.MessagingException;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    void sendVerification(String email) throws MessagingException;

    void reSentVerification(String email) throws MessagingException;
    void verify(VerificationRequest verificationRequest);
}
