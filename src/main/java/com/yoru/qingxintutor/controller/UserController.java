package com.yoru.qingxintutor.controller;

import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.filter.CustomUserDetails;
import com.yoru.qingxintutor.pojo.ApiResult;
import com.yoru.qingxintutor.pojo.dto.request.UserUpdateRequest;
import com.yoru.qingxintutor.pojo.result.UserInfoResult;
import com.yoru.qingxintutor.service.AvatarService;
import com.yoru.qingxintutor.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    // userinfo //
    @GetMapping("/me")
    public ApiResult<UserInfoResult> getProfiles(@AuthenticationPrincipal CustomUserDetails userDetails)
            throws BusinessException {
        return ApiResult.success(userService.getInfo(userDetails.getUser().getId()));
    }

    @PutMapping("/me")
    public ApiResult<UserInfoResult> updateProfiles(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @Valid @RequestBody UserUpdateRequest userUpdateRequest)
            throws BusinessException {
        userService.updateInfo(userDetails.getUser().getId(), userUpdateRequest);
        return ApiResult.success(userService.getInfo(userDetails.getUser().getId()));
    }

    // upload avatar
    @PostMapping("/upload-avatar")
    public ApiResult<UserInfoResult> uploadAvatar(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestParam("file") MultipartFile file)
            throws BusinessException {
        String accessURL = avatarService.uploadAvatar(file);
        userService.updateAvatar(userDetails.getUser().getId(), accessURL);
        return ApiResult.success(userService.getInfo(userDetails.getUser().getId()));
    }


    // reviews(reviewService), forum_messages, feedback //
    /*
    GET    /api/user/reviews
    POST   /api/user/reviews
    PUT    /api/user/reviews    -- 教师评论可更新
    GET    /api/user/forum-messages
    POST   /api/user/forum-messages
    GET    /api/user/feedback
    POST   /api/user/feedback
     */

    // notifications
    /*
    GET    /api/user/notifications
    PATCH  /api/user/notifications/{id}/read
     */

    // wallet, order, voucher, study_plan, reservation
    /*
    GET    /api/user/wallet
    GET    /api/user/orders
    GET    /api/user/vouchers
    GET    /api/user/reservations
    
    GET    /api/user/study-plans
    POST   /api/user/study-plans
    PUT    /api/user/study-plans/{id}  -- update
     */


}
