package com.abc.app.domain.entity;

import cn.hutool.core.bean.BeanUtil;
import com.abc.app.domain.dto.PackDTO;
import com.abc.app.domain.entity.typehanlder.details.FileIdsArray;
import com.abc.app.domain.enums.PackStatusEnum;
import com.abc.common.core.domain.BaseCustomEntity;
import com.abc.common.utils.IdUtils;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.abc.common.annotation.Excel;

/**
 * 包裹对象 tb_pack
 *
 * @author LiJunXi
 * @date 2025-09-22
 */
@Data
@TableName("tb_pack")
public class Pack extends BaseCustomEntity {
    private static final long serialVersionUID = 1L;

    /** 包裹ID */
    private Long packId;

    /** 文件ID */
    @Excel(name = "文件ID列表")
    private FileIdsArray fileIds;

    /** 取件码 */
    @Excel(name = "取件码")
    private String code;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;


    /** 有效期类型 */
    @Excel(name = "有效期类型")
    private Integer validTimeType;


    public static Pack buildDefaultInsert(PackDTO packDTO) {
        Pack insert = BeanUtil.copyProperties(packDTO, Pack.class);
        insert.setPackId(IdUtils.getId());
        // todo
        insert.setUserId(IdUtils.getId());
        insert.setCommonParams();

        return insert;
    }

    public boolean isInValid() {
        return this.getStatus().equals(PackStatusEnum.INVALID.getStatus());
    }

    public boolean isComplete() {
        return this.getStatus().equals(PackStatusEnum.COMPLETE.getStatus());
    }
}
