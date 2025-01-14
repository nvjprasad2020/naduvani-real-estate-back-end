package com.naduvani.realestate.security.model;

import com.naduvani.realestate.security.entities.ERoleTypes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse{
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private List<String> roles;
}
