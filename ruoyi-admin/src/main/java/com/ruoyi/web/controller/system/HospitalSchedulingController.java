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
import com.ruoyi.system.domain.HospitalScheduling;
import com.ruoyi.system.service.IHospitalSchedulingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医生排班Controller
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
@RestController
@RequestMapping("/system/schedulingKeshe")
public class HospitalSchedulingController extends BaseController
{
    @Autowired
    private IHospitalSchedulingService hospitalSchedulingService;

    /**
     * 查询医生排班列表
     */
    @PreAuthorize("@ss.hasPermi('system:schedulingKeshe:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalScheduling hospitalScheduling)
    {
        startPage();
        List<HospitalScheduling> list = hospitalSchedulingService.selectHospitalSchedulingList(hospitalScheduling);
        return getDataTable(list);
    }

    /**
     * 导出医生排班列表
     */
    @PreAuthorize("@ss.hasPermi('system:schedulingKeshe:export')")
    @Log(title = "医生排班", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalScheduling hospitalScheduling)
    {
        List<HospitalScheduling> list = hospitalSchedulingService.selectHospitalSchedulingList(hospitalScheduling);
        ExcelUtil<HospitalScheduling> util = new ExcelUtil<HospitalScheduling>(HospitalScheduling.class);
        util.exportExcel(response, list, "医生排班数据");
    }

    /**
     * 获取医生排班详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:schedulingKeshe:query')")
    @GetMapping(value = "/{scheduleId}")
    public AjaxResult getInfo(@PathVariable("scheduleId") Long scheduleId)
    {
        return success(hospitalSchedulingService.selectHospitalSchedulingByScheduleId(scheduleId));
    }

    /**
     * 新增医生排班
     */
    @PreAuthorize("@ss.hasPermi('system:schedulingKeshe:add')")
    @Log(title = "医生排班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalScheduling hospitalScheduling)
    {
        return toAjax(hospitalSchedulingService.insertHospitalScheduling(hospitalScheduling));
    }

    /**
     * 修改医生排班
     */
    @PreAuthorize("@ss.hasPermi('system:schedulingKeshe:edit')")
    @Log(title = "医生排班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalScheduling hospitalScheduling)
    {
        return toAjax(hospitalSchedulingService.updateHospitalScheduling(hospitalScheduling));
    }

    /**
     * 删除医生排班
     */
    @PreAuthorize("@ss.hasPermi('system:schedulingKeshe:remove')")
    @Log(title = "医生排班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{scheduleIds}")
    public AjaxResult remove(@PathVariable Long[] scheduleIds)
    {
        return toAjax(hospitalSchedulingService.deleteHospitalSchedulingByScheduleIds(scheduleIds));
    }
}
