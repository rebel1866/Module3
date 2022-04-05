package com.epam.esm.errorhandler;


import com.epam.esm.exception.LogicException;
import com.epam.esm.exception.RestControllerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private final static Map<String, HttpStatus> codesAndStatuses = new HashMap<>();
    private String defaultResponse = "Error has occurred";

    static {
        codesAndStatuses.put("errorCode=1", HttpStatus.NOT_FOUND);
        codesAndStatuses.put("errorCode=2", HttpStatus.INTERNAL_SERVER_ERROR);
        codesAndStatuses.put("errorCode=3", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(RestControllerException.class)
    public ResponseEntity<String> handleControllerException(RestControllerException resException) {
        HttpStatus status = codesAndStatuses.get(resException.getErrorCode());
        Errors errors = resException.getErrors();
        StringBuilder causeMessage;
        if (errors != null) {
            List<ObjectError> errorList = errors.getAllErrors();
            causeMessage = new StringBuilder();
            for (int i = 0; i < errorList.size(); i++) {
                causeMessage.append(errorList.get(i).getDefaultMessage());
                if (i != errorList.size() - 1) causeMessage.append(", ");
            }
        } else {
            causeMessage = new StringBuilder(resException.getMessage());
        }
        ErrorMessage errorMessage = new ErrorMessage(resException.getMessage(), resException.getErrorCode(),
                status, causeMessage.toString());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            defaultResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorMessage);
        } catch (IOException e) {
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(defaultResponse, status);
    }

    @ExceptionHandler(LogicException.class)
    public ResponseEntity<String> handleLogicException(LogicException logicException) {
        HttpStatus status = codesAndStatuses.get(logicException.getErrorCode());
        ErrorMessage errorMessage = new ErrorMessage(logicException.getMessage(), logicException.getErrorCode(),
                status, logicException.getMessage());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            defaultResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorMessage);
        } catch (IOException e) {
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(defaultResponse, status);
    }

    static class ErrorMessage {
        private String errorMessage;
        private String errorCode;
        private HttpStatus httpStatus;
        private String cause;

        public ErrorMessage(String errorMessage, String errorCode, HttpStatus httpStatus, String cause) {
            this.errorMessage = errorMessage;
            this.errorCode = errorCode;
            this.httpStatus = httpStatus;
            this.cause = cause;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }
    }
}