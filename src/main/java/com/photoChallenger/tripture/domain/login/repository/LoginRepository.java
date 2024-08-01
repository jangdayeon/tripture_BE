package com.photoChallenger.tripture.domain.login.repository;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Long countByLoginEmail(String email);

    @Query("SELECT l FROM Login l WHERE l.loginEmail = :email AND l.loginType = :type")
    Optional<Login> findByEmailWithType(@Param("email") String email, @Param("type") LoginType type);

    @Query("SELECT l FROM Login l WHERE l.sessionId = :sessionId")
    Optional<Login> findBySessionId(@Param("sessionId") String sessionId);
}
