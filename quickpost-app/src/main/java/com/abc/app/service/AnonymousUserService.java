package com.abc.app.service;

import java.util.List;
import com.abc.app.domain.entity.AnonymousUser;
import com.abc.app.domain.vo.AnonymousUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 匿名用户Service接口
 *
 * @author LiJunXi
 * @date 2025-09-23
 */
public interface AnonymousUserService extends IService<AnonymousUser> {
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
     * 批量删除匿名用户
     *
     * @param anyUserIds 需要删除的匿名用户主键集合
     * @return 结果
     */
    int deleteAnonymousUserByAnyUserIds(Long[] anyUserIds);

    /**
     * 删除匿名用户信息
     *
     * @param anyUserId 匿名用户主键
     * @return 结果
     */
    int deleteAnonymousUserByAnyUserId(Long anyUserId);

    AnonymousUserVO generateAnonymousUser();
}
