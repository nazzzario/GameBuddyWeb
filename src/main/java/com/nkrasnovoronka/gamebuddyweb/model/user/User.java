package com.nkrasnovoronka.gamebuddyweb.model.user;


import com.nkrasnovoronka.gamebuddyweb.model.BaseEntity;
import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails, Serializable {
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Lobby> createdLobbies;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_joined_lobbies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lobby_id")
    )
    private Set<Lobby> joinedLobbies;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private Status userStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        createdLobbies = new HashSet<>();
        joinedLobbies = new HashSet<>();
    }

    public void addCreatedLobbyToUser(Lobby lobby){
        createdLobbies.add(lobby);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
