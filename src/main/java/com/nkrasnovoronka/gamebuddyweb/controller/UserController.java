package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.user.RequestUser;
import com.nkrasnovoronka.gamebuddyweb.mapper.UserMapper;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public RedirectView registration(@RequestBody RequestUser requestUser) {
        if (!requestUser.getPassword().equals(requestUser.getMatchingPassword())) {
            System.out.println("Password didnt match");
        }
        User registerUser = userMapper.requestUserToEntity(requestUser);
        userService.register(registerUser);
        return new RedirectView("/api/v1/auth/login");

    }
}
