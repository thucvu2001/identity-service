package com.thucvu.identityservice.exception;

import com.thucvu.identityservice.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ApiResponse apiResponse = new ApiResponse();
        String enumKey = exception.getBindingResult().getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY; // errorCode default
        List<Map<String, Object>> attributes = new ArrayList<>();
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            List<ConstraintViolation<?>> constraintViolations = exception.getBindingResult()
                    .getAllErrors().stream()
                    .map(objectError -> (ConstraintViolation<?>) objectError.unwrap(ConstraintViolation.class))
                    .collect(Collectors.toList());
            constraintViolations.forEach(constraintViolation -> attributes.add(constraintViolation.getConstraintDescriptor().getAttributes()));
            log.info(attributes.toString());
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        StringBuilder code = new StringBuilder();
        StringBuilder message = new StringBuilder();
        if (attributes.isEmpty()) {
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(errorCode.getMessage());
        } else {
            int index = 0;
            int size = attributes.size();
            for (Map<String, Object> attribute : attributes) {
                ErrorCode error = ErrorCode.valueOf((String) attribute.get("message"));
                code.append(error.getCode());
                String map = mapAttribute(error.getMessage(), attribute);
                message.append(mapAttribute(error.getMessage(), attribute));
                if (index != size - 1) {
                    code.append(", ");
                    message.append(", ");
                }
                index++;
            }
            apiResponse.setCode(code.toString());
            apiResponse.setMessage(message.toString());
        }
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) throws NullPointerException {
        String result = new String(message);
        String minValue = Objects.nonNull(attributes.get(MIN_ATTRIBUTE)) ? String.valueOf(attributes.get(MIN_ATTRIBUTE)) : null;
        String maxValue = Objects.nonNull(attributes.get(MAX_ATTRIBUTE)) ? String.valueOf(attributes.get(MAX_ATTRIBUTE)) : null;
        if (maxValue != null) {
            result = result.replace("{" + MAX_ATTRIBUTE + "}", maxValue);
        }
        if (minValue != null) {
            result = result.replace("{" + MIN_ATTRIBUTE + "}", minValue);
        }
        log.info(result);
        return result;
    }
}