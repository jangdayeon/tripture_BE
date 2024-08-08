package com.photoChallenger.tripture.domain.postLike.service;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.domain.postLike.entity.PostLike;
import com.photoChallenger.tripture.domain.postLike.repository.PostLikeRepository;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import com.photoChallenger.tripture.global.exception.post.NoSuchPostException;
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

    @Override
    @Transactional
    public String postLikeAdd(Long postId, Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Post post = postRepository.findById(postId).orElseThrow(NoSuchPostException::new);

        Optional<PostLike> postLike = postLikeRepository.findPostLikeByProfileIdAndPostId(login.getProfile().getProfileId(), post.getPostId());
        if(postLike.isPresent()) {
            post.subtractLikeCount();
            postLikeRepository.deleteById(postLike.get().getPostLikeId());

            return "Like delete successful";
        } else {
            post.addLikeCount();
            PostLike postLikeBuild = PostLike.builder()
                    .profileId(login.getProfile().getProfileId())
                    .post(post).build();
            postLikeRepository.save(postLikeBuild);

            return "Like save successful";
        }
    }
}
