package com.photoChallenger.tripture.domain.comment.service;

import com.photoChallenger.tripture.domain.comment.dto.MyCommentResponse;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.comment.repository.CommentRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
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
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final LoginRepository loginRepository;
    @Override
    public List<MyCommentResponse> findMyComments(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Pageable pageable = PageRequest.of(pageNo,2, Sort.by(Sort.Direction.DESC, "CommentDate"));
        List<Comment> commentList = commentRepository.findAllByProfileId(login.getProfile().getProfileId(), pageable);
        List<MyCommentResponse> myCommentResponseList = new ArrayList<>();
        for(Comment c: commentList){
            myCommentResponseList.add(MyCommentResponse.from(c));
        }
        return myCommentResponseList;
    }
}
