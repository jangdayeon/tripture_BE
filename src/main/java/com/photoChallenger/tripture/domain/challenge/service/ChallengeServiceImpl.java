package com.photoChallenger.tripture.domain.challenge.service;

import com.photoChallenger.tripture.domain.challenge.dto.ChallengeAppendDistanceDto;
import com.photoChallenger.tripture.domain.challenge.dto.GetPhotoChallengeResponse;
import com.photoChallenger.tripture.domain.challenge.dto.SurroundingChallengeResponse;
import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import com.photoChallenger.tripture.domain.challenge.repository.ChallengeRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.global.exception.challenge.NoSuchChallengeException;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

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
        List<Challenge> tmpAroundChallengeList = calculateDistance(lat,lon,distance);
        List<Challenge> resultAroundChallengeList = new ArrayList<>();
        double distance_meter = distance * 1000;
        //정확한 거리 측정
        for(Challenge challenge : tmpAroundChallengeList) {
            double d = getDistance(lat, lon, challenge.getChallengeLatitude(), challenge.getChallengeLongitude());
            if(d < distance_meter) {
                resultAroundChallengeList.add(challenge);
            }
        }
        return resultAroundChallengeList;
    }

    //위치 계산
    private List<Challenge> calculateDistance(double lat, double lon, double distance){
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
        return challengeRepository.getAroundChallengeList(maxY, maxX, minY, minX);
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = EARTH_RADIUS * c * 1000;    // Distance in m
        return d;
    }

    @Override
    public List<SurroundingChallengeResponse> getSurroundingChallengeList(double lat, double lon, double distance, String properties) {
        //properties == postViewCount or postLikeCount
        List<Challenge> tmpAroundChallengeList = calculateDistance(lat,lon,distance);
        Map<Long,ChallengeAppendDistanceDto> resultAroundChallengeMap = new HashMap<>(); //challengeId, distance
        double distance_meter = distance * 1000;
        //정확한 거리 측정
        for(Challenge challenge : tmpAroundChallengeList) {
            double d = getDistance(lat, lon, challenge.getChallengeLatitude(), challenge.getChallengeLongitude());
            if(d < distance_meter) {
                resultAroundChallengeMap.put(challenge.getChallengeId(), new ChallengeAppendDistanceDto(challenge, d));
            }
        }

        //주변 챌린지 ID 가져오기
        List<Long> aroundChallengeIds = resultAroundChallengeMap.keySet().stream().toList();

        //챌린지id들의 post list 가져오기
        List<Post> posts = postRepository.findAllByChallenge_ChallengeIds(aroundChallengeIds);
        Map<Challenge, List<Post>> challengeListMap = posts.stream().collect(Collectors.groupingBy(Post::getChallenge));
        Map<Long, Long> participants = challengeListMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getChallengeId(),  // keyMapper
                        entry -> (long) entry.getValue().size()    // valueMapper
                ));
        //리턴 mapping하기 + 정렬하기
        Map<Long, Long> calculatedSum = new HashMap<>(); //challengeId, sum(OOOcount)
        if(properties.equals("postViewCount")){
            challengeListMap.forEach((subject, subjectList)->{
                Long sum = 0L;
                for (Post s : subjectList){
                    sum += s.getPostViewCount();
                }
                calculatedSum.put(subject.getChallengeId(),sum);
            });
        } else if(properties.equals("postLikeCount")){
            challengeListMap.forEach((subject, subjectList)->{
                Long sum = 0L;
                for (Post s : subjectList){
                    sum += s.getPostLikeCount();
                }
                calculatedSum.put(subject.getChallengeId(),sum);
            });
        }
        List<Long> sortedKeys = new ArrayList<>(calculatedSum.keySet()); //challengeId

        sortedKeys.sort(((o1, o2) -> calculatedSum.get(o2).compareTo(calculatedSum.get(o1))));

        List<SurroundingChallengeResponse> surroundingChallengeResponses = new ArrayList<>();
        for(Long key : sortedKeys){
            ChallengeAppendDistanceDto c = resultAroundChallengeMap.get(key);
            surroundingChallengeResponses.add(SurroundingChallengeResponse.of(c,participants.get(key)));
        };
        return surroundingChallengeResponses;
    }
}
