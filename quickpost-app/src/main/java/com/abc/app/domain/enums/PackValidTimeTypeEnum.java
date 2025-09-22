package com.abc.app.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PackValidTimeTypeEnum {

    ONE(1, 1),
    TWO(2, 2),
    THREE(3, 5),
    FOUR(4, 12);

    private final Integer type;
    private final Integer value;

    public static Integer getValueByType(Integer type) {
        return Arrays.stream(PackValidTimeTypeEnum.values()).filter(item -> item.getType().equals(type)).findFirst().get().getType();
    }

}
