package com.project.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	// GENERATE TOKEN (1A)
	public String generateToken(String username, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		return createToken(claims, username);
	}

	// CREATE TOKEN | HOW A TOKEN WILL BE CREATED | WITH WHAT PROPERTIES (1B)
	private String createToken(Map<String, Object> claims, String subject) {
		// here subject is actually username
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	// VALIDATE TOKEN (2A)
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

	}

	// 3A
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// 4A
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	// 5A
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

	// 4B 5B
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// 4C 5C
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// 4D 5D
	private String secret = "CafManSys";

}
