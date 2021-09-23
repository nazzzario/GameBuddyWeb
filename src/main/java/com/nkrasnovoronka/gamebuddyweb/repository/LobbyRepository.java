package com.nkrasnovoronka.gamebuddyweb.repository;

import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    List<Lobby> findAllByGameName(String gameName);
}
