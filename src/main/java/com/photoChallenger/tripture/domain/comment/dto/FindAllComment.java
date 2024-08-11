package com.photoChallenger.tripture.domain.comment.dto;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindAllComment {
    List<FindComment> result;

    public static FindAllComment of(List<Comment> commentList) {
        List<FindComment> nestedCommentList = commentList.stream()
                .map(FindComment::from).toList();
        return new FindAllComment(nestedCommentList);
    }
}
