package com.naduvani.realestate.exception;

import com.naduvani.realestate.model.ErrorResponse;
import com.naduvani.realestate.propertty.PropertyNotFoundException;
import com.naduvani.realestate.propertty.PropertyServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAppException(Exception exception) {
        ErrorResponse response = new ErrorResponse(
                "UN_KNOWN", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAppException(AppException exception) {
        ErrorResponse response = new ErrorResponse(
                exception.getStatus().name(), exception.getErrorMessage());
        return ResponseEntity.status(exception.getStatus()).body(response);
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
        ErrorResponse re = new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(),
                "Authentication failed at controller advice");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<String> handlePropertyNotFoundException(PropertyNotFoundException ex) {
        LOG.warn("Property not found exception: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyServiceException.class)
    public ResponseEntity<String> handlePropertyServiceException(PropertyServiceException ex) {
        LOG.error("Property service exception: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
