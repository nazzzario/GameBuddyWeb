package com.nkrasnovoronka.gamebuddyweb.service.impl;

import com.nkrasnovoronka.gamebuddyweb.model.user.Role;
import com.nkrasnovoronka.gamebuddyweb.model.user.Status;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.repository.UserRepository;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setUserStatus(Status.ACTIVE);
        userRepository.save(user);
        return user;
    }

    @Override
    public void registerAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_ADMIN);
        user.setUserStatus(Status.ACTIVE);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userRepository.findUsersById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
