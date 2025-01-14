package com.naduvani.realestate.controller;

import com.naduvani.realestate.security.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @GetMapping("/guestHome")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<ApiResponse> getGuestContent() {
        ApiResponse response = new ApiResponse();
        response.setStatus("success");
        response.setMessage("Hello Guest, Welcome to the land u dream");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/adminHome")
    public ResponseEntity<ApiResponse> getAdminContent() {
        ApiResponse response = new ApiResponse();
        response.setStatus("success");
        response.setMessage("Hello Admin, Welcome to the land u dream");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/sellerHome")
    public ResponseEntity<ApiResponse> getSellerContent() {
        ApiResponse response = new ApiResponse();
        response.setStatus("success");
        response.setMessage("Hello Seller, Welcome to the land u dream");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/agentHome")
    public ResponseEntity<ApiResponse> getAgentContent() {
        ApiResponse response = new ApiResponse();
        response.setStatus("success");
        response.setMessage("Hello Agent Welcome to the land u dream");
        return ResponseEntity.ok(response);
    }
}
