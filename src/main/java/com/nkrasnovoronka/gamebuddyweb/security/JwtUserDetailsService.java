package com.nkrasnovoronka.gamebuddyweb.security;

import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User byEmail = userService.findByEmail(email);
        if (byEmail == null) {
            throw new UsernameNotFoundException(String.format("User with email %s not found", email));
        }

        return byEmail;
    }
}
