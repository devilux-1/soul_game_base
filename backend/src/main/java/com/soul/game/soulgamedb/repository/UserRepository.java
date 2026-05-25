package com.soul.game.soulgamedb.repository;

import com.soul.game.soulgamedb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySteamId(String steamId);
}