package com.photoChallenger.tripture.domain.profile.service;

import com.photoChallenger.tripture.domain.comment.entity.Comment;
import com.photoChallenger.tripture.domain.comment.repository.CommentRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.entity.LoginType;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.domain.postLike.entity.PostLike;
import com.photoChallenger.tripture.domain.postLike.repository.PostLikeRepository;
import com.photoChallenger.tripture.domain.profile.dto.MemberDto;
import com.photoChallenger.tripture.domain.profile.dto.MemberEditForm;
import com.photoChallenger.tripture.domain.profile.dto.MemberEditRequest;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import com.photoChallenger.tripture.global.exception.profile.DuplicateNicknameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    private final LoginRepository loginRepository;
    private final ProfileRepository profileRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Override
    public MemberDto getMember(Long LoginId){
        Login login = loginRepository.findById(LoginId).get();
        return MemberDto.from(login);
    }

    @Override
    public MemberEditForm memberEditForm(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        return MemberEditForm.from(login);
    }

    @Override
    @Transactional
    public void memberEdit(String profileImgName, String profileNickname, String loginPw, long loginId) {
        Login login = loginRepository.findById(loginId).get();
        Profile profile = login.getProfile();
        if(!profileNickname.equals(profile.getProfileNickname())
                && profileRepository.existsByProfileNickname(profileNickname)) {
            throw new DuplicateNicknameException();
        }

        profile.update(profileImgName,profileNickname);
        if (!loginPw.isEmpty() && login.getLoginType().equals(LoginType.SELF)) {
            login.update(loginPw);
        }
    }

    @Override
    public String checkLevel(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        return login.getProfile().getProfileLevel().getDescription();
    }

    @Override
    public String checkProfileImgName(Long loginId) {
        Login login = loginRepository.findById(loginId).get();
        return !login.getProfile().getProfileImgName().equals("default")? login.getProfile().getProfileImgName():null;
    }

    @Override
    @Transactional
    public void deleteOne(Long loginId) throws NoSuchElementException{
        Login login = loginRepository.findById(loginId).get();
        Profile p = login.getProfile();
//        Profile p = profileRepository.findAllByProfileId(login.getProfile().getProfileId());

        //댓글 삭제
        commentRepository.deleteByProfileId(p.getProfileId());
        commentRepository.deleteByCommentGroupId(p.getProfileId());
        //포토챌린지 좋아요한 내역 불러와 좋아요수 -1 진행
        List<PostLike> postLikeList = postLikeRepository.findWhereILike(p.getProfileId());
        for(PostLike postLike : postLikeList){
            postLike.getPost().subtractLikeCount();
        }
        //포토챌린지 좋아요 삭제
        postLikeRepository.deleteByProfileId(p.getProfileId());

        profileRepository.deleteById(login.getProfile().getProfileId());
    }
}
