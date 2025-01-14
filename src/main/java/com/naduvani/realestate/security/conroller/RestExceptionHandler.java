package com.naduvani.realestate.security.conroller;

import com.naduvani.realestate.security.AuthException;
import com.naduvani.realestate.security.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AuthException.class})
    @ResponseBody
    public ResponseEntity<ApiResponse> handleException(AuthException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ApiResponse("failed", ex.getMessage()));
    }
}