package com.nkrasnovoronka.gamebuddyweb.dto.auth;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class AuthenticationRequest {
    @Email
    private String email;
    private String password;
}
