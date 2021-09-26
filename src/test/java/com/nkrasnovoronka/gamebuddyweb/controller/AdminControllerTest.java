package com.nkrasnovoronka.gamebuddyweb.controller;


import com.nkrasnovoronka.gamebuddyweb.dto.user.RequestUser;
import com.nkrasnovoronka.gamebuddyweb.mapper.UserMapper;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
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

import static com.nkrasnovoronka.gamebuddyweb.controller.UserControllerTest.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"h2db", "debug"})
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN", "SUPERUSER"})
class AdminControllerTest {
    private static final String ADMIN_CONTROLLER_URL = "https://localhost:8080/api/v1/admin";

    @MockBean
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerAdmin() throws Exception{
        RequestUser requestUser = new RequestUser();
        requestUser.setEmail("test@email.com");
        requestUser.setLogin("test");
        requestUser.setPassword("test1234");
        requestUser.setMatchingPassword("test1234");
        when(userService.register(ArgumentMatchers.any(User.class))).thenReturn(userMapper.requestUserToEntity(requestUser));

        MockHttpServletResponse response = mockMvc.perform(post(ADMIN_CONTROLLER_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestUser)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}
