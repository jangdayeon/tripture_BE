package com.photoChallenger.tripture.domain.challenge.service;

import com.photoChallenger.tripture.domain.challenge.dto.GetPhotoChallengeResponse;
import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import com.photoChallenger.tripture.domain.challenge.repository.ChallengeRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.global.exception.challenge.NoSuchChallengeException;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final PostRepository postRepository;
    private final LoginRepository loginRepository;

    @Override
    public boolean isPhotoChallengeExist(String contentId) {
        return challengeRepository.existsByContentId(contentId);
    }

    @Override
    public GetPhotoChallengeResponse getPhotoChallenge(String contentId, Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Challenge challenge = challengeRepository.findByContentId(contentId).orElseThrow(NoSuchChallengeException::new);

        Boolean isChallengeParticipate = postRepository.existsByProfile_ProfileIdAndChallenge_ChallengeId(login.getProfile().getProfileId()
                , challenge.getChallengeId());

        return GetPhotoChallengeResponse.builder()
                .challengeId(challenge.getChallengeId())
                .challengeName(challenge.getChallengeName())
                .challengeImgName(challenge.getChallengeImgName())
                .challengeContent(challenge.getChallengeContent())
                .challengePoint(challenge.getChallengePoint())
                .isChallengeParticipate(isChallengeParticipate).build();
    }
}
