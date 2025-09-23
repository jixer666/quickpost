package com.abc.framework.web.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenTypeEnum {

    SYSTEM(1),
    ANONYMOUS(2);

    private Integer type;

}
