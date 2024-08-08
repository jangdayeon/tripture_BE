package com.photoChallenger.tripture.domain.postCnt.repository;

import com.photoChallenger.tripture.domain.postCnt.entity.PostCnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCntRepository extends JpaRepository<PostCnt, Long> {
}
