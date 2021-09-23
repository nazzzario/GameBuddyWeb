package com.nkrasnovoronka.gamebuddyweb.dto.lobby;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestLobby {
    @Size(max = 250, message = "Lobby name is to long")
    private String lobbyName;
    private Long gameId;
    @Min(value = 1, message = "Players count cannot be less than 1")
    private Integer amountPlayer;
    private String description;
}
