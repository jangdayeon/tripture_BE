package com.photoChallenger.tripture.domain.post.service;

import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.dto.MyPostResponse;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final LoginRepository loginRepository;
    private final PostRepository postRepository;
    @Override
    public List<MyPostResponse> findMyPosts(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Pageable pageable = PageRequest.of(pageNo,9, Sort.by(Sort.Direction.DESC, "PostDate"));
        List<Post> postList = postRepository.findAllByProfile_ProfileId(login.getProfile().getProfileId(), pageable).getContent();
        List<MyPostResponse> myPostResponseList = new ArrayList<>();
        for(Post p : postList){
            myPostResponseList.add(MyPostResponse.from(p));
        }
        return myPostResponseList;
    }
}
