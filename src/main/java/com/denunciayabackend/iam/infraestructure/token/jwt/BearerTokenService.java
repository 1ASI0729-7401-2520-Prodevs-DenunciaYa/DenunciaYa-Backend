package com.denunciayabackend.iam.infraestructure.token.jwt;

import com.denunciayabackend.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * The bearer token service.
 * This class is responsible for generating and extracting the bearer token.
 */
public interface BearerTokenService extends TokenService {
    /**
     * Extract the bearer token from the request.
     *
     * @param request the {@link HttpServletRequest} request
     * @return the bearer token
     */
    String getBearerTokenFrom(HttpServletRequest request);

    /**
     * Generate the bearer token.
     *
     * @param authentication the {@link Authentication} authentication
     * @return the bearer token
     */
    String generateToken(Authentication authentication);
}
