package com.yoru.qingxintutor.controller;

import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.pojo.ApiResult;
import com.yoru.qingxintutor.pojo.dto.request.*;
import com.yoru.qingxintutor.pojo.dto.response.UserAuthResponse;
import com.yoru.qingxintutor.pojo.result.UserAuthResult;
import com.yoru.qingxintutor.service.AuthService;
import com.yoru.qingxintutor.service.VerificationCodeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/register-user")
    public ApiResult<UserAuthResponse> registerStudent(@Valid @RequestBody UserRegisterRequest userRegisterRequest)
            throws BusinessException {
        UserAuthResult userAuthResult = authService.registerStudent(userRegisterRequest);
        UserAuthResponse userAuthResponse = UserAuthResponse.builder()
                .expireIn(userAuthResult.getExpireIn())
                .token(userAuthResult.getToken())
                .user(UserAuthResponse.AuthedUser.builder()
                        .id(userAuthResult.getUserId())
                        .username(userAuthResult.getUsername())
                        .role(userAuthResult.getUserRole())
                        .build())
                .build();
        return ApiResult.success(userAuthResponse);
    }

    @PostMapping("/register-teacher")
    public ApiResult<UserAuthResponse> registerStudent(@Valid @RequestBody TeacherRegisterRequest teacherRegisterRequest)
            throws BusinessException {
        UserAuthResult userAuthResult = authService.registerTeacher(teacherRegisterRequest);
        UserAuthResponse userAuthResponse = UserAuthResponse.builder()
                .expireIn(userAuthResult.getExpireIn())
                .token(userAuthResult.getToken())
                .user(UserAuthResponse.AuthedUser.builder()
                        .id(userAuthResult.getUserId())
                        .username(userAuthResult.getUsername())
                        .role(userAuthResult.getUserRole())
                        .build())
                .build();
        return ApiResult.success(userAuthResponse);
    }

    @PostMapping("/login")
    public ApiResult<UserAuthResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest)
            throws BusinessException {
        UserAuthResult userAuthResult = authService.login(userLoginRequest);
        UserAuthResponse response = UserAuthResponse.builder()
                .token(userAuthResult.getToken())
                .expireIn(userAuthResult.getExpireIn())
                .user(UserAuthResponse.AuthedUser.builder()
                        .id(userAuthResult.getUserId())
                        .username(userAuthResult.getUsername())
                        .role(userAuthResult.getUserRole())
                        .build())
                .build();
        return ApiResult.success(response);
    }

    @PostMapping("/reset-password")
    public ApiResult<UserAuthResponse> resetPassword(@Valid @RequestBody UserResetPasswordRequest userResetPasswordRequest)
            throws BusinessException {
        authService.resetPassword(userResetPasswordRequest);
        return ApiResult.success();
    }

    @PostMapping("/send-code")
    public ApiResult<String> sendCode(@Valid @RequestBody UserSendCodeRequest userSendCodeRequest)
            throws BusinessException {
        verificationCodeService.sendVerificationCode(userSendCodeRequest.getEmail(), userSendCodeRequest.getPurpose());
        return ApiResult.success("If the email is valid, a verification code has been sent.");
    }
}
