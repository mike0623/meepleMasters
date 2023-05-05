package tw.com.eeit162.meepleMasters.jack.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

@Service
public class JwtService {
	private String secretKey = "753778214125442A472D4B6150645367566B59703273357638792F423F452848";
	private int jwtExpiration = 1000*60*10;
	
	private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
	            .setSigningKey(getSignInKey())
	            .build()
	            .parseClaimsJws(token)
	            .getBody();	
				
	}
	
	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String generateToken(Member user) {
        return generateToken(new HashMap<>(), user);
    }
	
	public String generateToken(Map<String, Object> extraClaims,Member member) {
        return createToken(extraClaims, member, jwtExpiration);
    }
	
	public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
	
	public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

	private String createToken(Map<String, Object> extraClaims, Member member, int expiration) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(member.getMemberEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
}
