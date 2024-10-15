package healthmanagementsystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret; // Secret key for signing the JWT

    @Value("${jwt.expiration}")
    private Long expiration; // Expiration time for the JWT in seconds

    // Method to generate a token using user ID and role
    public String generateAccessToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Add role as claim
        return doGenerateToken(claims, userId);
    }

    // Method to generate a refresh token using user ID
    public String generateRefreshToken(String userId) {
        return doGenerateToken(new HashMap<>(), userId);
    }

    // Method to generate a token with specified claims and subject
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // Set user ID as subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set issue date
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // Set expiration date
                .signWith(SignatureAlgorithm.HS512, secret) // Sign the token
                .compact(); // Build the token
    }

    // Method to validate the token using user ID
    public Boolean validateToken(String token, String userId) {
        final String extractedUserId = getUserIdFromToken(token); // Extract user ID from token
        return (extractedUserId.equals(userId) && !isTokenExpired(token)); // Check if token is valid
    }

    // Method to extract user ID from the token
    public String getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject(); // Get user ID from claims
    }

    // Method to retrieve all claims from the token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret) // Set the signing key
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get claims from the token
    }

    // Method to check if the token is expired
    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getAllClaimsFromToken(token).getExpiration(); // Get expiration date
        return expirationDate.before(new Date()); // Check if the current date is after the expiration date
    }
}
