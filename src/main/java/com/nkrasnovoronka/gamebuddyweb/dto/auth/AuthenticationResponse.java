package com.nkrasnovoronka.gamebuddyweb.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private String email;
    private String token;
}
