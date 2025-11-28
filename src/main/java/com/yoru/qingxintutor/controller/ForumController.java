package com.yoru.qingxintutor.controller;

import com.yoru.qingxintutor.filter.CustomUserDetails;
import com.yoru.qingxintutor.pojo.ApiResult;
import com.yoru.qingxintutor.pojo.dto.request.ForumMessageCreateRequest;
import com.yoru.qingxintutor.pojo.entity.ForumEntity;
import com.yoru.qingxintutor.pojo.result.ForumMessageInfoResult;
import com.yoru.qingxintutor.service.ForumMessageService;
import com.yoru.qingxintutor.service.ForumService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @Autowired
    private ForumMessageService forumMessageService;

    @Autowired
    private ForumService forumService;

    @PostMapping("/{forumId}/message")
    public ApiResult<ForumMessageInfoResult> sendMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @PathVariable
                                                         @Min(value = 1, message = "ForumId must be a positive number")
                                                         Long forumId,
                                                         @Valid @RequestBody ForumMessageCreateRequest forumMessageCreateRequest) {
        ForumMessageInfoResult result = forumMessageService.insert(userDetails.getUser().getId(),
                forumId, forumMessageCreateRequest);
        return ApiResult.success(result);
    }

    @GetMapping("/{forumId}/messages")
    public ApiResult<List<ForumMessageInfoResult>> getMessages(@PathVariable
                                                               @Min(value = 1, message = "ForumId must be a positive number")
                                                               Long forumId) {
        return ApiResult.success(forumMessageService.listAllByForumId(forumId));
    }

    @GetMapping("/all")
    public ApiResult<List<ForumEntity>> getForums() {
        return ApiResult.success(forumService.listAll());
    }

    @GetMapping("/{forumId}")
    public ApiResult<ForumEntity> getForumById(@PathVariable
                                               @Min(value = 1, message = "ForumId must be a positive number")
                                               Long forumId) {
        return ApiResult.success(forumService.findById(forumId));
    }
}
