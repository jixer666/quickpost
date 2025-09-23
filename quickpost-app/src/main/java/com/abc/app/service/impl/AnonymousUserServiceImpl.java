package com.abc.app.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.abc.app.domain.vo.AnonymousUserVO;
import com.abc.common.constant.CacheConstants;
import com.abc.common.utils.AssertUtil;
import com.abc.common.utils.DateUtils;
import com.abc.common.utils.IdUtils;
import com.abc.common.utils.RedisUtils;
import com.abc.common.utils.ip.IpUtils;
import com.abc.framework.web.service.TokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.app.mapper.AnonymousUserMapper;
import com.abc.app.domain.entity.AnonymousUser;
import com.abc.app.service.AnonymousUserService;

/**
 * 匿名用户Service业务层处理
 *
 * @author LiJunXi
 * @date 2025-09-23
 */
@Service
public class AnonymousUserServiceImpl extends ServiceImpl<AnonymousUserMapper, AnonymousUser> implements AnonymousUserService {

    @Autowired
    private AnonymousUserMapper anonymousUserMapper;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询匿名用户
     *
     * @param anyUserId 匿名用户主键
     * @return 匿名用户
     */
    @Override
    public AnonymousUser selectAnonymousUserByAnyUserId(Long anyUserId) {
        return anonymousUserMapper.selectAnonymousUserByAnyUserId(anyUserId);
    }

    /**
     * 查询匿名用户列表
     *
     * @param anonymousUser 匿名用户
     * @return 匿名用户
     */
    @Override
    public List<AnonymousUser> selectAnonymousUserList(AnonymousUser anonymousUser) {
        return anonymousUserMapper.selectAnonymousUserList(anonymousUser);
    }

    /**
     * 新增匿名用户
     *
     * @param anonymousUser 匿名用户
     * @return 结果
     */
    @Override
    public int insertAnonymousUser(AnonymousUser anonymousUser) {
        anonymousUser.setCreateTime(DateUtils.getNowDate());
        return anonymousUserMapper.insertAnonymousUser(anonymousUser);
    }

    /**
     * 修改匿名用户
     *
     * @param anonymousUser 匿名用户
     * @return 结果
     */
    @Override
    public int updateAnonymousUser(AnonymousUser anonymousUser) {
        return anonymousUserMapper.updateAnonymousUser(anonymousUser);
    }

    /**
     * 批量删除匿名用户
     *
     * @param anyUserIds 需要删除的匿名用户主键
     * @return 结果
     */
    @Override
    public int deleteAnonymousUserByAnyUserIds(Long[] anyUserIds) {
        return anonymousUserMapper.deleteAnonymousUserByAnyUserIds(anyUserIds);
    }

    /**
     * 删除匿名用户信息
     *
     * @param anyUserId 匿名用户主键
     * @return 结果
     */
    @Override
    public int deleteAnonymousUserByAnyUserId(Long anyUserId) {
        return anonymousUserMapper.deleteAnonymousUserByAnyUserId(anyUserId);
    }

    @Override
    public AnonymousUserVO generateAnonymousUser() {
        Long anyUserId = IdUtils.getId();
        Boolean isSetAnonymousUser = RedisUtils.set(CacheConstants.ANONYMOUS_TEMP_ID + anyUserId, IpUtils.getIpAddr(),
                CacheConstants.ANONYMOUS_TEMP_ID_EXPIRE, TimeUnit.HOURS);
        AssertUtil.isTrue(isSetAnonymousUser, "分配匿名出错");

        return AnonymousUserVO.buildAnonymousUserVO(anyUserId, tokenService.createAnonymousUserToken(anyUserId));
    }
}
