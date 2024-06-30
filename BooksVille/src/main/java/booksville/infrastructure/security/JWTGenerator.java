package booksville.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTGenerator {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final Logger LOG = LoggerFactory.getLogger(JWTGenerator.class);

    public String generateToken(Authentication authentication, Long milliSeconds) {
        String email = authentication.getName();

        return generateNewToken(email, milliSeconds);
    }

    public String generateSignUpVerificationToken(String email, Long milliSeconds){
        Date currentDate = new Date();
        Date expireDate = new Date(System.currentTimeMillis() + milliSeconds);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }

    private String generateNewToken(String email, Long milliSeconds){

        LOG.info("User with email: {} logged in", email);

        Date currentDate = new Date();
        Date expireDate = new Date(System.currentTimeMillis() + milliSeconds);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Jwt Token Validator
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parse(token);

            return true;
        } catch (MalformedJwtException e) {
            LOG.warn("Invalid JWT Token");
            throw new JwtException("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            LOG.warn("Expired JWT Token");
            throw new JwtException("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            LOG.warn("Unsupported JWT Token");
            throw new JwtException("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            LOG.warn("JWT Claims string is empty");
            throw new JwtException("JWT Claims string is empty");
        }
    }
}
