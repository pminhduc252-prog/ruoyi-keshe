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
import com.ruoyi.system.domain.HospitalDoctor;
import com.ruoyi.system.service.IHospitalDoctorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医生信息Controller
 * 
 * @author Shuhan
 * @date 2026-01-01
 */
@RestController
@RequestMapping("/system/doctorKeshe")
public class HospitalDoctorController extends BaseController
{
    @Autowired
    private IHospitalDoctorService hospitalDoctorService;

    /**
     * 查询医生信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:doctorKeshe:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalDoctor hospitalDoctor)
    {
        startPage();
        List<HospitalDoctor> list = hospitalDoctorService.selectHospitalDoctorList(hospitalDoctor);
        return getDataTable(list);
    }

    /**
     * 导出医生信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:doctorKeshe:export')")
    @Log(title = "医生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalDoctor hospitalDoctor)
    {
        List<HospitalDoctor> list = hospitalDoctorService.selectHospitalDoctorList(hospitalDoctor);
        ExcelUtil<HospitalDoctor> util = new ExcelUtil<HospitalDoctor>(HospitalDoctor.class);
        util.exportExcel(response, list, "医生信息数据");
    }

    /**
     * 获取医生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:doctorKeshe:query')")
    @GetMapping(value = "/{doctorId}")
    public AjaxResult getInfo(@PathVariable("doctorId") Long doctorId)
    {
        return success(hospitalDoctorService.selectHospitalDoctorByDoctorId(doctorId));
    }

    /**
     * 新增医生信息
     */
    @PreAuthorize("@ss.hasPermi('system:doctorKeshe:add')")
    @Log(title = "医生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalDoctor hospitalDoctor)
    {
        return toAjax(hospitalDoctorService.insertHospitalDoctor(hospitalDoctor));
    }

    /**
     * 修改医生信息
     */
    @PreAuthorize("@ss.hasPermi('system:doctorKeshe:edit')")
    @Log(title = "医生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalDoctor hospitalDoctor)
    {
        return toAjax(hospitalDoctorService.updateHospitalDoctor(hospitalDoctor));
    }

    /**
     * 删除医生信息
     */
    @PreAuthorize("@ss.hasPermi('system:doctorKeshe:remove')")
    @Log(title = "医生信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{doctorIds}")
    public AjaxResult remove(@PathVariable Long[] doctorIds)
    {
        return toAjax(hospitalDoctorService.deleteHospitalDoctorByDoctorIds(doctorIds));
    }
}
