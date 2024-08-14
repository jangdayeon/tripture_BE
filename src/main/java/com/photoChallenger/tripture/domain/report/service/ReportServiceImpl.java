package com.photoChallenger.tripture.domain.report.service;

import com.photoChallenger.tripture.domain.bookmark.repository.BookmarkRepository;
import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.comment.repository.CommentRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.domain.report.dto.ReportRequest;
import com.photoChallenger.tripture.domain.report.entity.Report;
import com.photoChallenger.tripture.domain.report.entity.ReportType;
import com.photoChallenger.tripture.domain.report.repository.ReportRepository;
import com.photoChallenger.tripture.global.exception.comment.NoSuchCommentException;
import com.photoChallenger.tripture.global.exception.login.NoSuchLoginException;
import com.photoChallenger.tripture.global.exception.post.NoSuchPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LoginRepository loginRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    @Transactional
    public String registerUserReporting(ReportRequest reportRequest, Long loginId) {
        Long profileId = 0L;
        if(reportRequest.getReportType() == ReportType.comment) {
            Comment comment = commentRepository.findById(reportRequest.getPostOrCommentId()).orElseThrow(NoSuchCommentException::new);
            profileId = comment.getProfileId();
        } else if(reportRequest.getReportType() == ReportType.post) {
            Post post = postRepository.findById(reportRequest.getPostOrCommentId()).orElseThrow(NoSuchPostException::new);
            profileId = post.getProfile().getProfileId();

            if(bookmarkRepository.existsByProfile_ProfileIdAndPostId(profileId, post.getPostId())) {
                bookmarkRepository.deleteByPostId(post.getPostId());
            }
        }

        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);

        Report report = Report.builder()
                .reporterId(login.getProfile().getProfileId())
                .reportBlockId(profileId)
                .postOrCommentId(reportRequest.getPostOrCommentId())
                .reportCategory(reportRequest.getReportCategory())
                .reportContent(reportRequest.getReportContent())
                .reportType(reportRequest.getReportType())
                .reportBlockChk(reportRequest.isReportBlockChk()).build();

        reportRepository.save(report);
        return "Saved " + reportRequest.getReportType() + " Report Successfully";
    }
}
