package com.forohub_alura.Foro_hub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.forohub_alura.Foro_hub.domain.user.User;
import com.forohub_alura.Foro_hub.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private User user;
    @Autowired
    private UserRepository userRepository;

    /**
     * Generates a JWT token for the given user.
     * @param user The user object for whom the token is generated.
     * @return JWT token as a String.
     */
    public String generateToken(User user){
        try {
            // Algorithm for signing the token with HMAC256 using user's password hash
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(user.getUsername())
                    .withClaim("id",user.getId())
                    .withExpiresAt(expirationdate())
                    .sign(algorithm);// Sign the token with the algorithm
        } catch (JWTCreationException exception) {
            throw new RuntimeException();
        }
    }
    /**
     * Retrieves the subject (username) from the JWT token.
     * @param token JWT token string.
     * @return Username (subject) extracted from the token.
     */
    public String getSubject(String token){
        if (token == null) {
            throw new IllegalArgumentException("Token is null");
        }

        try {
            // Decode the JWT token
            DecodedJWT decodedJWT = JWT.decode(token);
            String username = decodedJWT.getSubject();// Extract username from the token
            if (username == null) {
                throw new IllegalArgumentException("Invalid token: Subject not found");
            }
            // Retrieve the user from UserRepository based on username
            User user = (User) userRepository.findByUsername(username);
            if (user == null) {
                throw new IllegalArgumentException("User not found for username: " + username);
            }

            // Build the Algorithm with user's password hash and verify the token
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(token);// Verify the token using the algorithm

            // Return the verified subject (username)
            return verifier.getSubject();
        } catch (JWTVerificationException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid token: " + e.getMessage(), e);
        }

    }
    /**
     * Calculates the expiration date for JWT token (2 hours from now).
     * @return Instant representing the expiration date.
     */
    private Instant expirationdate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
