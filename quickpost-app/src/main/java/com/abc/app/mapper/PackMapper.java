package com.abc.app.mapper;

import java.util.List;
import com.abc.app.domain.entity.Pack;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 包裹Mapper接口
 *
 * @author LiJunXi
 * @date 2025-09-22
 */
public interface PackMapper extends BaseMapper<Pack> {
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
     * @param pack 包裹
     * @return 结果
     */
    int insertPack(Pack pack);

    /**
     * 修改包裹
     *
     * @param pack 包裹
     * @return 结果
     */
    int updatePack(Pack pack);

    /**
     * 删除包裹
     *
     * @param packId 包裹主键
     * @return 结果
     */
    int deletePackByPackId(Long packId);

    /**
     * 批量删除包裹
     *
     * @param packIds 需要删除的数据主键集合
     * @return 结果
     */
    int deletePackByPackIds(Long[] packIds);
}
