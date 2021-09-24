package com.nkrasnovoronka.gamebuddyweb.service;

import com.nkrasnovoronka.gamebuddyweb.model.user.User;

import java.util.List;

public interface UserService {
    void register(User user);

    void registerAdmin(User user);

    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);

    void delete(Long id);
}
