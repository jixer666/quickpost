package com.abc.app.mapper;

import java.util.List;
import com.abc.app.domain.entity.AnonymousUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 匿名用户Mapper接口
 *
 * @author LiJunXi
 * @date 2025-09-23
 */
public interface AnonymousUserMapper extends BaseMapper<AnonymousUser> {
    /**
     * 查询匿名用户
     *
     * @param anyUserId 匿名用户主键
     * @return 匿名用户
     */
    AnonymousUser selectAnonymousUserByAnyUserId(Long anyUserId);

    /**
     * 查询匿名用户列表
     *
     * @param anonymousUser 匿名用户
     * @return 匿名用户集合
     */
    List<AnonymousUser> selectAnonymousUserList(AnonymousUser anonymousUser);

    /**
     * 新增匿名用户
     *
     * @param anonymousUser 匿名用户
     * @return 结果
     */
    int insertAnonymousUser(AnonymousUser anonymousUser);

    /**
     * 修改匿名用户
     *
     * @param anonymousUser 匿名用户
     * @return 结果
     */
    int updateAnonymousUser(AnonymousUser anonymousUser);

    /**
     * 删除匿名用户
     *
     * @param anyUserId 匿名用户主键
     * @return 结果
     */
    int deleteAnonymousUserByAnyUserId(Long anyUserId);

    /**
     * 批量删除匿名用户
     *
     * @param anyUserIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAnonymousUserByAnyUserIds(Long[] anyUserIds);
}
