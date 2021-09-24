package com.nkrasnovoronka.gamebuddyweb.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestUser {
    private String login;
    @Email
    private String email;
    @Size(min = 8, message = "Password mist contain at leas 8 symbols")
    private String password;
    private String matchingPassword;
}
