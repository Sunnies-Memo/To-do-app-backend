package com.sunniesfish.todo_app.auth.service;

import com.sunniesfish.todo_app.auth.dto.PwdChangeRequest;
import com.sunniesfish.todo_app.auth.dto.UserProfileResponse;
import com.sunniesfish.todo_app.auth.dto.UserProfileUpdateRequest;
import com.sunniesfish.todo_app.auth.entity.Member;
import com.sunniesfish.todo_app.global.exceptions.S3Exception;
import com.sunniesfish.todo_app.global.service.S3ImageService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class MemberService {

    private final MemberCRUDService memberCRUDService;
    private final S3ImageService s3ImageService;

    private final String PROFILE_IMG_PATH = "/images/member/profile/";
    private final String BG_IMG_PATH = "/images/member/bg/";
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void changePassword(PwdChangeRequest pwdChangeRequest) throws IllegalAccessException {
        Optional<Member> memberOptional = memberCRUDService.findByUsername(pwdChangeRequest.getUsername());
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

             if(passwordEncoder.matches(pwdChangeRequest.getOldPassword(), member.getPassword())) {
                 String encodedPassword = passwordEncoder.encode(pwdChangeRequest.getNewPassword());
                memberCRUDService.update(pwdChangeRequest.getUsername(), Member.builder().username(pwdChangeRequest.getUsername()).password(encodedPassword).build());
             } else {
                 throw new IllegalAccessException("Old password does not match");
             }


        } else {
            throw new IllegalAccessException("invalid password");
        }
    }


    @Transactional
    public UserProfileResponse updateProfile(UserProfileUpdateRequest userProfileUpdateRequest) throws IllegalAccessException, S3Exception {
        UserProfileResponse userProfileResponse = new UserProfileResponse(userProfileUpdateRequest.getUsername(),"","");
        String newProfileImgPath = "";
        String newBgImgPath = "";
        if(userProfileUpdateRequest.getProfileImg() != null && !userProfileUpdateRequest.getProfileImg().isEmpty()) {
            newProfileImgPath = s3ImageService.upload(userProfileUpdateRequest.getProfileImg(), PROFILE_IMG_PATH);
        }
        if(userProfileUpdateRequest.getBgImg() != null && !userProfileUpdateRequest.getBgImg().isEmpty()) {
            newBgImgPath = s3ImageService.upload(userProfileUpdateRequest.getBgImg(), BG_IMG_PATH);
        }

        if(userProfileUpdateRequest.getPassword() != null && !userProfileUpdateRequest.getPassword().isEmpty()) {
            memberCRUDService.update(userProfileResponse.getUsername(),
                        Member.builder()
                                .password(userProfileUpdateRequest.getPassword())
                                .profileImg(newProfileImgPath)
                                .bgImg(newBgImgPath)
                                .build()
                    );
        } else {
            memberCRUDService.update(userProfileResponse.getUsername(),
                        Member.builder()
                                .profileImg(newProfileImgPath)
                                .bgImg(newBgImgPath)
                                .build()
                    );
        }
        userProfileResponse.setProfileImg(newProfileImgPath);
        userProfileResponse.setBgImg(newBgImgPath);
        return userProfileResponse;
    }

    @Transactional
    public UserProfileResponse getProfile(String username) throws IllegalAccessException {
        Member member = memberCRUDService.findByUsername(username).orElseThrow(() -> new IllegalAccessException("User does not exist"));
        return new UserProfileResponse(member.getUsername(), member.getProfileImg(), member.getBgImg());
    }

    @Transactional
    public String uploadProfileImg(UserProfileUpdateRequest userProfileUpdateRequest) throws IllegalAccessException, S3Exception {
        String newProfileImgPath = "";
        if(userProfileUpdateRequest.getProfileImg() != null && !userProfileUpdateRequest.getProfileImg().isEmpty()) {
            newProfileImgPath = s3ImageService.upload(userProfileUpdateRequest.getProfileImg(), PROFILE_IMG_PATH);
        }
        memberCRUDService.update(userProfileUpdateRequest.getUsername(), Member.builder().profileImg(newProfileImgPath).build());
        return newProfileImgPath;
    }

    @Transactional
    public String uploadBgImg(UserProfileUpdateRequest userProfileUpdateRequest) throws IllegalAccessException, S3Exception {
        String newBgImgPath = "";
        if(userProfileUpdateRequest.getBgImg() != null && !userProfileUpdateRequest.getBgImg().isEmpty()) {
            newBgImgPath = s3ImageService.upload(userProfileUpdateRequest.getBgImg(), BG_IMG_PATH);
        }
        memberCRUDService.update(userProfileUpdateRequest.getUsername(), Member.builder().bgImg(newBgImgPath).build());
        return newBgImgPath;
    }
}
