package com.nkrasnovoronka.gamebuddyweb.dto.lobby;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLobby {
    private String lobbyName;
    private Long gameId;
    private Integer amountPlayer;
    private String description;
}
