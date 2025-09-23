package com.abc.app.domain.dto;

import com.abc.app.domain.entity.typehanlder.details.FileIdsArray;
import com.abc.common.utils.AssertUtil;
import lombok.Data;


@Data
public class PackDTO {

    private FileIdsArray fileIds;

    private Integer validTimeType;

    public void checkInsertParams() {
        AssertUtil.isNotEmpty(this, "参数不能为空");
        AssertUtil.isNotEmpty(fileIds, "上传文件不能为空");
        AssertUtil.isTrue(!fileIds.isEmpty(), "上传文件不能为空");
        AssertUtil.isNotEmpty(validTimeType, "有效期类型不能为空");
    }
}
