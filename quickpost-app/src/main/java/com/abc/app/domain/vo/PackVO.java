package com.abc.app.domain.vo;

import com.abc.app.domain.entity.Pack;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PackVO {

    private Long packId;

    private String code;

    public static PackVO buildByPack(Pack pack) {
        return PackVO.builder()
                .packId(pack.getPackId())
                .code(pack.getCode())
                .build();
    }
}
