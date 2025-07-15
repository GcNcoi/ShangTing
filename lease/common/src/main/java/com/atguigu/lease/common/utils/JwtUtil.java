package com.atguigu.lease.common.utils;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @BelongsProject: lease
 * @BelongsPackage: com.atguigu.lease.common.utils
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-14  22:57
 * @Description: TODO
 * @Version: 1.0
 */
public class JwtUtil {

    private static long tokenExpiration = 60 * 60 * 1000L;

    private static SecretKey tokenSignKey = Keys.hmacShaKeyFor("fLYaYYBrn2fjPIMBNPapUfmUkZLAPdUm".getBytes());

    public static String createToken(Long userId, String username) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .setSubject("LOGIN_USER")
                .claim("userId", userId)
                .claim("username", username)
                .signWith(tokenSignKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims parseToken(String token) {
        if (token == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(tokenSignKey).build();
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }
    }

}
