package com.nkrasnovoronka.gamebuddyweb.dto.user;

import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseUser {
    private String login;
    private String email;
//    private List<Lobby> createdLobbies;
}
