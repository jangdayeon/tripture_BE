package com.photoChallenger.tripture.domain.comment.service;

import com.photoChallenger.tripture.domain.comment.dto.*;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.comment.repository.CommentRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.report.entity.ReportType;
import com.photoChallenger.tripture.domain.report.repository.ReportRepository;
import com.photoChallenger.tripture.global.exception.comment.NoSuchCommentException;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import com.photoChallenger.tripture.global.exception.post.NoSuchPostException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final LoginRepository loginRepository;
    private final PostRepository postRepository;
    private final ReportRepository reportRepository;

    @Override
    public MyCommentListResponse findMyComments(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Pageable pageable = PageRequest.of(pageNo,2, Sort.by(Sort.Direction.DESC, "CommentDate"));
        Page<Comment> page = commentRepository.findAllByProfileId(login.getProfile().getProfileId(), pageable);
        List<Comment> commentList = page.getContent();
        List<MyCommentResponse> myCommentResponseList = new ArrayList<>();
        for(Comment c: commentList){
            myCommentResponseList.add(MyCommentResponse.from(c));
        }
        return new MyCommentListResponse(page.getTotalPages(),myCommentResponseList);
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
    public FindAllNestedComment findAllNestedComment(Long groupId, Long loginId) {
        List<Comment> allNestedCommentByCommentId = commentRepository.findAllNestedCommentByCommentId(groupId);
        Set<Long> blockList = reportRepository.findAllByReporterIdAndReportTypeAndReportBlockChk(loginId, ReportType.comment, true);

        return FindAllNestedComment.of(allNestedCommentByCommentId, blockList);
    }

    /**
     * 댓글 삭제
     */
    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NoSuchCommentException::new);

        if(comment.getNested()) {
            log.info("nested");
            commentRepository.deleteById(commentId);
        } else {
            commentRepository.deleteById(commentId);
            commentRepository.deleteAllCommentByGroupId(commentId);
        }
    }

    @Override
    public FindAllNotNestedComment findAllNotNestedComment(Long postId, int pageNo, Long loginId) {
        Pageable pageable = PageRequest.of(pageNo,4, Sort.by(Sort.Direction.DESC, "CommentDate"));
        Page<Comment> commentPage = commentRepository.findAllByPost_PostIdAndNested(postId,false, pageable);
        Set<Long> blockList = reportRepository.findAllByReporterIdAndReportTypeAndReportBlockChk(loginId, ReportType.comment, true);

        return FindAllNotNestedComment.of(commentPage.getTotalPages(), commentPage.getContent(), blockList);
    }
}
