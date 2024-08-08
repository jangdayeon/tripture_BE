package com.photoChallenger.tripture.domain.postCnt.service;

import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.postCnt.dto.PostChallengeCntResponse;
import com.photoChallenger.tripture.domain.postCnt.entity.PostCnt;
import com.photoChallenger.tripture.domain.postCnt.repository.PostCntRepository;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCntServiceImpl implements PostCntService{
    private final LoginRepository loginRepository;
    @Override
    public PostChallengeCntResponse findProfile(Long loginId) {
        PostCnt postCnt = loginRepository.findById(loginId).get().getProfile().getPostCnt();
        return PostChallengeCntResponse.from(postCnt);
    }
}
