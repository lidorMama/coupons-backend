package com.lidor.coupon.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dto.SuccessfulLoginData;
import com.lidor.coupon.exceptions.ServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;


public class JWTUtils {

    public static Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(Constants.JWT_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static String createJWT(SuccessfulLoginData userLoginData) throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();
        String jsonUserData = objectMapper.writeValueAsString(userLoginData);
        String strUserId = String.valueOf(userLoginData.getId());
        String strUserType = String.valueOf(userLoginData.getUserType());
        String strCompanyId = String.valueOf(userLoginData.getCompanyId());
        String token = createJWT(strUserId,strUserType,jsonUserData, strCompanyId , 0 );
        return token;
    }
    private static String createJWT(String id, String userType, String subject, String companyId, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.JWT_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(userType)
                .setAudience(companyId)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Integer getIdByToken(String token) {
        Claims claims = decodeJWT(token);
        return Integer.parseInt(claims.getId());
    }
    public static String getTypeByToken(String token) {
        Claims claims = decodeJWT(token);
        String userType = claims.getIssuer();
        return userType;
    }

    public static Integer validateToken(String token) throws ServerException {
        Claims claims = decodeJWT(token);
        return Integer.parseInt(claims.getId());
    }

}
