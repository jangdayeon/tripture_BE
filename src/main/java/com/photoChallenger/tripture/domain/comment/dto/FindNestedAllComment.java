package com.photoChallenger.tripture.domain.comment.dto;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindNestedAllComment {
    List<FindNestedComment> result;

    public static FindNestedAllComment of(List<Comment> commentList) {
        List<FindNestedComment> nestedCommentList = commentList.stream()
                .map(FindNestedComment::from).toList();
        return new FindNestedAllComment(nestedCommentList);
    }
}
