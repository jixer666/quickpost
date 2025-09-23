package com.abc.app.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnonymousUserVO {

    private Long anyUserId;

    private String token;

    public static AnonymousUserVO buildAnonymousUserVO(Long anyUserId, String token) {
        return AnonymousUserVO.builder()
                .anyUserId(anyUserId)
                .token(token)
                .build();
    }
}
