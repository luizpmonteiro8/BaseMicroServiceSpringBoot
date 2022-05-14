package br.com.erudio.util;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
	public class JwtUtil {  
	  

	public Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey("123456".getBytes()).parseClaimsJws(token.substring(7)).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}

	    
	
}
