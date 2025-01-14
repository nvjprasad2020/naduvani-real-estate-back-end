package com.naduvani.realestate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AppException extends RuntimeException {

    private HttpStatus status;
    private String errorMessage;
}
