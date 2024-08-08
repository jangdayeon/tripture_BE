package com.photoChallenger.tripture.domain.postCnt.repository;

import com.photoChallenger.tripture.domain.postCnt.entity.PostCnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostCntRepository extends JpaRepository<PostCnt, Long> {
//    @Query("SELECT l " +
//            "FROM Login l " +
//            "JOIN FETCH l.profile " +
//            "WHERE l.loginId = :loginId")
//    Optional findByLoginId(Long loginId);
}
