package com.photoChallenger.tripture.domain.bookmark.service;

import com.photoChallenger.tripture.domain.bookmark.dto.*;
import com.photoChallenger.tripture.domain.bookmark.entity.Bookmark;
import com.photoChallenger.tripture.domain.bookmark.entity.Content;
import com.photoChallenger.tripture.domain.bookmark.entity.PhotoChallenge;
import com.photoChallenger.tripture.domain.bookmark.repository.BookmarkRepository;
import com.photoChallenger.tripture.domain.bookmark.repository.ContentRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
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
    private final ContentRepository contentRepository;
    
    @Override
    public MyContentListResponse getContentList(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).get();
        Pageable pageable = PageRequest.of(pageNo,2,Sort.by(Sort.Direction.DESC, "bookmarkTime"));
        Page<Bookmark> page = bookmarkRepository.findAllByProfile_ProfileIdAndType(login.getProfile().getProfileId(), Content.class, pageable);
        List<Bookmark> bookmarkList = page.getContent();
        List<MyContentResponse> contentList = new ArrayList<>();
        for(Bookmark b: bookmarkList){
            if(b instanceof Content){
                contentList.add(new MyContentResponse(((Content) b).getContentId()));
            }
        }
        return new MyContentListResponse(page.getTotalPages(),contentList);
    }

    @Override
    public MyPhotoChallengeListResponse getPhotoChallengeList(Long loginId, int pageNo) {
        Login login = loginRepository.findById(loginId).get();
        Pageable pageable = PageRequest.of(pageNo,9,Sort.by(Sort.Direction.DESC, "bookmarkTime"));
        Page<Bookmark> page = bookmarkRepository.findAllByProfile_ProfileIdAndType(login.getProfile().getProfileId(), PhotoChallenge.class, pageable);
        List<Bookmark> bookmarkList = page.getContent();
        List<MyPhotoChallengeResponse> photoChallengeList = new ArrayList<>();
        for(Bookmark b: bookmarkList){
            if(b instanceof PhotoChallenge){
                photoChallengeList.add(MyPhotoChallengeResponse.from(postRepository.findById(((PhotoChallenge) b).getPostId()).get()));
            }
        }
        return new MyPhotoChallengeListResponse(page.getTotalPages(), photoChallengeList);
    }

    @Override
    @Transactional
    public BookmarkSaveResponse savePhotoChallengeBookmark(Long postId, Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        Post post = postRepository.findById(postId).orElseThrow(NoSuchPostException::new);

        Optional<Bookmark> bookmark = bookmarkRepository.findBookmarkPostIdAndProfileId(postId, login.getProfile().getProfileId());
        if(bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
            return BookmarkSaveResponse.of("Delete");
        } else {
            PhotoChallenge photoChallenge = PhotoChallenge.create(login.getProfile(), postId);
            bookmarkRepository.save(photoChallenge);
            return BookmarkSaveResponse.of("Save");
        }
    }

    @Override
    @Transactional
    public String saveContentIdBookmark(String contentId, Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);

        Optional<Bookmark> bookmark = bookmarkRepository.findBookmarkContentIdAndProfileId(contentId, login.getProfile().getProfileId());
        if(bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
            return "Bookmark deletion successful";
        } else {
            Content content = Content.create(login.getProfile(), contentId);
            bookmarkRepository.save(content);
            return "Bookmark Save Successful";
        }
    }

    @Override
    public boolean checkContentBookmark(Long loginId, String contentId) {
        Login login = loginRepository.findById(loginId).orElseThrow(NoSuchLoginException::new);
        return contentRepository.existsByProfile_ProfileIdAndContentId(login.getProfile().getProfileId(), contentId);
    }
}