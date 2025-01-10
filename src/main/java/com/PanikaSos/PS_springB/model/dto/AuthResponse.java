package com.PanikaSos.PS_springB.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private Authorization token;
    private UserDetailsResponse user;

    public AuthResponse(Authorization authorization, UserDetailsResponse userDetailsResponse){
        this.token = authorization;
        this.user = userDetailsResponse;
    }
}
