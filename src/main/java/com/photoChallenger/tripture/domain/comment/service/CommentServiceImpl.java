package com.photoChallenger.tripture.domain.comment.service;

import com.photoChallenger.tripture.domain.comment.dto.FindNestedAllComment;
import com.photoChallenger.tripture.domain.comment.dto.MyCommentResponse;
import com.photoChallenger.tripture.domain.comment.dto.WriteCommentRequest;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.comment.repository.CommentRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import com.photoChallenger.tripture.global.exception.post.NoSuchPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final LoginRepository loginRepository;
    private final PostRepository postRepository;

    @Override
    public List<MyCommentResponse> findMyComments(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Pageable pageable = PageRequest.of(pageNo,2, Sort.by(Sort.Direction.DESC, "CommentDate"));
        List<Comment> commentList = commentRepository.findAllByProfileId(login.getProfile().getProfileId(), pageable).getContent();
        List<MyCommentResponse> myCommentResponseList = new ArrayList<>();
        for(Comment c: commentList){
            myCommentResponseList.add(MyCommentResponse.from(c));
        }
        return myCommentResponseList;
    }

    /**
     * 댓글 작성
     */
    @Override
    @Transactional
    public Long writeComment(WriteCommentRequest writeCommentRequest, Long loginId) {
        Profile profile = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new).getProfile();
        Post post = postRepository.findById(writeCommentRequest.getPostId()).orElseThrow(NoSuchPostException::new);

        boolean isNested = writeCommentRequest.getGroupId() != 0;

        Comment comment = Comment.builder()
                .commentContent(writeCommentRequest.getCommentContent())
                .commentDate(LocalDateTime.now())
                .profileId(profile.getProfileId())
                .commentGroupId(writeCommentRequest.getGroupId())
                .nested(isNested)
                .post(post).build();

        Comment saveComment = commentRepository.save(comment);
        return saveComment.getCommentId();
    }

    /**
     * 대댓글 조회
     */
    @Override
    public FindNestedAllComment findAllNestedComment(Long groupId) {
        List<Comment> allNestedCommentByCommentId = commentRepository.findAllNestedCommentByCommentId(groupId);
        return FindNestedAllComment.of(allNestedCommentByCommentId);
    }
}
