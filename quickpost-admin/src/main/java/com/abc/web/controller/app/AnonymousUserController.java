package com.abc.web.controller.app;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.abc.app.domain.vo.AnonymousUserVO;
import com.abc.common.core.domain.ApiResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.abc.common.annotation.Log;
import com.abc.common.core.controller.BaseController;
import com.abc.common.core.domain.AjaxResult;
import com.abc.common.enums.BusinessType;
import com.abc.app.domain.entity.AnonymousUser;
import com.abc.app.service.AnonymousUserService;
import com.abc.common.utils.poi.ExcelUtil;
import com.abc.common.core.page.TableDataInfo;

/**
 * 匿名用户Controller
 *
 * @author LiJunXi
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/app/anonymous")
public class AnonymousUserController extends BaseController {
    @Autowired
    private AnonymousUserService anonymousUserService;

    /**
     * 查询匿名用户列表
     */
    @PreAuthorize("@ss.hasPermi('app:anonymousUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(AnonymousUser anonymousUser) {
        startPage();
        List<AnonymousUser> list = anonymousUserService.selectAnonymousUserList(anonymousUser);
        return getDataTable(list);
    }

    /**
     * 导出匿名用户列表
     */
    @PreAuthorize("@ss.hasPermi('app:anonymousUser:export')")
    @Log(title = "匿名用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AnonymousUser anonymousUser) {
        List<AnonymousUser> list = anonymousUserService.selectAnonymousUserList(anonymousUser);
        ExcelUtil<AnonymousUser> util = new ExcelUtil<AnonymousUser>(AnonymousUser.class);
        util.exportExcel(response, list, "匿名用户数据");
    }

    /**
     * 获取匿名用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:anonymousUser:query')")
    @GetMapping(value = "/{anyUserId}")
    public AjaxResult getInfo(@PathVariable("anyUserId") Long anyUserId) {
        return success(anonymousUserService.selectAnonymousUserByAnyUserId(anyUserId));
    }

    /**
     * 新增匿名用户
     */
    @PreAuthorize("@ss.hasPermi('app:anonymousUser:add')")
    @Log(title = "匿名用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AnonymousUser anonymousUser) {
        return toAjax(anonymousUserService.insertAnonymousUser(anonymousUser));
    }

    /**
     * 修改匿名用户
     */
    @PreAuthorize("@ss.hasPermi('app:anonymousUser:edit')")
    @Log(title = "匿名用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AnonymousUser anonymousUser) {
        return toAjax(anonymousUserService.updateAnonymousUser(anonymousUser));
    }

    /**
     * 删除匿名用户
     */
    @PreAuthorize("@ss.hasPermi('app:anonymousUser:remove')")
    @Log(title = "匿名用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{anyUserIds}")
    public AjaxResult remove(@PathVariable Long[] anyUserIds) {
        return toAjax(anonymousUserService.deleteAnonymousUserByAnyUserIds(anyUserIds));
    }

    @ApiOperation("生成一个匿名用户")
    @PostMapping("/generate")
    public ApiResult<AnonymousUserVO> generate() {
        return ApiResult.ok(anonymousUserService.generateAnonymousUser());
    }
}
