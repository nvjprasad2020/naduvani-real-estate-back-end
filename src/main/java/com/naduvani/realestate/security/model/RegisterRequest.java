package com.naduvani.realestate.security.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private Set<String> roles;
    private String firstName;
    private String lastName;
    private String mobileNumber;
}
