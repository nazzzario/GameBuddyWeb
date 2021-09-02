package com.nkrasnovoronka.gamebuddyweb.repository;

import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    List<Lobby> findAllByGameName(String gameName);
}
