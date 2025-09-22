package com.abc.app.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.RandomUtil;
import com.abc.app.domain.dto.PackDTO;
import com.abc.app.domain.enums.PackValidTimeTypeEnum;
import com.abc.app.domain.vo.PackVO;
import com.abc.common.constant.CacheConstants;
import com.abc.common.utils.AssertUtil;
import com.abc.common.utils.DateUtils;
import com.abc.common.utils.RedisUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.app.mapper.PackMapper;
import com.abc.app.domain.entity.Pack;
import com.abc.app.service.PackService;

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
    public PackVO insertPack(PackDTO packDTO) {
        checkInsertParams(packDTO);
        Pack pack = Pack.buildDefaultInsert(packDTO);
        buildPackCode(pack);
        validPack(pack.getPackId(), pack.getValidTimeType());

        packMapper.insertPack(pack);

        return PackVO.buildByPack(pack);
    }

    private void buildPackCode(Pack pack) {
        AssertUtil.isNotEmpty(pack, "包裹不能为空");
        pack.setCode(RandomUtil.randomNumbers(6));
    }

    private void validPack(Long packId, Integer validTimeType) {
        AssertUtil.isNotEmpty(packId, "包裹ID不能为空");
        AssertUtil.isNotEmpty(validTimeType, "有效期不能为空");
        RedisUtils.expire(CacheConstants.PACK_VALID_TIME_KEY + packId, PackValidTimeTypeEnum.getValueByType(validTimeType), TimeUnit.HOURS);
    }

    private void checkInsertParams(PackDTO packDTO) {
        AssertUtil.isNotEmpty(packDTO, "参数不能为空");
        AssertUtil.isNotEmpty(packDTO.getFileIds(), "上传文件不能为空");
        AssertUtil.isTrue(!packDTO.getFileIds().isEmpty(), "上传文件不能为空");
        AssertUtil.isNotEmpty(packDTO.getValidTimeType(), "有效期类型不能为空");
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
