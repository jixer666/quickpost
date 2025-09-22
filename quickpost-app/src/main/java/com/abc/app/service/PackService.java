package com.abc.app.service;

import java.util.List;

import com.abc.app.domain.dto.PackDTO;
import com.abc.app.domain.entity.Pack;
import com.abc.app.domain.vo.PackVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 包裹Service接口
 *
 * @author LiJunXi
 * @date 2025-09-22
 */
public interface PackService extends IService<Pack> {
    /**
     * 查询包裹
     *
     * @param packId 包裹主键
     * @return 包裹
     */
    Pack selectPackByPackId(Long packId);

    /**
     * 查询包裹列表
     *
     * @param pack 包裹
     * @return 包裹集合
     */
    List<Pack> selectPackList(Pack pack);

    /**
     * 新增包裹
     *
     * @param packDTO 包裹
     * @return 结果
     */
    PackVO insertPack(PackDTO packDTO);

    /**
     * 修改包裹
     *
     * @param pack 包裹
     * @return 结果
     */
    int updatePack(Pack pack);

    /**
     * 批量删除包裹
     *
     * @param packIds 需要删除的包裹主键集合
     * @return 结果
     */
    int deletePackByPackIds(Long[] packIds);

    /**
     * 删除包裹信息
     *
     * @param packId 包裹主键
     * @return 结果
     */
    int deletePackByPackId(Long packId);
}
