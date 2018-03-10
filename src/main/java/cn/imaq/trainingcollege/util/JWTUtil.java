package cn.imaq.trainingcollege.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtil {
    public static String sign(Map<String, Object> claims) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, Sensitive.JWT_KEY)
                .compact();
    }

    public static Map<String, Object> parse(String jwt) {
        return Jwts.parser()
                .setSigningKey(Sensitive.JWT_KEY)
                .parseClaimsJwt(jwt)
                .getBody();
    }
}
