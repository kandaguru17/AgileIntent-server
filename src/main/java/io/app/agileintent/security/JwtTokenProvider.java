package io.app.agileintent.security;

import static io.app.agileintent.security.SecurityConstants.JWT_EXPIRY;
import static io.app.agileintent.security.SecurityConstants.JWT_SECRET;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.app.agileintent.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	//token generation using the authentication obj
	public String generateToken(Authentication auth) throws ParseException {

		User user = (User) auth.getPrincipal();

		Date now = new Date(System.currentTimeMillis());
		Date Expiry = new Date(System.currentTimeMillis() + JWT_EXPIRY);

		// claims are the detail we need to extract from the jwt token
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("username", user.getUsername());
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());

		return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(now).setExpiration(Expiry)
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
		
	}

	
	//validate token parses the token and return true if token is valid
	public boolean ValidateToken(String token) {
		try {
			//parsing the token using the parseclaimsjws() because its signed and is a jws
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			System.out.println("Signature is invalid");
		} catch (MalformedJwtException e) {
			System.out.println("token is invalid");
		} catch (ExpiredJwtException e) {
			System.out.println("token is expired");
		} catch (UnsupportedJwtException e) {
			System.out.println("Invalid Jwt format");
		}catch(IllegalArgumentException e) {
			System.out.println("Illegal arguement as token");
		}

		return false;
	}

	
	public String getUserNameFromJwt(String jwt) {
		Claims claims=Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwt).getBody();
		String username =(String) claims.get("username");
		return username;
	}
	
	
	
	
}
