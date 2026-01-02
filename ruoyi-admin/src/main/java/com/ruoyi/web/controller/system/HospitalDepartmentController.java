package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.HospitalDepartment;
import com.ruoyi.system.service.IHospitalDepartmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科室信息Controller
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
@RestController
@RequestMapping("/system/departmentKeshe")
public class HospitalDepartmentController extends BaseController
{
    @Autowired
    private IHospitalDepartmentService hospitalDepartmentService;

    /**
     * 查询科室信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:departmentKeshe:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalDepartment hospitalDepartment)
    {
        startPage();
        List<HospitalDepartment> list = hospitalDepartmentService.selectHospitalDepartmentList(hospitalDepartment);
        return getDataTable(list);
    }

    /**
     * 导出科室信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:departmentKeshe:export')")
    @Log(title = "科室信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalDepartment hospitalDepartment)
    {
        List<HospitalDepartment> list = hospitalDepartmentService.selectHospitalDepartmentList(hospitalDepartment);
        ExcelUtil<HospitalDepartment> util = new ExcelUtil<HospitalDepartment>(HospitalDepartment.class);
        util.exportExcel(response, list, "科室信息数据");
    }

    /**
     * 获取科室信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:departmentKeshe:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable("deptId") Long deptId)
    {
        return success(hospitalDepartmentService.selectHospitalDepartmentByDeptId(deptId));
    }

    /**
     * 新增科室信息
     */
    @PreAuthorize("@ss.hasPermi('system:departmentKeshe:add')")
    @Log(title = "科室信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalDepartment hospitalDepartment)
    {
        return toAjax(hospitalDepartmentService.insertHospitalDepartment(hospitalDepartment));
    }

    /**
     * 修改科室信息
     */
    @PreAuthorize("@ss.hasPermi('system:departmentKeshe:edit')")
    @Log(title = "科室信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalDepartment hospitalDepartment)
    {
        return toAjax(hospitalDepartmentService.updateHospitalDepartment(hospitalDepartment));
    }

    /**
     * 删除科室信息
     */
    @PreAuthorize("@ss.hasPermi('system:departmentKeshe:remove')")
    @Log(title = "科室信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptIds}")
    public AjaxResult remove(@PathVariable Long[] deptIds)
    {
        return toAjax(hospitalDepartmentService.deleteHospitalDepartmentByDeptIds(deptIds));
    }
}
