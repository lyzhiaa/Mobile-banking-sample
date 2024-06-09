package co.istad.mobilebanking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;

    @Bean
    InMemoryUserDetailsManager configurationUserSecurity() {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin@1234"))
                .roles("USER", "ADMIN")
                .build();
        UserDetails editor = User
                .withUsername("editor")
                .password(passwordEncoder.encode("editor@123"))
                .roles("USER", "EDITOR")
                .build();
        UserDetails subscriber = User
                .withUsername("customer")
                .password(passwordEncoder.encode("subscriber@123"))
                .roles("USER", "CUSTOMER")
                .build();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(admin);
        manager.createUser(editor);
        manager.createUser(subscriber);

        return manager;
    }
    @Bean
    SecurityFilterChain configureApiSecurity(HttpSecurity httpSecurity) throws Exception {
        //endpoint security config
        httpSecurity.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers(HttpMethod.POST, "api/v1/accounts/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.GET, "api/v1/accounts/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/accounts/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/accounts/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/accounts/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.POST, "api/v1/account-types/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "api/v1/accounts-types/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/accounts-types/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/accounts-types/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/accounts/**").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().authenticated());

        //Security Mechanism (HTTP Basic Auth)
        //http basic auth (username & password)
        httpSecurity.httpBasic(Customizer.withDefaults());

        //disable CSRF (Cross site request forgery) token
        httpSecurity.csrf(token -> token.disable());
        //Make Stateless Session
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
}
