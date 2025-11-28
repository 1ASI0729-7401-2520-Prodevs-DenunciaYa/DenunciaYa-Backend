package com.denunciayabackend.iam.application.internal.outboundservices.tokens;

/**
 * TokenService
 * <p>
 *     Interface for token service. This service is used to generate, validate and extract username from tokens.
 * </p>
 */
public interface TokenService {


    String generateToken(String username);


    String getUsernameFromToken(String token);

    boolean validateToken(String token);
}
