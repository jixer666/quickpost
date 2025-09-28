package com.abc.app.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import com.abc.app.domain.dto.PackDTO;
import com.abc.app.domain.enums.PackStatusEnum;
import com.abc.app.domain.enums.PackValidTimeTypeEnum;
import com.abc.app.domain.vo.PackVO;
import com.abc.app.service.AnonymousUserService;
import com.abc.app.util.PackCodeUtil;
import com.abc.common.constant.CacheConstants;
import com.abc.common.utils.AssertUtil;
import com.abc.common.utils.DateUtils;
import com.abc.common.utils.RedisUtils;
import com.abc.system.domain.entity.File;
import com.abc.system.service.FileService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.app.mapper.PackMapper;
import com.abc.app.domain.entity.Pack;
import com.abc.app.service.PackService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 包裹Service业务层处理
 *
 * @author LiJunXi
 * @date 2025-09-22
 */
@Service
public class PackServiceImpl extends ServiceImpl<PackMapper, Pack> implements PackService {

    @Autowired
    private PackMapper packMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private AnonymousUserService anonymousUserService;

    /**
     * 查询包裹
     *
     * @param packId 包裹主键
     * @return 包裹
     */
    @Override
    public Pack selectPackByPackId(Long packId) {
        return packMapper.selectPackByPackId(packId);
    }

    public Pack selectPackByPackCode(String code) {
        AssertUtil.isNotEmpty(code, "取件码包裹ID不能为空");

        return packMapper.selectPackByPackCode(code, PackStatusEnum.WAIT.getStatus());
    }

    @Override
    public PackVO selectPackByPackCodeWithUiParam(String code) {
        AssertUtil.isNotEmpty(code, "取件码不能为空");

        Pack pack = selectPackByPackCode(code);

        AssertUtil.isFalse(Objects.isNull(pack) || pack.isComplete(), "包裹不存在");

        Boolean isValid = checkPackValid(pack.getPackId());
        if (!isValid) {
            updatePackStatus(pack.getPackId(), PackStatusEnum.INVALID.getStatus());
        }
        AssertUtil.isTrue(isValid, "包裹已过期");

        List<File> files = fileService.selectFileByFileIds(pack.getFileIds());
        updatePackStatus(pack.getPackId(), PackStatusEnum.COMPLETE.getStatus());
        validPackCodeAgain(pack.getCode());

        return PackVO.buildByPack(pack, files);
    }

    private void validPackCodeAgain(String code) {
        AssertUtil.isNotEmpty(code, "取件码不能为空");
        PackCodeUtil.setCode(code);
    }

    private void updatePackStatus(Long packId, Integer status) {
        AssertUtil.isNotEmpty(packId, "包裹ID不能为空");
        AssertUtil.isNotEmpty(status, "状态不能为空");

        packMapper.update(null, new LambdaUpdateWrapper<Pack>()
                                .eq(Pack::getPackId, packId)
                                .set(Pack::getStatus, status));
    }

    /**
     * 查询包裹列表
     *
     * @param pack 包裹
     * @return 包裹
     */
    @Override
    public List<Pack> selectPackList(Pack pack) {
        return packMapper.selectPackList(pack);
    }

    /**
     * 新增包裹
     *
     * @param packDTO 包裹
     * @return 结果
     */
    @Override
    @Transactional
    public PackVO insertPack(PackDTO packDTO) {
        packDTO.checkInsertParams();
        Pack pack = Pack.buildDefaultInsert(packDTO);
        buildPackCode(pack);
        validPack(pack);
        anonymousUserService.saveAnonymousUserByAnyUserId(pack.getUserId());

        packMapper.insertPack(pack);

        return PackVO.buildByPack(pack);
    }

    private void buildPackCode(Pack pack) {
        AssertUtil.isNotEmpty(pack, "包裹不能为空");
        String packCode = PackCodeUtil.getCode();
        AssertUtil.isNotEmpty(packCode, "当前系统存放包裹已上限，请稍后再试");
        pack.setCode(packCode);
    }

    private void validPack(Pack pack) {
        AssertUtil.isNotEmpty(pack, "包裹不能为空");
        AssertUtil.isNotEmpty(pack.getPackId(), "包裹ID不能为空");
        AssertUtil.isNotEmpty(pack.getValidTimeType(), "有效期不能为空");
        RedisUtils.set(CacheConstants.PACK_VALID_TIME_KEY + pack.getPackId(),
                pack,
                PackValidTimeTypeEnum.getValueByType(pack.getValidTimeType()),
                TimeUnit.HOURS);
    }

    private Boolean checkPackValid(Long packId) {
        AssertUtil.isNotEmpty(packId, "包裹ID不能为空");
        String packStr = RedisUtils.get(CacheConstants.PACK_VALID_TIME_KEY + packId);
        if (StrUtil.isBlank(packStr)) {
            return false;
        }
        return true;
    }

    /**
     * 修改包裹
     *
     * @param pack 包裹
     * @return 结果
     */
    @Override
    public int updatePack(Pack pack) {
        pack.setUpdateTime(DateUtils.getNowDate());
        return packMapper.updatePack(pack);
    }

    /**
     * 批量删除包裹
     *
     * @param packIds 需要删除的包裹主键
     * @return 结果
     */
    @Override
    public int deletePackByPackIds(Long[] packIds) {
        return packMapper.deletePackByPackIds(packIds);
    }

    /**
     * 删除包裹信息
     *
     * @param packId 包裹主键
     * @return 结果
     */
    @Override
    public int deletePackByPackId(Long packId) {
        return packMapper.deletePackByPackId(packId);
    }
}
