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
import com.ruoyi.system.domain.HospitalPatient;
import com.ruoyi.system.service.IHospitalPatientService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 患者信息Controller
 * 
 * @author Shuhan
 * @date 2025-12-31
 */
@RestController
@RequestMapping("/system/patientKeshe")
public class HospitalPatientController extends BaseController
{
    @Autowired
    private IHospitalPatientService hospitalPatientService;

    /**
     * 查询患者信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:patientKeshe:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalPatient hospitalPatient)
    {
        startPage();
        List<HospitalPatient> list = hospitalPatientService.selectHospitalPatientList(hospitalPatient);
        return getDataTable(list);
    }

    /**
     * 导出患者信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:patientKeshe:export')")
    @Log(title = "患者信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalPatient hospitalPatient)
    {
        List<HospitalPatient> list = hospitalPatientService.selectHospitalPatientList(hospitalPatient);
        ExcelUtil<HospitalPatient> util = new ExcelUtil<HospitalPatient>(HospitalPatient.class);
        util.exportExcel(response, list, "患者信息数据");
    }

    /**
     * 获取患者信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:patientKeshe:query')")
    @GetMapping(value = "/{patientId}")
    public AjaxResult getInfo(@PathVariable("patientId") Long patientId)
    {
        return success(hospitalPatientService.selectHospitalPatientByPatientId(patientId));
    }

    /**
     * 新增患者信息
     */
    @PreAuthorize("@ss.hasPermi('system:patientKeshe:add')")
    @Log(title = "患者信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalPatient hospitalPatient)
    {
        return toAjax(hospitalPatientService.insertHospitalPatient(hospitalPatient));
    }

    /**
     * 修改患者信息
     */
    @PreAuthorize("@ss.hasPermi('system:patientKeshe:edit')")
    @Log(title = "患者信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalPatient hospitalPatient)
    {
        return toAjax(hospitalPatientService.updateHospitalPatient(hospitalPatient));
    }

    /**
     * 删除患者信息
     */
    @PreAuthorize("@ss.hasPermi('system:patientKeshe:remove')")
    @Log(title = "患者信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{patientIds}")
    public AjaxResult remove(@PathVariable Long[] patientIds)
    {
        return toAjax(hospitalPatientService.deleteHospitalPatientByPatientIds(patientIds));
    }
}
