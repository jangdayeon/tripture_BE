package com.photoChallenger.tripture.domain.login.repository;

import com.photoChallenger.tripture.domain.login.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
}
