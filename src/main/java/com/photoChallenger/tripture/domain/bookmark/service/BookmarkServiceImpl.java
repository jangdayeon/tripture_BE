package com.photoChallenger.tripture.domain.bookmark.service;

import com.photoChallenger.tripture.domain.bookmark.dto.MyContentResponse;
import com.photoChallenger.tripture.domain.bookmark.dto.MyPhotoChallengeResponse;
import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import com.photoChallenger.tripture.domain.bookmark.entity.Content;
import com.photoChallenger.tripture.domain.bookmark.entity.PhotoChallenge;
import com.photoChallenger.tripture.domain.bookmark.repository.BookmarkRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.global.exception.post.NoSuchPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{
    private final LoginRepository loginRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    
    @Override
    public List<MyContentResponse> getContentList(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).get();
        Pageable pageable = PageRequest.of(pageNo,2,Sort.by(Sort.Direction.DESC, "bookmarkTime"));
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByProfile_ProfileIdAndType(login.getProfile().getProfileId(), Content.class, pageable).getContent();
        List<MyContentResponse> contentList = new ArrayList<>();
        for(Bookmark b: bookmarkList){
            if(b instanceof Content){
                contentList.add(new MyContentResponse(((Content) b).getContentId()));
            }
        }
        return contentList;
    }

    @Override
    public List<MyPhotoChallengeResponse> getPhotoChallengeList(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).get();
        Pageable pageable = PageRequest.of(pageNo,9,Sort.by(Sort.Direction.DESC, "bookmarkTime"));
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByProfile_ProfileIdAndType(login.getProfile().getProfileId(), PhotoChallenge.class, pageable).getContent();
        List<MyPhotoChallengeResponse> photoChallengeList = new ArrayList<>();
        for(Bookmark b: bookmarkList){
            if(b instanceof PhotoChallenge){
                photoChallengeList.add(MyPhotoChallengeResponse.from(postRepository.findById(((PhotoChallenge) b).getPostId()).get()));
            }
        }
        return photoChallengeList;
    }

    @Override
    @Transactional
    public String savePhotoChallengeBookmark(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NoSuchPostException::new);

        Optional<Bookmark> bookmark = bookmarkRepository.findBookmarkPostIdAndProfileId(postId, post.getProfile().getProfileId());
        if(bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
            return "Bookmark deletion successful";
        } else {
            PhotoChallenge photoChallenge = PhotoChallenge.create(post.getProfile(), postId);
            bookmarkRepository.save(photoChallenge);
            return "Bookmark Save Successful";
        }
    }
}