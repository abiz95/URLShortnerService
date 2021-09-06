package com.service.main.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

    private String secret = "myfatherwasjewishcarpenter";

    public String extractUsername(String token) {
    	
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
    	
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    	
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
    	
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
    	
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, String userId) {
    	
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return createToken(claims, username);
    }
    
	public String generateRefreshToken(String tokenParam) throws Exception {

		Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(tokenParam)
                .getBody();

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		return createRefreshToken(expectedMap, expectedMap.get("sub").toString());
	}

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
    	
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
	public String createRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(Claims claims) {
		
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
	
}
