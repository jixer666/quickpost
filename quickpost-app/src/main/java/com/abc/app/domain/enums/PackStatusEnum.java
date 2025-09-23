package com.abc.app.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PackStatusEnum {

    WAIT(1, "待取件"),
    COMPLETE(2, "已取件"),
    INVALID(3, "已失效")
    ;

    private Integer status;
    private String desc;

}
