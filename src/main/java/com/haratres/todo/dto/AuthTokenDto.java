package com.haratres.todo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthTokenDto {
    private String token;

    public AuthTokenDto(String token) {
        this.token = token;
    }
}
