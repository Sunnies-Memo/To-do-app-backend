package com.sunniesfish.todo_app.auth.controller;

import com.sunniesfish.todo_app.auth.dto.PwdChangeRequest;
import com.sunniesfish.todo_app.auth.dto.UserProfileUpdateRequest;
import com.sunniesfish.todo_app.auth.service.MemberService;
import com.sunniesfish.todo_app.global.exceptions.S3Exception;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@CrossOrigin
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return new ResponseEntity(memberService.getProfile(username) ,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/password")
    public ResponseEntity changePassword(@RequestBody PwdChangeRequest pwdChangeRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if(!username.equals(pwdChangeRequest.getUsername())) {
                throw new IllegalAccessException("Not authorized");
            }
            memberService.changePassword(pwdChangeRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/profileImg")
    public ResponseEntity uploadProfileImg(@Valid @ModelAttribute UserProfileUpdateRequest userProfileUpdateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if(!username.equals(userProfileUpdateRequest.getUsername())) {
                throw new IllegalAccessException("Not authorized");
            }

            if (userProfileUpdateRequest.getProfileImg().getSize() > 15 * 1024 * 1024) {
                throw new IllegalAccessException("File is too large to upload");
            }
            if (isAllowedFileType(userProfileUpdateRequest.getProfileImg().getContentType())) {
                throw new IllegalAccessException("Invalid image type");
            }
            String profileImg = memberService.uploadProfileImg(userProfileUpdateRequest);
            return ResponseEntity.ok().body(profileImg);
        } catch (S3Exception | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        }
    }

    @PostMapping("/bgImg")
    public ResponseEntity uploadBgImg(@Valid @ModelAttribute UserProfileUpdateRequest userProfileUpdateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if(!username.equals(userProfileUpdateRequest.getUsername())) {
                throw new IllegalAccessException("Not authorized");
            }
            if (userProfileUpdateRequest.getBgImg().getSize() > 30 * 1024 * 1024) {
                throw new IllegalAccessException("File is too large to upload");
            }
            if (isAllowedFileType(userProfileUpdateRequest.getBgImg().getContentType())) {
                throw new IllegalAccessException("Invalid image type");
            }
            String bgImg = memberService.uploadBgImg(userProfileUpdateRequest);
            return ResponseEntity.ok().body(bgImg);
        } catch (S3Exception | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        }
    }

//    @PutMapping("")
//    public ResponseEntity updateProfile(@Valid @ModelAttribute UserProfileUpdateRequest userProfileUpdateRequest) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName();
//            if(!username.equals(userProfileUpdateRequest.getUsername())) {
//                throw new IllegalAccessException("Not authorized");
//            }
//            UserProfileResponse userProfileResponse = memberService.updateProfile(userProfileUpdateRequest);
//            return ResponseEntity.ok().body(userProfileResponse);
//        } catch (S3Exception | IllegalAccessException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

    private boolean isAllowedFileType(String contentType) {
        return "image/png".equals(contentType) ||
                "image/jpeg".equals(contentType) ||
                "image/jpg".equals(contentType);
    }
}
