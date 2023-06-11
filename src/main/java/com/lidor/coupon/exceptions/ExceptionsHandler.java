package com.lidor.coupon.exceptions;

import com.lidor.coupon.dto.ServerErrorData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler
    @ResponseBody
    public ServerErrorData response(Exception exception, HttpServletResponse httpServletResponse) {

        if (exception instanceof ServerException) {

            ServerException serverException = (ServerException) exception;
            int errorCode = serverException.getErrorType().getErrorNumber();
            String errorMessage = serverException.getErrorType().getErrorMessage();
            String errorType = String.valueOf(serverException.getErrorType());

            httpServletResponse.setStatus(errorCode);

            if (serverException.getErrorType().isShowStackTrace()) {
                serverException.printStackTrace();
            }
            return new ServerErrorData(errorType, errorMessage);
        }
        httpServletResponse.setStatus(601);
        return new ServerErrorData("General Error", "Something went wrong , try again later");
}
}