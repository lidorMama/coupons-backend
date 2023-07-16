package com.lidor.coupon.util;

import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.enums.UserType;
import com.lidor.coupon.exceptions.ServerException;
import io.jsonwebtoken.Claims;

public class AuthorizationUtils {
    public static void validatePermission(String authorization, UserType requiredUserTypePermission) throws ServerException {
        Claims claims = JWTUtils.decodeJWT(authorization);
        String accountUserType = claims.getIssuer();
        if(!accountUserType.equals(String.valueOf(requiredUserTypePermission))){
            throw new ServerException( ErrorType.PERMISSION_DENIED,"Permission denied!");
        }
    }

    public static void validatePermission(String authorization, UserType requiredUserTypePermissionOne , UserType requiredUserTypePermissionTwo) throws ServerException {
        Claims claims = JWTUtils.decodeJWT(authorization);
        String accountUserType = claims.getIssuer();
        String strRequiredUserTypePermissionOne = String.valueOf(requiredUserTypePermissionOne);
        String strRequiredUserTypePermissionTwo = String.valueOf(requiredUserTypePermissionTwo);
        if(accountUserType.equals(strRequiredUserTypePermissionOne) || accountUserType.equals(strRequiredUserTypePermissionTwo)){
            return;
        }
        throw new ServerException( ErrorType.PERMISSION_DENIED,"Permission denied!");
    }
}
