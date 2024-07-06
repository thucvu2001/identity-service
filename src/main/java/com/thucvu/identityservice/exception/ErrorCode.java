package com.thucvu.identityservice.exception;

public enum ErrorCode {

    USER_EXISTED(9999, "User existed"),
    USERNAME_INVALID(1003, "Username must be least at 3 characters"),
    INVALID_PASSWORD(1004, "Username must be least at 8 characters"),
    INVALID_KEY(1001, "Invalid message key"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
