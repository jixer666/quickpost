package com.abc.app.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.abc.common.annotation.Excel;
import com.abc.common.core.domain.BaseEntity;

/**
 * 匿名用户对象 tb_anonymous_user
 *
 * @author LiJunXi
 * @date 2025-09-23
 */
@Data
@TableName("tb_anonymous_user")
public class AnonymousUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 匿名用户ID */
    private Long anyUserId;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ip;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;


}
