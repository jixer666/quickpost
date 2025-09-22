package com.abc.framework.web.strategy.oss;

import com.abc.system.domain.dto.OssFileDTO;
import com.abc.system.domain.entity.File;

public interface OssStrategy {

    void saveFile(OssFileDTO file);

    byte[] getFile(File fileEntity);

    void deleteFile(File fileEntity);


}
