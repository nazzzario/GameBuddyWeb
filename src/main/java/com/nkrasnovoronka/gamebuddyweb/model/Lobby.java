package com.nkrasnovoronka.gamebuddyweb.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lobbies")
@Getter
@Setter
public class Lobby extends BaseEntity {
    @Column(name = "lobby_name")
    private String lobbyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(mappedBy = "joinedLobbies", fetch = FetchType.LAZY)
    private Set<User> joinedUsers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "amount_players", nullable = false)
    private Integer amountPlayer;

    private String description;

    public Lobby() {
        joinedUsers = new HashSet<>();
    }

    public void addUserToLobby(User user){
        joinedUsers.add(user);
        user.addJoinedLobbyToUser(this);
    }
}
