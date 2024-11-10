package io.github.eappezo.soundary.services.authentication.infrastructure.configuration;

import io.github.eappezo.soundary.core.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomJwtFilter customJwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers((configurer) -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(
                        (auth) -> {
                            auth.requestMatchers("/api/login").anonymous();
                            auth.requestMatchers("/api/refresh").anonymous();
                            auth.requestMatchers(HttpMethod.GET, "/api/v1/me").authenticated();
                            auth.requestMatchers("/api/v1/me/default-info").hasRole(UserRole.PENDING.name());
                            auth.requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/exception/**",
                                    "/actuator/**"
                            ).permitAll();
                            auth.anyRequest().hasRole(UserRole.USER.name());
                        }
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((configurer) -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
