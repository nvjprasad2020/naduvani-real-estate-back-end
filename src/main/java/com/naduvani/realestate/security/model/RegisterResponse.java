package com.naduvani.realestate.security.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String token;
    private String email;
}
