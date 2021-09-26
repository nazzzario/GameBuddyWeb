package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.lobby.RequestLobby;
import com.nkrasnovoronka.gamebuddyweb.mapper.LobbyMapper;
import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.service.LobbyService;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import java.util.List;
import java.util.Set;

import static com.nkrasnovoronka.gamebuddyweb.controller.UserControllerTest.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"h2db", "debug"})
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN", "USER"})
class LobbyControllerTest {
    private static final String LOBBY_CONTROLLER_URL = "https://localhost:8080/api/v1/lobby";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LobbyService lobbyService;

    @MockBean
    private UserService userService;

    @Autowired
    private LobbyMapper lobbyMapper;


    @Test
    void createUserTest() throws Exception {
        RequestLobby requestLobby = new RequestLobby();
        requestLobby.setLobbyName("test");
        requestLobby.setAmountPlayer(12);
        requestLobby.setGameId(1L);
        requestLobby.setDescription("bla bla");


        when(lobbyService.create(any(Lobby.class))).thenReturn(lobbyMapper.requestLobbyToEntity(requestLobby));

        MockHttpServletResponse response = mockMvc.perform(post(LOBBY_CONTROLLER_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestLobby)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void shouldThrowExceptionWhileCreatingUser() throws Exception {
        RequestLobby requestLobby = new RequestLobby();
        requestLobby.setLobbyName("test");
        requestLobby.setAmountPlayer(12);
        requestLobby.setGameId(1L);
        requestLobby.setDescription("bla bla");

        doThrow(IllegalArgumentException.class).when(lobbyService).create(any(Lobby.class));

        MockHttpServletResponse response = mockMvc.perform(post(LOBBY_CONTROLLER_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestLobby)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void testLobbyValidation() {
        RequestLobby invalidLobby = new RequestLobby();
        invalidLobby.setLobbyName("test");
        invalidLobby.setAmountPlayer(-12);
        invalidLobby.setGameId(1L);
        invalidLobby.setDescription("bla bla");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<RequestLobby>> violations = validator.validate(invalidLobby);
        assertFalse(violations.isEmpty());
    }

    @Test
    void updateLobbyTest() throws Exception {
        RequestLobby requestLobby = new RequestLobby();
        requestLobby.setLobbyName("test");
        requestLobby.setAmountPlayer(12);
        requestLobby.setGameId(1L);
        requestLobby.setDescription("bla bla");

        doNothing().when(lobbyService).update(anyLong(), any(Lobby.class));

        MockHttpServletResponse response = mockMvc.perform(put(LOBBY_CONTROLLER_URL + "/1/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestLobby)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingLobby() throws Exception {
        RequestLobby requestLobby = new RequestLobby();
        requestLobby.setLobbyName("test");
        requestLobby.setAmountPlayer(12);
        requestLobby.setGameId(1L);
        requestLobby.setDescription("bla bla");

        doThrow(IllegalArgumentException.class).when(lobbyService).update(anyLong(), any(Lobby.class));

        MockHttpServletResponse response = mockMvc.perform(put(LOBBY_CONTROLLER_URL + "/1/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestLobby)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void deleteLobbyTest() throws Exception {
        doNothing().when(lobbyService).delete(anyLong());

        MockHttpServletResponse response = mockMvc.perform(delete(LOBBY_CONTROLLER_URL + "/1/delete/1"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void throwExceptionWhenDeletingUser() throws Exception {
        doThrow(IllegalArgumentException.class).when(lobbyService).delete(anyLong());

        MockHttpServletResponse response = mockMvc.perform(delete(LOBBY_CONTROLLER_URL + "/1/delete/1"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllLobbiesTest() throws Exception {
        when(lobbyService.getAll()).thenReturn(List.of(new Lobby()));


        MockHttpServletResponse response = mockMvc.perform(get(LOBBY_CONTROLLER_URL + "/all"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testJoinUserToLobby() throws Exception {
        when(userService.findById(anyLong())).thenReturn(new User());

        doNothing().when(lobbyService).addUserToLobby(anyLong(), any(User.class));

        MockHttpServletResponse response = mockMvc.perform(get(LOBBY_CONTROLLER_URL + "/1/join/1"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testJoinUserToLobbyThrowException() throws Exception {
        when(userService.findById(anyLong())).thenReturn(new User());

        doThrow(IllegalArgumentException.class).when(lobbyService).addUserToLobby(anyLong(), any(User.class));

        MockHttpServletResponse response = mockMvc.perform(get(LOBBY_CONTROLLER_URL + "/1/join/1"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getLobbiesByNameTest() throws Exception {
        when(lobbyService.findAllLobbiesByGameName(anyString())).thenReturn(List.of(new Lobby()));

        MockHttpServletResponse response = mockMvc.perform(get(LOBBY_CONTROLLER_URL + "/all")
                        .param("gameName", "test")
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
