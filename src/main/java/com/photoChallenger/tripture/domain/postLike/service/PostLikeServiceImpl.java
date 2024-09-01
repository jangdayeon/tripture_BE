package com.photoChallenger.tripture.domain.postLike.service;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.domain.postLike.dto.LikeSaveResponse;
import com.photoChallenger.tripture.domain.postLike.entity.PostLike;
import com.photoChallenger.tripture.domain.postLike.repository.PostLikeRepository;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import com.photoChallenger.tripture.global.exception.post.NoSuchPostException;
import com.photoChallenger.tripture.global.redis.RedisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService{
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final LoginRepository loginRepository;
    private final RedisDao redisDao;

    @Override
    @Transactional
    public LikeSaveResponse postLikeAdd(Long postId, Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Post post = postRepository.findById(postId).orElseThrow(NoSuchPostException::new);

        String redisKey = "post:like:" + post.getPostId().toString(); // 좋아요 postId

        int likes = 0;
        if(redisDao.getValues(redisKey) == null) {
            likes = post.getPostLikeCount().intValue();
        } else {
            likes = Integer.parseInt(redisDao.getValues(redisKey));
        }

        Optional<PostLike> postLike = postLikeRepository.findPostLikeByProfileIdAndPostId(login.getProfile().getProfileId(), post.getPostId());
        if(postLike.isPresent()) {
            postLikeRepository.deleteById(postLike.get().getPostLikeId());

            likes -= 1;
            redisDao.setValues(redisKey, String.valueOf(likes));

            return LikeSaveResponse.of("Delete");
        } else {
            PostLike postLikeBuild = PostLike.builder()
                    .profileId(login.getProfile().getProfileId())
                    .post(post).build();
            postLikeRepository.save(postLikeBuild);

            likes += 1;
            redisDao.setValues(redisKey, String.valueOf(likes));

            return LikeSaveResponse.of("Save");
        }
    }
}
