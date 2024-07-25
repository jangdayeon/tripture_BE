package com.photoChallenger.tripture.domain.bookmark.service;

import com.photoChallenger.tripture.domain.bookmark.dto.MyContentResponse;
import com.photoChallenger.tripture.domain.bookmark.dto.MyPhotoChallengeResponse;
import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import com.photoChallenger.tripture.domain.bookmark.entity.Content;
import com.photoChallenger.tripture.domain.bookmark.entity.PhotoChallenge;
import com.photoChallenger.tripture.domain.bookmark.repository.BookmarkRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{
    private final LoginRepository loginRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    @Override
    public List<MyContentResponse> getContentList(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByProfile_ProfileIdAndTypeOOrderByBookmarkTimeAsc(login.getProfile().getProfileId(), Content.class);
        List<MyContentResponse> contentList = new ArrayList<>();
        for(Bookmark b: bookmarkList){
            if(b instanceof Content){
                contentList.add(new MyContentResponse(((Content) b).getContentId()));
            }
        }
        return contentList;
    }

    @Override
    public List<MyPhotoChallengeResponse> getPhotoChallengeList(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByProfile_ProfileIdAndTypeOOrderByBookmarkTimeAsc(login.getProfile().getProfileId(), PhotoChallenge.class);
        List<MyPhotoChallengeResponse> photoChallengeList = new ArrayList<>();
        for(Bookmark b: bookmarkList){
            if(b instanceof PhotoChallenge){
                photoChallengeList.add(MyPhotoChallengeResponse.from(postRepository.findById(((PhotoChallenge) b).getPostId()).get()));
            }
        }
        return photoChallengeList;
    }
}