package com.photoChallenger.tripture.domain.post.service;

import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import com.photoChallenger.tripture.domain.bookmark.repository.BookmarkRepository;
import com.photoChallenger.tripture.domain.challenge.entity.Challenge;
import com.photoChallenger.tripture.domain.challenge.repository.ChallengeRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.dto.*;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.domain.postLike.entity.PostLike;
import com.photoChallenger.tripture.domain.postLike.repository.PostLikeRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import com.photoChallenger.tripture.domain.profile.service.ProfileService;
import com.photoChallenger.tripture.domain.report.entity.ReportType;
import com.photoChallenger.tripture.domain.report.repository.ReportRepository;
import com.photoChallenger.tripture.global.S3.S3Service;
import com.photoChallenger.tripture.global.elasticSearch.challengeSearch.ChallengeDocument;
import com.photoChallenger.tripture.global.elasticSearch.challengeSearch.ChallengeSearchService;
import com.photoChallenger.tripture.global.exception.global.S3IOException;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import com.photoChallenger.tripture.global.exception.post.NoSuchPostException;
import com.photoChallenger.tripture.global.redis.RedisDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService{
    private final LoginRepository loginRepository;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PostLikeRepository postLikeRepository;
    private final ReportRepository reportRepository;
    private final ProfileRepository profileRepository;
    private final ChallengeRepository challengeRepository;
    private final RedisDao redisDao;
    private final ProfileService profileService;
    private final S3Service s3Service;
    private final ChallengeSearchService challengeSearchService;
    private final ElasticsearchOperations elasticsearchOperations;
    @Override
    public MyPostListResponse findMyPosts(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Pageable pageable = PageRequest.of(pageNo,9, Sort.by(Sort.Direction.DESC, "postDate"));
        Page<Post> page = postRepository.findAllByProfile_ProfileId(login.getProfile().getProfileId(), pageable);
        List<Post> postList = page.getContent();
        List<MyPostResponse> myPostResponseList = new ArrayList<>();
        for(Post p : postList){
            myPostResponseList.add(MyPostResponse.from(p));
        }
        return new MyPostListResponse(page.getTotalPages(),myPostResponseList);
    }

    /**
     * 커뮤니티 게시물 조회
     */
    @Override
    public GetPostResponse getPost(Long postId, Long loginId) {
        Post post = postRepository.findById(postId).orElseThrow(NoSuchPostException::new);
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);

        String isMyPost = "false";
        if(login.getProfile().getProfileId().equals(post.getProfile().getProfileId())) {
            isMyPost = "true";
        }

        String isSaveBookmark = "false";
        Optional<Bookmark> bookmark = bookmarkRepository.findBookmarkPostIdAndProfileId(postId, login.getProfile().getProfileId());
        if(bookmark.isPresent()) {
            isSaveBookmark = "true";
        }

        String isLike = "false";
        Optional<PostLike> postLike = postLikeRepository.findPostLikeByProfileIdAndPostId(login.getProfile().getProfileId(), post.getPostId());
        if(postLike.isPresent()) {
            isLike = "true";
        }


        String redisKey = "post:view:" + post.getPostId().toString(); // 조회수 key
        String redisUserKey = "user:post:" + login.getLoginId().toString(); // 유저 key

        int views = 0;
        if(redisDao.getValues(redisKey) == null) {
            views = post.getPostViewCount().intValue();
        } else {
            views = Integer.parseInt(redisDao.getValues(redisKey));
        }

        // 유저를 key로 조회한 게시글 ID List안에 해당 게시글 ID가 포함되어있지 않는다면,
        if (!redisDao.getValuesList(redisUserKey).contains(redisKey)) {
            redisDao.setValuesList(redisUserKey, redisKey); // 유저 key로 해당 글 ID를 List 형태로 저장
            views = views + 1; // 조회수 증가
            redisDao.setValues(redisKey, String.valueOf(views)); // 글ID key로 조회수 저장
            redisDao.expireValues(redisKey, 60 * 24);
            redisDao.expireValues(redisUserKey, 10);
        }

        return GetPostResponse.builder()
                .profileId(post.getProfile().getProfileId())
                .nickname(post.getProfile().getProfileNickname())
                .postLikeCount(post.getPostLikeCount())
                .postContent(post.getPostContent())
                .imgName(post.getPostImgName())
                .level(post.getProfile().getProfileLevel())
                .contentId(post.getContentId())
                .isMyPost(isMyPost)
                .isSaveBookmark(isSaveBookmark)
                .isLike(isLike).build();
    }

    /**
     * 커뮤니티 게시물 수정
     */
    @Override
    @Transactional
    public void editPost(Long postId, MultipartFile file, String postContent) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(NoSuchPostException::new);

        String imgName = null;
        if(!file.isEmpty()) {
            s3Service.delete(post.getPostImgName());
            imgName = s3Service.upload(file, "photo_challenge");
        }

        if(imgName != null) {
            post.update(imgName, postContent, LocalDate.now());
        } else {
            post.update(postContent, LocalDate.now());
        }

    }

    @Override
    @Transactional
    public void deletePost(Long postId) throws IOException {
        Post post = postRepository.findPostFetchJoin(postId);
        if(post == null) {  throw new NoSuchPostException(); }
        s3Service.delete(post.getPostImgName()); // 사진 삭제
        post.getProfile().getPostCnt().update(post.getChallenge().getChallengeRegion(),-1);
        postRepository.deleteById(postId);
    }

    @Override
    public SearchListResponse searchPost(String searchOne, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo,15, Sort.by(Sort.Direction.DESC, "postDate"));
        List<ChallengeDocument> challengeDocuments =  challengeSearchService.getChallengeByChallengeName(searchOne);
        List<Long> challengeIds = challengeDocuments.stream()
                        .map(o -> o.getChallengeId())
                                .collect(Collectors.toList());
        Page<Post> page = postRepository.findAllByChallenge_ChallengeId(challengeIds, pageable);
        List<Post> postList = page.getContent();
        List<SearchResponse> searchResponseList = postList.stream()
                .map(o -> new SearchResponse(o.getPostId(),o.getPostImgName()))
                .collect(Collectors.toList());
        return new SearchListResponse(page.getTotalPages(),searchResponseList);
    }

    @Override
    public PopularPostListResponse popularPostList(Long loginId,int pageNo) {
        Long profileId = loginRepository.findById(loginId).get().getProfile().getProfileId();
        Pageable pageable = PageRequest.of(pageNo,15);
        Page<Post> page = postRepository.findPopularPost(pageable);
        List<Post> postList = page.getContent();
        Set<Long> blockList = reportRepository.findAllByReporterIdAndReportTypeAndReportBlockChk(profileId, ReportType.post, true);
        return PopularPostListResponse.of(page.getTotalPages(),postList,blockList);
    }

    @Override
    public List<ChallengePopularPostResponse> getPopularPost10(Long loginId, String properties) {
        Long profileId = loginRepository.findById(loginId).get().getProfile().getProfileId();
        List<Post> postList = postRepository.findPopularPostList(properties);
        Set<Long> blockList = reportRepository.findAllByReporterIdAndReportTypeAndReportBlockChk(profileId, ReportType.post, true);
        return postList.stream()
                .map(post -> {
                    boolean isBlocked = blockList.contains(post.getProfile().getProfileId());
                    return ChallengePopularPostResponse.of(post,isBlocked);
                }).toList();
    }

    @Override
    @Transactional
    public void newPost(Long loginId, String postContent, MultipartFile file, Long challengeId) {
        Profile profile = loginRepository.findById(loginId).get().getProfile();
        Challenge challenge = challengeRepository.findById(challengeId).get();
        String imgName = null;
        try {
            imgName = s3Service.upload(file, "post");
        } catch (IOException e){
            throw new S3IOException();
        }
        Post.create(profile,challenge,imgName,postContent,LocalDate.now(),0,0L,challenge.getContentId());
        profile.getPostCnt().update(challenge.getChallengeRegion(),1);
    }
}
