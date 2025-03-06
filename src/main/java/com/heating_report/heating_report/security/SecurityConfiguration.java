package com.heating_report.heating_report.security;


import com.heating_report.heating_report.security.filters.JwtTokenFilter;
import com.heating_report.heating_report.security.utils.CustomSecurity;
import com.heating_report.heating_report.security.utils.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;
    private final CustomSecurity customSecurity;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/auth/**").permitAll()
//                        .requestMatchers("/api/v1/users").hasRole("ADMINISTRATOR")
//                        .requestMatchers(HttpMethod.GET, "/api/v1/users/me").fullyAuthenticated()
//                        .requestMatchers("/api/v1/users/{id}", "/api/v1/users/{id}/password").access((authetication, context) -> {
//                            boolean isAdmin = customSecurity.isUserAdmin(authentication);
//                            boolean isUserHasTheSameId = customSecurity.hasUserAccessToUserId(authentication.get().getName(), Integer.parseInt(context.getVariables().get("id")));
//                            return new AuthorizationDecision(isUserHasTheSameId || isAdmin);
//                        })
//                        .requestMatchers("/api/v1/users/*/role/*").hasRole("ADMINISTRATOR")
                                .anyRequest().permitAll()
                )
                .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}