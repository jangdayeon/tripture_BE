package com.photoChallenger.tripture.domain.comment.dto;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.report.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindAllNestedComment {
    List<FindComment> result;

    public static FindAllNestedComment of(List<Comment> commentList, Set<Long> blockList) {
        List<FindComment> nestedCommentList = commentList.stream()
                .map(comment -> {
                    boolean isBlocked = blockList.contains(comment.getCommentId());
                    return FindComment.from(comment, isBlocked);
                }).toList();
        return new FindAllNestedComment(nestedCommentList);
    }
}
