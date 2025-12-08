package com.yoru.qingxintutor.controller;

import com.yoru.qingxintutor.enums.NotificationType;
import com.yoru.qingxintutor.exception.BusinessException;
import com.yoru.qingxintutor.filter.CustomUserDetails;
import com.yoru.qingxintutor.pojo.ApiResult;
import com.yoru.qingxintutor.pojo.result.NotificationInfoResult;
import com.yoru.qingxintutor.service.NotificationService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /*
    GET    /api/notification/all?type=global|personal
    GET    /api/notification/:id
     */
    @GetMapping("/all")
    public ApiResult<List<NotificationInfoResult>> getNotifications(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                    @RequestParam("type") NotificationType type) {
        if (type == NotificationType.GLOBAL)
            return ApiResult.success(notificationService.listGlobalNotifications());
        else if (type == NotificationType.PERSONAL)
            return ApiResult.success(notificationService.listPersonalNotifications(userDetails.getUser().getId()));
        else
            throw new BusinessException("Unknown notification type");
    }

    @GetMapping("/{id}")
    public ApiResult<?> getNotificationById(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable("id")
                                            @Min(value = 1, message = "Id must be a positive number")
                                            Long id) {
        return ApiResult.success(notificationService.findById(userDetails.getUser().getId(), id));
    }
}
