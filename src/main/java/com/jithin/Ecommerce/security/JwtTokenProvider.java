package com.jithin.Ecommerce.security;

import com.jithin.Ecommerce.models.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {


    public static final String SECRET_KEY = "mySuper_Secret#6w876re";
    private static final long TOKEN_EXPIRATION_TIME = 999999999;

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("invalid signature");
        } catch (MalformedJwtException ex) {
            System.out.println("invalid jwt token");
        } catch (ExpiredJwtException ex) {
            System.out.println("token expired");
        } catch (UnsupportedJwtException ex) {
            System.out.println("un supported jwt");
        } catch (IllegalArgumentException ex) {
            System.out.println("illegal argument");
        }

        return false;
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

        String id = (String) claims.get("id");

        return id;
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());

        return Jwts.builder()
                .setSubject(user.getId())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
