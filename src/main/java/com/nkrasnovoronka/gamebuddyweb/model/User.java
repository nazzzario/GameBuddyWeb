package com.nkrasnovoronka.gamebuddyweb.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<Lobby> createdLobbies;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_joined_lobbies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lobby_id")
    )
    private Set<Lobby> joinedLobbies;

    public User() {
        createdLobbies = new HashSet<>();
        joinedLobbies = new HashSet<>();
    }

    public void addCreatedLobbyToUser(Lobby lobby){
        createdLobbies.add(lobby);
    }

    public void addJoinedLobbyToUser(Lobby lobby){
        joinedLobbies.add(lobby);
        lobby.addUserToLobby(this);
    }
}
