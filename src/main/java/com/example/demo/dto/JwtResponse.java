package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private long expiresIn;

    public JwtResponse(String token, Long id, String username, String email, long expiresIn) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.expiresIn = expiresIn;
    }
}