// // For logins and role based access control.
package com.example.roleapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow everything for now
                )
                .formLogin(form -> form.disable()) // no login page
                .httpBasic(httpBasic -> httpBasic.disable()); // no HTTP basic

        return http.build();
    }
}


// THIS IS THE MAIN CODE. IT WILL BE IMPLEMENTED LATER AFTER THE LOGIN FEATURE IS DONE.

// package com.example.roleapp.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

//    @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//     http
//         .csrf(csrf -> csrf.disable())
//         .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/login", "/register", "/public/**").permitAll()
//                 .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                 .requestMatchers("/teacher/**").hasAuthority("TEACHER")
//                 .requestMatchers("/student/**").hasAuthority("STUDENT")
//                 .anyRequest().authenticated()
//         )
//         .formLogin(form -> form
//                 .loginPage("/login")
//                 .defaultSuccessUrl("/redirect", true)
//                 .permitAll()
//         )
//         .logout(logout -> logout.permitAll());

//     return http.build();
// }

// }
