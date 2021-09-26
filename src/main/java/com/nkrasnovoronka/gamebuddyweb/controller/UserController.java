package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.user.RequestUser;
import com.nkrasnovoronka.gamebuddyweb.dto.user.ResponseUser;
import com.nkrasnovoronka.gamebuddyweb.mapper.UserMapper;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> registration(@RequestBody @Valid RequestUser requestUser) {
        logger.info("Registration new user with email {}", requestUser.getEmail());
        if (!requestUser.getPassword().equals(requestUser.getMatchingPassword())) {
            logger.warn("User {} password didn`t match", requestUser.getEmail());
            return new ResponseEntity<>("Password didnt match", HttpStatus.FORBIDDEN);
        }
        User registerUser = userMapper.requestUserToEntity(requestUser);
        userService.register(registerUser);
        logger.info("User {} register ", registerUser.getEmail());
        logger.info("Redirection to logger page");
        return new ResponseEntity<>(new RedirectView("localhost:8080/api/v1/auth/login"), HttpStatus.PERMANENT_REDIRECT);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated() AND hasAnyRole({'ROLE_USER', 'ROLE_ADMIN'})")
    public ResponseUser getUserById(@PathVariable Long id) {
        logger.info("Getting user with id {}", id);
        try {
            User byId = userService.findById(id);
            return userMapper.entityToResponseUser(byId);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot get user with id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found user with id " + id, e);
        }
    }
}
