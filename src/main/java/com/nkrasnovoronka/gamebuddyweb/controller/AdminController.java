package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.user.RequestUser;
import com.nkrasnovoronka.gamebuddyweb.mapper.UserMapper;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public void registerAdmin(@RequestBody RequestUser requestUser) {
        logger.info("Crating new admin ");
        User user = userMapper.requestUserToEntity(requestUser);
        userService.registerAdmin(user);
        logger.info("Admin with id {} created successfully", requestUser.getLogin());
    }
}
