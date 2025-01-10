package com.PanikaSos.PS_springB.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
