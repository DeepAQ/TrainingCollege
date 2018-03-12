package cn.imaq.trainingcollege.util;

import cn.imaq.trainingcollege.config.Sensitive;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;

public class JWTUtil {
    private static ObjectMapper jsonMapper = new ObjectMapper();

    public static String sign(Object object) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .addClaims(Collections.singletonMap("obj", object))
                .signWith(SignatureAlgorithm.HS256, Sensitive.JWT_KEY)
                .compact();
    }

    public static Object parse(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(Sensitive.JWT_KEY)
                .parseClaimsJws(jwt)
                .getBody();
        if (!claims.containsKey("obj")) {
            return null;
        }
        return claims.get("obj");
    }

    public static <T> T parse(String jwt, Type type) {
        return jsonMapper.convertValue(parse(jwt), jsonMapper.constructType(type));
    }
}
