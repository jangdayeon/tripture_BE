package com.photoChallenger.tripture.domain.comment.service;

import com.photoChallenger.tripture.domain.comment.dto.MyCommentResponse;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.comment.repository.CommentRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
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
    public List<MyCommentResponse> findMyComments(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        List<Comment> commentList = commentRepository.findAllByProfileIdOrderByCommentDateAsc(login.getProfile().getProfileId());
        List<MyCommentResponse> myCommentResponseList = new ArrayList<>();
        for(Comment c: commentList){
            myCommentResponseList.add(MyCommentResponse.from(c));
        }
        return myCommentResponseList;
    }
}
