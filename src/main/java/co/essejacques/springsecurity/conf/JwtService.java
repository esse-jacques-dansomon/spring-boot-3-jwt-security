package co.essejacques.springsecurity.conf;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Author  : essejacques.co
 * Date    : 27/02/2023 23:13
 * Project : spring-security
 */

@Service
public class JwtService {
    private static final String SECRET_KEY = "7538782F413F4428472B4B6250655368566D5971337436773979244226452948";

    public String getUserEmailFromToken(String jwt) {
        return getClaimFromToken(jwt, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSecretKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
        //(System.currentTimeMillis() + 1000 * 60 * 60 * 10) = 10 hours
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
        //(System.currentTimeMillis() + 1000 * 60 * 60 * 10) = 10 hours
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        final String username = getUserEmailFromToken(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        return getExpirationDateFromToken(jwt).before(new java.util.Date());
    }

    private Date getExpirationDateFromToken(String jwt) {
        return getClaimFromToken(jwt, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return
                Jwts
                        .parserBuilder()
                        .setSigningKey(getSecretKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
