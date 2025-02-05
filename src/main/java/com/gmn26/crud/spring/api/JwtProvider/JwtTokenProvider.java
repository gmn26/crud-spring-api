package com.gmn26.crud.spring.api.JwtProvider;

import com.gmn26.crud.spring.api.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.security.Key;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {
    private String jwtSecret = "74ba88e3be86e9022ec42ae0a20f2da862e6a1f7db7d22ab35588c6229449e712132b3cc7cb0dd970f6a4d992fafeeaf5171cffe5bc4f6194dca72c26d9d0570dde3f4b62c5997c333c20261c6edbea1d29f0592e19aa7a17f7e6b14bd22d4ee721f9d12ae3ea4412577f2c82195d68be76bc487ea401085e1403ef4fb0257ab2b33246c8df733e2e45ce996d31cc898ec319bc4db56c4ee85e89b8aeec7986869caa4cdfa6d373e214f72103b5c85fb3cf225c38cd59125582dad7d62e40981bdeffc7f619255a6182261a38918a69e334faab1a8f5f5b7c666b7eeee55ea84545602d163c790b5cc85960407c37209a58242ea8243f90dbacb076ae1bdae39";
    private long jwtExpirationTime = 3600;
    public String getJwtSecret(Authentication authentication) {
        String username = authentication.getName();

//        CustomUserDetailService userDetailService = (CustomUserDetailService) authentication.getPrincipal();
//        UUID userId = userDetailService.getId();

//        log.error("ID User : {}", userId);

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationTime * 1000);

        return Jwts.builder()
                .subject(username)
//                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("userId", UUID.class);
    }

    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
        return true;
    }
}
