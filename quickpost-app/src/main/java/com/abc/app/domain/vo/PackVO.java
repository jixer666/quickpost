package com.abc.app.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.abc.app.domain.entity.Pack;
import com.abc.system.domain.entity.File;
import com.abc.system.domain.vo.FileVO;
import com.abc.system.util.FileUtil;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PackVO {

    private Long packId;

    private String code;

    private List<FileVO> fileList;

    public static PackVO buildByPack(Pack pack) {
        return PackVO.builder()
                .packId(pack.getPackId())
                .code(pack.getCode())
                .build();
    }

    public static PackVO buildByPack(Pack pack, List<File> files) {
        return PackVO.builder()
                .packId(pack.getPackId())
                .code(pack.getCode())
                .fileList(files.stream().map(item -> {
                    FileVO fileVO = BeanUtil.copyProperties(item, FileVO.class);
                    fileVO.setDownloadUrl(FileUtil.getFileDownloadUrl(item.getFileId()));
                    return fileVO;
                }).collect(Collectors.toList()))
                .build();
    }
}
