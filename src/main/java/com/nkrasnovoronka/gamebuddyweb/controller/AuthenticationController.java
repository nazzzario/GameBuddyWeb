package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.auth.AuthenticationRequest;
import com.nkrasnovoronka.gamebuddyweb.dto.auth.AuthenticationResponse;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.security.jwt.JwtTokenProvider;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        logger.info("User {} try authenticate", authenticationRequest.getEmail());
        try {
            String email = authenticationRequest.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, authenticationRequest.getPassword()));
            User byEmail = userService.findByEmail(email);
            if (byEmail == null) {
                throw new UsernameNotFoundException(String.format("User with email %s not found", email));
            }
            String token = jwtTokenProvider.createToken(email, byEmail.getRole());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setEmail(email);
            authenticationResponse.setToken(token);
            logger.info("User {} login into system", authenticationRequest.getEmail());
            return authenticationResponse;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("/login")
    public String greeting() {
        return "Hello";
    }
}
