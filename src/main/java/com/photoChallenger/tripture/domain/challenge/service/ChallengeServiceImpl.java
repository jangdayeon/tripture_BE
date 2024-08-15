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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final PostRepository postRepository;
    private final LoginRepository loginRepository;

    private static final double EARTH_RADIUS = 6371;

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

    @Override
    public List<Challenge> getAroundChallengeList(double lat, double lon, double distance) {
        //현재 위도 좌표 (y 좌표)
        double nowLatitude = lat;
        //현재 경도 좌표 (x 좌표)
        double nowLongitude = lon;

        //m당 y 좌표 이동 값
        double mForLatitude =(1 /(EARTH_RADIUS* 1 *(Math.PI/ 180)))/ 1000;
        //m당 x 좌표 이동 값
        double mForLongitude =(1 /(EARTH_RADIUS* 1 *(Math.PI/ 180)* Math.cos(Math.toRadians(nowLatitude))))/ 1000;

        double distance_meter = distance * 1000;
        //현재 위치 기준 검색 거리 좌표
        double maxY = nowLatitude + (distance_meter * mForLatitude);
        double minY = nowLatitude - (distance_meter * mForLatitude);
        double maxX = nowLongitude + (distance_meter * mForLongitude);
        double minX = nowLongitude - (distance_meter * mForLongitude);

        //해당되는 좌표의 범위 안에 있는 가맹점
        List<Challenge> tmpAroundChallengeList = challengeRepository.getAroundChallengeList(maxY, maxX, minY, minX);
        List<Challenge> resultAroundChallengeList = new ArrayList<>();

        //정확한 거리 측정
        for(Challenge challenge : tmpAroundChallengeList) {
            double d = getDistance(nowLatitude, nowLongitude, challenge.getChallengeLatitude(), challenge.getChallengeLongitude());
            if(d < distance_meter) {
                resultAroundChallengeList.add(challenge);
            }
        }
        return resultAroundChallengeList;
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = EARTH_RADIUS * c * 1000;    // Distance in m
        return d;
    }
}
