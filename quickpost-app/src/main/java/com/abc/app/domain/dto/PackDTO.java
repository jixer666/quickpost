package com.abc.app.domain.dto;

import com.abc.app.domain.entity.typehanlder.details.FileIdsArray;
import lombok.Data;


@Data
public class PackDTO {

    private FileIdsArray fileIds;

    private Integer validTimeType;

}
