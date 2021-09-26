package com.nkrasnovoronka.gamebuddyweb.service;

import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import com.nkrasnovoronka.gamebuddyweb.model.user.Role;
import com.nkrasnovoronka.gamebuddyweb.model.user.Status;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.repository.UserRepository;
import com.nkrasnovoronka.gamebuddyweb.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"h2db", "debug"})
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword("12345678");
        user.setEmail("test@email.com");
    }

    @Test
    void createUserTest() {
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());
        when(userRepository.save(user)).thenReturn(user);
        User user = userService.register(this.user);
        assertEquals(this.user, user);
    }

    @Test
    void createAdminTest() {
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());
        when(userRepository.save(user)).thenReturn(user);
        userService.registerAdmin(this.user);
        assertEquals(Role.ROLE_ADMIN.name(), user.getRole().name());

    }


    @Test
    void deleteUserTest() {
        userService.delete(1L);
        verify(userRepository).deleteById(anyLong());
    }

    @Test
    void findByIdUserTest() {
        when(userRepository.findUsersById(anyLong())).thenReturn(Optional.ofNullable(user));
        User get = userService.findById(1L);
        assertEquals(this.user, get);
    }

    @Test
    void findByEmailUserTest() {
        when(userRepository.getUserByEmail(anyString())).thenReturn(user);
        User get = userService.findByEmail("email");
        assertEquals(this.user, get);
    }
}
