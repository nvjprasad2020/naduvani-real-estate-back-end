package com.naduvani.realestate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
}
