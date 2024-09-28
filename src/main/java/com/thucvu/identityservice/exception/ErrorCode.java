package com.thucvu.identityservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    INVALID_KEY("1001", "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED("1002", "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID("1003", "Username must be greater than {min} characters less than {max} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("1004", "Password must be greater than {min} characters less than {max} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("1005", "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("1006", "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("1007", "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB("1008", "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION("9999", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    private String code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(String code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
