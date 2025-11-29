package com.yoru.qingxintutor.controller;

import com.yoru.qingxintutor.filter.CustomUserDetails;
import com.yoru.qingxintutor.pojo.ApiResult;
import com.yoru.qingxintutor.pojo.dto.request.UserUpdateRequest;
import com.yoru.qingxintutor.pojo.result.UserInfoResult;
import com.yoru.qingxintutor.service.AvatarService;
import com.yoru.qingxintutor.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    // User Info
    @GetMapping("/me")
    public ApiResult<UserInfoResult> getProfiles(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResult.success(userService.getInfo(userDetails.getUser().getId()));
    }

    @PutMapping("/me")
    public ApiResult<UserInfoResult> updateProfiles(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateInfo(userDetails.getUser().getId(), userUpdateRequest);
        return ApiResult.success(userService.getInfo(userDetails.getUser().getId()));
    }

    // upload avatar
    @PostMapping("/upload-avatar")
    public ApiResult<UserInfoResult> uploadAvatar(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestParam("file") MultipartFile file) {
        String accessURL = avatarService.uploadAvatar(file);
        userService.updateAvatar(userDetails.getUser().getId(), accessURL);
        return ApiResult.success(userService.getInfo(userDetails.getUser().getId()));
    }
}
