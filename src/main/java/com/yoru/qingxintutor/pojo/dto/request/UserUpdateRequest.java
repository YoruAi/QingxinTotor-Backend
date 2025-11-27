package com.yoru.qingxintutor.pojo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
    private String username;

    @Size(min = 1, max = 50, message = "Nickname must be between 1 and 50 characters")
    private String nickname;

    @Size(min = 1, max = 255, message = "Address must be between 1 and 255 characters")
    private String address;
}
