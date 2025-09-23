package com.abc.web.controller.app;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.abc.app.domain.dto.PackDTO;
import com.abc.app.domain.vo.PackVO;
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
import com.abc.app.domain.entity.Pack;
import com.abc.app.service.PackService;
import com.abc.common.utils.poi.ExcelUtil;
import com.abc.common.core.page.TableDataInfo;

/**
 * 包裹Controller
 *
 * @author LiJunXi
 * @date 2025-09-22
 */
@RestController
@RequestMapping("/app/pack")
public class PackController extends BaseController {
    @Autowired
    private PackService packService;

    /**
     * 查询包裹列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Pack pack) {
        startPage();
        List<Pack> list = packService.selectPackList(pack);
        return getDataTable(list);
    }

    /**
     * 导出包裹列表
     */
    @Log(title = "包裹", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Pack pack) {
        List<Pack> list = packService.selectPackList(pack);
        ExcelUtil<Pack> util = new ExcelUtil<Pack>(Pack.class);
        util.exportExcel(response, list, "包裹数据");
    }

    /**
     * 获取包裹详细信息
     */
    @GetMapping(value = "/{packId}")
    public AjaxResult getInfo(@PathVariable("packId") Long packId) {
        return success(packService.selectPackByPackId(packId));
    }

    @ApiOperation("提取文件")
    @GetMapping("/code/{code}")
    public ApiResult<PackVO> getInfoByCode(@PathVariable("code") String code) {
        return ApiResult.ok(packService.selectPackByPackCodeWithUiParam(code));
    }

    /**
     * 新增包裹
     */
    @Log(title = "包裹", businessType = BusinessType.INSERT)
    @PostMapping
    public ApiResult<PackVO> add(@RequestBody PackDTO packDTO) {
        return ApiResult.ok(packService.insertPack(packDTO));
    }

    /**
     * 修改包裹
     */
    @Log(title = "包裹", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Pack pack) {
        return toAjax(packService.updatePack(pack));
    }

    /**
     * 删除包裹
     */
    @Log(title = "包裹", businessType = BusinessType.DELETE)
	@DeleteMapping("/{packIds}")
    public AjaxResult remove(@PathVariable Long[] packIds) {
        return toAjax(packService.deletePackByPackIds(packIds));
    }
}
