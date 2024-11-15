package com.CodeWithSumit.Task_Management.Utiles;


import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import java.util.Date;
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserRepository userRepository;


    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);

    }

    private String generateToken(Map<String,Object> extractClaims, UserDetails userDetails) {
      return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
           .setIssuedAt(new Date(System.currentTimeMillis()))
           .setExpiration(new Date(System.currentTimeMillis()*1000 * 60* 60 *24))
           .signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();
           
    }

    private Key getSigningKey() {
       byte[] keyBytes=Decoders.BASE64.decode("413F4428472B4B6250655368566D5978337336763979244226452948404O6351");
       return Keys.hmacShaKeyFor(keyBytes);

    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username =extractUserName(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    public String extractUserName(String token) {

        return extractClaim(token,Claims::getSubject);

    }
    private boolean isTokenExpired(String token) {
    
        return extractExpiration(token).before(new Date());
    }        
       
    private Date extractExpiration(String token) {
      
        return extractClaim(token,Claims::getExpiration);
       
    }

    private <T> T extractClaim(String token,Function<Claims,T> ClaimsResolvers){
        final Claims claims=extractAllClaims(token);
        return ClaimsResolvers.apply(claims);

    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
        .getBody();

    }

    public User get

    
}
