package com.denunciayabackend.iam.infraestructure.authorization.sfs.pipeline;


import com.denunciayabackend.iam.infraestructure.authorization.sfs.model.UserDetailsImpl;
import com.denunciayabackend.iam.infraestructure.authorization.sfs.model.UsernamePasswordAuthenticationTokenBuilder;
import com.denunciayabackend.iam.infraestructure.token.jwt.BearerTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BearerAuthorizationRequestFilter.class);
    private final BearerTokenService tokenService;
    @Qualifier("defaultUserDetailsService")
    private final UserDetailsService userDetailsService;

    /**
     * Constructor
     * @param tokenService {@link BearerTokenService} Bearer token service
     * @param userDetailsService {@link UserDetailsService} User details service
     */
    public BearerAuthorizationRequestFilter(BearerTokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Skip authorization for preflight OPTIONS requests so the browser CORS preflight isn't blocked
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            LOGGER.debug("Skipping auth filter for preflight OPTIONS request");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var token = tokenService.getBearerTokenFrom(request);
            LOGGER.info("Token: {}", token);
            if (Objects.nonNull(token) && tokenService.validateToken(token)) {
                var username = tokenService.getUsernameFromToken(token);
                var userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
                SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationTokenBuilder.build(userDetails, request));
            } else {
                LOGGER.warn("Token is not valid");
                // Si el token es null, loguear todos los headers para diagnosticar por qué no llega el Authorization
                if (token == null) {
                    try {
                        var headerNames = request.getHeaderNames();
                        if (headerNames != null) {
                            LOGGER.debug("Request headers:");
                            while (headerNames.hasMoreElements()) {
                                var name = headerNames.nextElement();
                                LOGGER.debug("{}: {}", name, request.getHeader(name));
                            }
                        } else {
                            LOGGER.debug("No header names available (null). This could be a preflight OPTIONS or container filtering headers.");
                        }
                    } catch (Exception e) {
                        LOGGER.warn("Could not read request headers for debugging: {}", e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
