package com.nkrasnovoronka.gamebuddyweb.dto.lobby;

import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseLobby {
    private String lobbyName;
    private String ownerName;
    private String gameName;
    private String gameGenre;
    private Integer amountPlayer;
    private String description;
    private List<User> joinedUsers;
}
