package com.naduvani.realestate.security.services;

import com.naduvani.realestate.enities.Person;
import com.naduvani.realestate.person.PersonService;
import com.naduvani.realestate.security.AuthException;
import com.naduvani.realestate.security.entities.ERoleTypes;
import com.naduvani.realestate.security.entities.Role;
import com.naduvani.realestate.security.entities.User;
import com.naduvani.realestate.security.model.LoginRequest;
import com.naduvani.realestate.security.model.LoginResponse;
import com.naduvani.realestate.security.model.RegisterRequest;
import com.naduvani.realestate.security.model.RegisterResponse;
import com.naduvani.realestate.security.repository.RoleRepository;
import com.naduvani.realestate.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final PersonService personService;

    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .token(jwtToken)
                .build();
    }

    public RegisterResponse saveUserDetails(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Duplicate email id", HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(getRoleSet(request))
                .build();
        User createdUser = userRepository.save(user);

        personService.createPerson(
                Person.builder()
                        .emailId(user.getEmail()).firstName(user.getFirstName())
                        .lastName(user.getLastName()).mobileNumber1(request.getMobileNumber())
                        .build());

        return RegisterResponse.builder()
                .firstName(createdUser.getFirstName())
                .lastName(createdUser.getLastName())
                .email(createdUser.getEmail())
                .token(jwtService.generateToken(createdUser)).build();
    }

    private Set<Role> getRoleSet(RegisterRequest request) {
        Set<String> strRoles = request.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = getRole(ERoleTypes.ROLE_GUEST);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role foundRole = switch (role) {
                    case "admin" -> getRole(ERoleTypes.ROLE_ADMIN);
                    case "agent" -> getRole(ERoleTypes.ROLE_AGENT);
                    case "seller" -> getRole(ERoleTypes.ROLE_SELLER);
                    default -> getRole(ERoleTypes.ROLE_GUEST);
                };
                roles.add(foundRole);
            });
        }
        return roles;
    }

    private Role getRole(ERoleTypes role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."
                        + role.toString()));
    }
}