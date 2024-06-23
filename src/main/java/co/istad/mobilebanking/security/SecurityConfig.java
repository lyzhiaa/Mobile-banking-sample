package co.istad.mobilebanking.security;

import co.istad.mobilebanking.security.jwt.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

//    @Bean
//    InMemoryUserDetailsManager configurationUserSecurity() {
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("admin@1234"))
//                .roles("USER", "ADMIN")
//                .build();
//        UserDetails editor = User
//                .withUsername("editor")
//                .password(passwordEncoder.encode("editor@123"))
//                .roles("USER", "EDITOR")
//                .build();
//        UserDetails subscriber = User
//                .withUsername("customer")
//                .password(passwordEncoder.encode("subscriber@123"))
//                .roles("USER", "CUSTOMER")
//                .build();
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(admin);
//        manager.createUser(editor);
//        manager.createUser(subscriber);
//
//        return manager;
//    }
    @Bean
    JwtAuthenticationProvider configJwtAuthenticationProvider(@Qualifier("refreshTokenJwtDecoder") JwtDecoder refreshTokenJwtDecoder) {
//        JwtAuthenticationProvider auth = new JwtAuthenticationProvider(refreshTokenJwtDecoder);
//        return auth;
        return new JwtAuthenticationProvider(refreshTokenJwtDecoder);
    }
    @Bean
    DaoAuthenticationProvider configDaoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);

        return auth;
    }

    @Bean
    SecurityFilterChain configureApiSecurity(HttpSecurity httpSecurity, @Qualifier("accessTokenJwtDecoder") JwtDecoder jwtDecoder) throws Exception {
        //endpoint security config
        httpSecurity.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers("/api/v1/auth/**", "/api/v1/upload/**", "/upload/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "api/v1/accounts/**").hasAnyRole("USER")
//                .requestMatchers(HttpMethod.GET, "api/v1/accounts/**").hasAuthority("SCOPE_ROLE_USER")
//                .requestMatchers(HttpMethod.DELETE, "/api/v1/accounts/**").hasAnyRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/api/v1/accounts/**").hasAnyRole("USER")
//                .requestMatchers(HttpMethod.PATCH, "/api/v1/accounts/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/account-types/**").hasAnyAuthority("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/accounts-types/**").hasAuthority("SCOPE_USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/accounts-types/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/accounts-types/**").hasAnyAuthority("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/accounts/**").hasAnyAuthority("ADMIN", "MANAGER")
                .anyRequest().authenticated());

        //Security Mechanism (HTTP Basic Auth)
        //http basic auth (username & password)

        //httpSecurity.httpBasic(Customizer.withDefaults());

        //Security Mechanism (JWT)
        //for single @Bean
//        httpSecurity.oauth2ResourceServer(jwt -> jwt.jwt(Customizer.withDefaults()));
        httpSecurity.oauth2ResourceServer(jwt -> jwt.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)));

        //disable CSRF (Cross site request forgery) token
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //Make Stateless Session
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
}
