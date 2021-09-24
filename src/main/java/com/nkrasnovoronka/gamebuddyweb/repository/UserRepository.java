package com.nkrasnovoronka.gamebuddyweb.repository;

import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);

    @Query("select u from User u where u.role = 'ROLE_USER' and u.id = :id")
    Optional<User> findUsersById(Long id);
}
