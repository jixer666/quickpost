package com.abc.app.domain.entity;

import com.abc.common.core.domain.BaseCustomEntity;
import com.abc.common.enums.StatusEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.abc.common.annotation.Excel;
import lombok.NoArgsConstructor;

/**
 * 匿名用户对象 tb_anonymous_user
 *
 * @author LiJunXi
 * @date 2025-09-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_anonymous_user")
public class AnonymousUser extends BaseCustomEntity {
    private static final long serialVersionUID = 1L;

    /** 匿名用户ID */
    private Long anyUserId;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ip;

    public static AnonymousUser buildDefaultInsert(Long anyUserId, String ipAddr) {
        AnonymousUser anonymousUser = AnonymousUser.builder()
                .anyUserId(anyUserId)
                .ip(ipAddr)
                .build();
        anonymousUser.setCommonParams();

        return anonymousUser;
    }
}
