package com.abc.framework.web.strategy.oss;

import com.abc.common.exception.GlobalException;
import com.abc.common.utils.AssertUtil;
import com.abc.common.utils.file.FileUploadUtils;
import com.abc.system.domain.dto.OssFileDTO;
import com.abc.system.domain.entity.File;
import com.abc.system.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author: LiJunXi
 * @Description:
 * @Date: 2025-08-24  14:37
 */
@Slf4j
@Service("localOssStrategy")
public class LocalOssStrategy implements OssStrategy {

    @Value("${oss.local.path}")
    private String filePath;

    @Override
    public void saveFile(OssFileDTO file) {
        String extension = FileUtil.getFileExtension(file.getMultipartFile().getOriginalFilename());
        String filename = filePath + FileUtil.getFileUploadPath(extension, file.getFileMd5());
        try {
            java.io.File desc = new java.io.File(filename);
            if (!desc.exists()) {
                if (!desc.getParentFile().exists()) {
                    desc.getParentFile().mkdirs();
                }
            }
            file.getMultipartFile().transferTo(Paths.get(filename));
        } catch (Exception e) {
            log.error("上传文件出错：{}", e.getMessage());
            throw new GlobalException("上传文件出错");
        }
        file.setFilePath(filename);
    }

    @Override
    public byte[] getFile(File fileEntity) {
        java.io.File file = new java.io.File(fileEntity.getFilePath());
        AssertUtil.isTrue(file.exists(), "文件已损坏");
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            log.error("读取文件失败：{}", e.getMessage());
            throw new GlobalException("读取文件失败");
        }
    }

    @Override
    public void deleteFile(File fileEntity) {
        java.io.File file = new java.io.File(fileEntity.getFilePath());
        AssertUtil.isTrue(file.exists(), "文件已损坏");
        file.delete();
    }
}

