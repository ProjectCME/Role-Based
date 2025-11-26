package com.example.roleapp.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .addFilterBefore(new RedirectFilter(), AnonymousAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth

                        // Public Endpoints
                        .requestMatchers(
                                "/actuator/health",
                                "/login",
                                "/register",
                                "/send-login-otp",
                                "/send-reg-otp",
                                "/css/**",
                                "/js/**",
                                "/403")
                        .permitAll()

                        // Student + Admin
                        .requestMatchers("/student/**")
                        .access(roleAccess("STUDENT", "ADMIN"))

                        // Teacher + Admin
                        .requestMatchers("/teacher/**")
                        .access(roleAccess("TEACHER", "ADMIN"))

                        // Admin only
                        .requestMatchers("/admin/**")
                        .access(roleAccess("ADMIN"))

                        .anyRequest().authenticated())

                // 403 Page
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((req, res, exc) -> {
                            res.sendRedirect("/403");
                        }))

                // Disable default login
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }

    private AuthorizationManager<RequestAuthorizationContext> roleAccess(String... allowedRoles) {
        return (authentication, context) -> {
            HttpSession session = context.getRequest().getSession(false);

            if (session == null)
                throw new AccessDeniedException("No session");

            String userRole = (String) session.getAttribute("userRole");

            for (String role : allowedRoles) {
                if (role.equals(userRole)) {
                    return new AuthorizationDecision(true);
                }
            }

            // Force Spring to trigger /403 redirection
            throw new AccessDeniedException("Permission denied");
        };
    }
}
