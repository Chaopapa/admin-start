package com.le.core.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

/**
 * jwt加密工具类
 */
@Slf4j
public class JwtUtils {
    private static String JWT_SECRET = "menhi1234567";
    /**
     * 生成jwt字符串
     * @param userId 用户id
     * @param roles 角色id
     * @param TTlSecond 有效时长
     * @return jwt字符串
     */
    public  static  String createJWt(Long userId, ArrayList roles, Long TTlSecond){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);

        byte[] keyBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);
        Key signKey = new SecretKeySpec(keyBytes, signatureAlgorithm.getJcaName());
        //此处JwtBuilder是jwt包提供的用于生成jwt字符串的构造类，“setHeaderParam”表示设置jwt预定义字段的值，“claim”表示设置自定义字段及值
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("useId",userId)
                .claim("roles",roles)
                .signWith(signatureAlgorithm,signKey);
        if(TTlSecond>0){
            Date expTime = new Date(nowMills+1000*TTlSecond);
            builder.setExpiration(expTime).setNotBefore(now);
        }
        return builder.compact();
    }
    /**
     * 解析jwt字符串
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        try {
            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(JWT_SECRET)).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("jwt 过期！", e);
            return null;
        } catch (SignatureException e) {
            log.error("jwt 解码失败！", e);
            return null;
        } catch (Exception e) {
            log.error("jwt 未知异常！",e);
            return null;
        }
    }
}
