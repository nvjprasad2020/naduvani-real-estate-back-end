package com.naduvani.realestate.security.conroller;

import com.naduvani.realestate.model.ErrorResponse;
import com.naduvani.realestate.security.model.LoginRequest;
import com.naduvani.realestate.security.model.LoginResponse;
import com.naduvani.realestate.security.model.RegisterRequest;
import com.naduvani.realestate.security.model.RegisterResponse;
import com.naduvani.realestate.security.services.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(userAuthService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(userAuthService.saveUserDetails(request));
    }

    @GetMapping("/accessDenied")
    public ResponseEntity<ErrorResponse> sendErrorResponse() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).
                body(new ErrorResponse(HttpStatus.FORBIDDEN.name()," access denied due ot some reason"));
    }

}
