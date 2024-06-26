package co.istad.mobilebanking.features.auth;

import co.istad.mobilebanking.domain.Role;
import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.domain.UserVerification;
import co.istad.mobilebanking.features.auth.dto.*;
import co.istad.mobilebanking.features.user.RoleRepository;
import co.istad.mobilebanking.features.user.UserRepository;
import co.istad.mobilebanking.features.util.RandomUtil;
import co.istad.mobilebanking.mapper.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    private final UserVerificationRepository userVerificationRepository;

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder accessTokenJwtEncoder;

    @Value("${spring.mail.username")
    private String adminEmail;
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        //validate' s user phone number
        if (userRepository.existsByPhoneNumber(registerRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This phone number is already used!!");
        }

        //Validate' s user email
        if (userRepository.existsByEmail(registerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This email is already used!!");
        }

        //Validate ' s user password
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The confirmed password does not match the password!!");
        }

        //Validate' s user national id card
        if(userRepository.existsByNationalCardId(registerRequest.nationalCardId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This national id card is already used!!");
        }

        //Validate term and policy
        if (!registerRequest.acceptTerm()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must accept the term before use!!");
        }

        User user  = userMapper.fromUserRegisterRequest(registerRequest);

        //set system data
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImage("profile/images/default-user.png");
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        user.setIsVerified(false);

        Role roleUser = roleRepository.findRoleUser(); //default role
        Role roleCustomer = roleRepository.findRoleCustomer();
        List<Role> roles = List.of(roleUser, roleCustomer);
        user.setRoles(roles);

        userRepository.save(user);

        return RegisterResponse.builder()
                .message("Registered successfully!!!")
                .email(user.getEmail())
                .build();
    }

    @Override
    public void sendVerification(String email) throws MessagingException {
        //validate email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                        ,"User has not been found!!"));

        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpireTime(LocalTime.now().plusSeconds(20));
        userVerificationRepository.save(userVerification);
        // Prepare mail for sending
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User verification.");
        helper.setText(userVerification.getVerifiedCode());

        Context context = new Context();
        context.setVariable("code", userVerification.getVerifiedCode());

        String  htmlTemplate = templateEngine.process("/card.html", context);
        helper.setText(htmlTemplate, true);
        javaMailSender.send(message);

    }

    @Override
    public void reSentVerification(String email) throws MessagingException {
//validate email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                        ,"User has not been found!!"));

        UserVerification userVerification = userVerificationRepository
                .findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This user has not been found!!!"));
        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpireTime(LocalTime.now().plusSeconds(20));
        userVerificationRepository.save(userVerification);
        // Prepare mail for sending
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User verification.");
        helper.setText(userVerification.getVerifiedCode());

        Context context = new Context();
        context.setVariable("code", userVerification.getVerifiedCode());

        String  htmlTemplate = templateEngine.process("/card.html", context);
        helper.setText(htmlTemplate, true);
        javaMailSender.send(message);
    }

    @Override
    public void verify(VerificationRequest verificationRequest) {
        //validate email
        User user = userRepository.findByEmail(verificationRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                        ,"User has not been found!!"));

        // Validate verified code
        UserVerification userVerification = userVerificationRepository
                .findByUserAndVerifiedCode(user, verificationRequest.verifiedCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User verification has not been found"));

        // Validate is VerifiedCode expire
        if (LocalTime.now().isAfter(userVerification.getExpireTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Verify code is expire!!");
        }
        user.setIsVerified(true);
        userRepository.save(user);

        userVerificationRepository.delete(userVerification);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Authenticate client with username (phoneNumber) and password
        Authentication auth = new UsernamePasswordAuthenticationToken
                (loginRequest.phoneNumber(), loginRequest.password());
        auth = daoAuthenticationProvider.authenticate(auth);

        log.info("Auth: {}", auth.getPrincipal());

        // Generate JWT token by JwtEncoder
        // 1. Define JwtClaimsSet (Payload)
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Access APIs")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .audience(List.of("NextJS", "Android", "iOS"))
                .claim("isAdmin", true)
                .claim("studentId", "ISTAD0010")
                .build();
        // 2. Generate Token
        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
        log.info("Access Token : {}", accessToken);

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .build();
    }

}


