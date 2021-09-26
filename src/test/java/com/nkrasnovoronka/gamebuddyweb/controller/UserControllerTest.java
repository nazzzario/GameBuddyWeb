package com.nkrasnovoronka.gamebuddyweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nkrasnovoronka.gamebuddyweb.dto.user.RequestUser;
import com.nkrasnovoronka.gamebuddyweb.mapper.UserMapper;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"h2db", "debug"})
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN"})
class UserControllerTest {
    private static final String USER_CONTROLLER_URL = "https://localhost:8080/api/v1/user";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    private static RequestUser requestUser;

    @BeforeAll
    private static void setUp() {
        requestUser = new RequestUser();
        requestUser.setEmail("test@email.com");
        requestUser.setLogin("test");
        requestUser.setPassword("test1234");
        requestUser.setMatchingPassword("test1234");
    }

    @Test
    void creatingValidUser() throws Exception {
        when(userService.register(ArgumentMatchers.any(User.class))).thenReturn(userMapper.requestUserToEntity(requestUser));

        MockHttpServletResponse response = mockMvc.perform(post(USER_CONTROLLER_URL + "/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestUser)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.PERMANENT_REDIRECT.value(), response.getStatus());
    }

    @Test
    void testUserValidation() {
        RequestUser invalidUser = new RequestUser();
        invalidUser.setPassword("123");
        invalidUser.setMatchingPassword("1234565");
        invalidUser.setEmail("Invalid");
        invalidUser.setLogin("invalid");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<RequestUser>> violations = validator.validate(invalidUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldThrowAnExceptionIfPasswordDidntMatch() throws Exception {
        RequestUser requestUser = new RequestUser();
        requestUser.setEmail("test@email.com");
        requestUser.setLogin("test");
        requestUser.setPassword("test1234");
        requestUser.setMatchingPassword("test12345");

        MockHttpServletResponse response = mockMvc.perform(post(USER_CONTROLLER_URL + "/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestUser)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());


    }

    @Test
    void getUserById() throws Exception {
        doNothing().when(userService).findById(anyLong());

        MockHttpServletResponse response = mockMvc.perform(get(USER_CONTROLLER_URL + "/1"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getUserWithInvalidId() throws Exception {
        doThrow(IllegalArgumentException.class).when(userService).findById(anyLong());

        MockHttpServletResponse response = mockMvc.perform(get(USER_CONTROLLER_URL + "/1"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
