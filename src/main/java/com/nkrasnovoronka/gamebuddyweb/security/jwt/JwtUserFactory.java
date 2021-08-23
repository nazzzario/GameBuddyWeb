package com.nkrasnovoronka.gamebuddyweb.security.jwt;

import com.nkrasnovoronka.gamebuddyweb.model.user.Status;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public final class JwtUserFactory {
    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getLogin(),
                user.getUserStatus().equals(Status.ACTIVE),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRoleName()))
        );
    }
}
