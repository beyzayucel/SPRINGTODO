package com.haratres.todo.config;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Date;

@Component
public class TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;

    public String createToken(Authentication authentication){
        String authorities=authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256,SIGNING_KEY)
                .compact();

    }

    public String resolveToken(HttpServletRequest httpServletRequest){
        String bearerToken=httpServletRequest.getHeader("Authorization");
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
    log.warn("Authorization header bulunamadı veya 'Bearer ' ile başlamıyor.");
    return null;
    }

    public String getEmailFromToken(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();

    }

   public Boolean validateToken(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
        Date expiration= claims.getExpiration();
        return !expiration.before(new Date());
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token, UserDetails userDetails) {
        Claims claims=Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();

        String authoritiesClaim = (String) claims.get(AUTHORITIES_KEY);
        if (authoritiesClaim == null || authoritiesClaim.trim().isEmpty()) {
            authoritiesClaim = "ROLE_USER";
        }

        List<GrantedAuthority> authorities = Arrays.stream(authoritiesClaim.split(","))
                .map(String::trim)
                .filter(role -> !role.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

}
