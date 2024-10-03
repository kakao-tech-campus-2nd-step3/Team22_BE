package io.github.eappezo.soundary.services.authentication.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eappezo.soundary.core.exception.ErrorResponse;
import io.github.eappezo.soundary.services.authentication.domain.exception.AuthenticationErrorCode;
import io.github.eappezo.soundary.services.authentication.domain.exception.AuthenticationFailedException;
import io.github.eappezo.soundary.services.authentication.infrastructure.APIAuthentication;
import io.github.eappezo.soundary.services.authentication.infrastructure.JwtTokenExtractor;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static io.github.eappezo.soundary.core.ExceptionUtil.stackTraceOf;


@Component
@Slf4j
@RequiredArgsConstructor
public class CustomJwtFilter extends OncePerRequestFilter {
    @Value("${app.headers.auth-token-header}")
    private String authTokenHeader;
    private final JwtTokenExtractor jwtTokenExtractor;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = getBearerToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            APIAuthentication authentication = jwtTokenExtractor.extractAuthenticationFrom(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException exception) {
            writeAuthenticationErrorResponse(response, AuthenticationErrorCode.AUTHENTICATION_EXPIRED);
            return;
        } catch (JwtException | AuthenticationFailedException exception) {
            writeAuthenticationErrorResponse(response, AuthenticationErrorCode.AUTHENTICATION_FAILED);
            return;
        } catch (Exception exception) {
            log.error(stackTraceOf(exception));
            writeAuthenticationErrorResponse(response, AuthenticationErrorCode.AUTHENTICATION_FAILED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getBearerToken(HttpServletRequest request) {
        String token = request.getHeader(authTokenHeader);
        if (token == null) {
            return null;
        }
        if (!token.startsWith("Bearer ")) {
            throw new AuthenticationFailedException();
        }
        return token.substring(7);
    }

    private void writeAuthenticationErrorResponse(
            HttpServletResponse response,
            AuthenticationErrorCode errorCode
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(
                ErrorResponse.of(errorCode)
        ));
    }
}
