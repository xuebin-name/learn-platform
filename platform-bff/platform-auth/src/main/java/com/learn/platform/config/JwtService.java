package com.learn.platform.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName JwtService
 * @Description jwt token 工具类
 * @Author xue
 * @Date 2024/1/4 16:40
 */

@Configuration
public class JwtService {
    @Value("${jwt.jwtSecret}")
    private  String jwtSecret; // 从配置文件或环境变量中获取密钥
    @Value("${jwt.jwtExpirationInMs}")
    private  long jwtExpirationInMs; // JWT过期时间，这里设置为1小时

    String siyao="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC3NBpHBqxDGe0XtsH7u33rdL5U+8G+krpUpmh7+aWBB2IY/qR6KLNsrtjC7mhkXXd72l1bax02HUamTM3QqWTnMcW9dB6NwSNTtWGeyMkv+4gNyd8LYL//LXighevINvUACao31a28CAoh0nmurCInB8J6TbP69wqj4tGVzLbhveHvuMqqPXWrEx6WVAVNIOmGPSN9xFDWLzEv4K9fjUEzkaS2ZVB+lMvLZ+8m2jA5N8imF/E5MtbWGIIokBEdHx8MAh2GYglU/72zNrH21skuBkqJxbX8VQRkX+uS4fat+apZa0jxGH7KZDVya4L1BODex0PrqzvUaGSYfTVpYGbnAgMBAAECggEBAKhlbAH9UDodiqFRi90n6gaTuJ/FCcFyfXNxwniycD652qG+7tCauNIdpYEIQ0x93WYqwcVWH/Ot1VLnJge2rNkWFvydz7fc3+wBvUmGpedE/TovZKXAuLqFwWtLrf7LBUiTmbLY5mj/utB79v1nm7zklQ52fgkhKtaZlqxrD29O28BDKE31QqnC5GJONv6U7rFHYU9cf252VdaLCHB7a3GqUlERpHvVqFpcGfPYyDzAFghPBq2jdmGHczPTPhhT7y9JOGP7y3378b8e0aLG2A+ijEnJy2Yq2DtlW72mV8zxUE7osdXRmo7bSHPRpNIWnTqGPGYpfsTRaqp7/yx0Z4ECgYEA5EzX0MOCLLcFkrNm4fAsXqt2ZEDm9TNUgQ8yFAIyUCPiY+PYVjBR+XkXQrOX2RdbqBvR9ZvM74gHiFJMs8u0FNZgj5ojvFD0wQs6JgqcckRn1qvV3Y/hgmuc67FwAUhLn2LUJOe+D+kmHwx0UgbAX8NhlwDFXrDpJ2R8bvzppoMCgYEAzW6HDQDIrWK5g0wI+ffL5KYwzsemsgLFLvN2Pl3zZy4wj+t86Ew+/v51cMBJFqZYnRIa8lBRJegmyDiOWsDI6Js/VmIPdGlBYkLgQfARSusAc0VDUiJg4ksueAO0o2GyisrU4HTFLuUX6VJXGE0A66ZdFCYYoojO7ljChNdEsM0CgYBpCC1V/0x5U+59cooHsl5HY60BLJbVAjuDcMFCUUxicE/sUtups2tTUfFwn31hxAxICByNWZNsM/H7NpnRMgt0drhP+MMuFTS1R3wGGmWlcHExYzbuSw6d2PKFwMld3avk9SvSmsdnPIVsTWeKgU6qABJ6zUEAcnWhyOoyiarZDwKBgQCWwuaZxFmB5VH/6W0tBY2837oyBpNvUFkle8brPkfDEULSXB/u2wXgXR4TAQqidLHTk9Xyvr7O8vCDP/443AKVsllpK83rwNMbKbkqquQF9zD9Z3dgb5pqeIJB0XZf8PL2qKRRlWCgacCCmssQKnLifdURVGP/5Cb5BGq80r62gQKBgQCNIFkhD5h+J8hpy9NV6g8cjqUZve3fTcnD6ofGWRW/8O4iU1rAMuQJIVy7Y4YqesLyGNoM2UjUQIIVzpJh2B/QOT6OLuVbFTHgIDc8HxYrMHiGe6cBK/4SdmdjZLPxLR7YLzinbfnRr0KJzU08Vg/3Y0ssFEvVzPm9QAXNPyvj5A==";
    

    private SecretKey secret;

    @PostConstruct void initsecret(){
        secret = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = calculateExpirationDate(now);
        claims.put("iat", nowMillis); // Issued At: 当前时间戳
        claims.put("exp", jwtExpirationInMs); // Expiration Time: 过期时间戳
        claims.put("sub", subject); // Subject: 主题，这里是用户名或用户ID
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.RS512, jwtSecret) // 使用HS512算法和密钥签名JWT令牌
                .compact(); // 将JWT令牌转换为紧凑格式字符串（Base64编码）返回
    }

    private Date calculateExpirationDate(Date date) {
        return new Date(date.getTime() + jwtExpirationInMs); // 计算过期时间并返回新的日期对象
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public  boolean validateToken(String token) {
        try {
            Jwts.builder().claims();
            Jwts.parser().verifyWith(secret).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 初始化负载内数据
     * @param username 用户名
     * @return 负载集合
     */
    private Map<String,Object> initClaims(String username){
        Map<String, Object> claims = new HashMap<>();
        //"iss" (Issuer): 代表 JWT 的签发者。在这个字段中填入一个字符串，表示该 JWT 是由谁签发的。例如，可以填入你的应用程序的名称或标识符。
        claims.put("iss","jx");
        //"sub" (Subject): 代表 JWT 的主题，即该 JWT 所面向的用户。可以是用户的唯一标识符或者其他相关信息。
        claims.put("sub",username);
        //"exp" (Expiration Time): 代表 JWT 的过期时间。通常以 UNIX 时间戳表示，表示在这个时间之后该 JWT 将会过期。建议设定一个未来的时间点以保证 JWT 的有效性，比如一个小时、一天、一个月后的时间。
        claims.put("exp",generatorExpirationDate());
        //"aud" (Audience): 代表 JWT 的接收者。这个字段可以填入该 JWT 预期的接收者，可以是单个用户、一组用户、或者某个服务。
        claims.put("aud","internal use");
        //"iat" (Issued At): 代表 JWT 的签发时间。同样使用 UNIX 时间戳表示。
        claims.put("iat",new Date());
        //"jti" (JWT ID): JWT 的唯一标识符。这个字段可以用来标识 JWT 的唯一性，避免重放攻击等问题。
        claims.put("jti", UUID.randomUUID().toString());
        //"nbf" (Not Before): 代表 JWT 的生效时间。在这个时间之前 JWT 不会生效，通常也是一个 UNIX 时间戳。我这里不填，没这个需求
        return claims;
    }
    /**
     * 生成失效时间，以秒为单位
     *
     * @return 预计失效时间
     */
    private Date generatorExpirationDate()
    {
        //预计失效时间为：token生成时间+预设期间
        return new Date(System.currentTimeMillis() + jwtExpirationInMs * 1000);
    }

    /**
     * 根据用户信息生成token
     *
     * @param userDetails 用户信息
     * @return token
     */
    public String generatorToken(UserDetails userDetails)
    {
        Map<String, Object> claims = initClaims(userDetails.getUsername());
        return generatorToken(claims);
    }

    /**
     * 根据负载生成JWT token
     * @param claims 负载
     * @return token
     */
    private String generatorToken(Map<String,Object> claims){
        return Jwts.builder()
                .claims(claims)
                .signWith(secret,Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 刷新token
     * @param token 需要被刷新的token
     * @return 刷新后的token
     */
    public String refreshToken(String token){
        Claims claims = getPayloadFromToken(token);
        Map<String, Object> initClaims = initClaims(claims.getSubject());
        initClaims.put("iat",new Date());
        return generatorToken(initClaims);
    }

    /**
     * 从token中获取 Claims
     * @param token token
     * @return Claims
     */
    private Claims getPayloadFromToken(String token)
    {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
