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
import com.ruoyi.system.domain.HospitalAppointment;
import com.ruoyi.system.service.IHospitalAppointmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 挂号预约记录Controller
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
@RestController
@RequestMapping("/system/appointmentKeshe")
public class HospitalAppointmentController extends BaseController
{
    @Autowired
    private IHospitalAppointmentService hospitalAppointmentService;

    /**
     * 查询挂号预约记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:appointmentKeshe:list') || @ss.hasRole('patient')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalAppointment hospitalAppointment)
    {
        startPage();
        List<HospitalAppointment> list = hospitalAppointmentService.selectHospitalAppointmentList(hospitalAppointment);
        return getDataTable(list);
    }

    /**
     * 导出挂号预约记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:appointmentKeshe:export')")
    @Log(title = "挂号预约记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalAppointment hospitalAppointment)
    {
        List<HospitalAppointment> list = hospitalAppointmentService.selectHospitalAppointmentList(hospitalAppointment);
        ExcelUtil<HospitalAppointment> util = new ExcelUtil<HospitalAppointment>(HospitalAppointment.class);
        util.exportExcel(response, list, "挂号预约记录数据");
    }

    /**
     * 获取挂号预约记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:appointmentKeshe:query')")
    @GetMapping(value = "/{appointmentId}")
    public AjaxResult getInfo(@PathVariable("appointmentId") Long appointmentId)
    {
        return success(hospitalAppointmentService.selectHospitalAppointmentByAppointmentId(appointmentId));
    }

    /**
     * 新增挂号预约记录
     */
    @PreAuthorize("@ss.hasPermi('system:appointmentKeshe:add')")
    @Log(title = "挂号预约记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalAppointment hospitalAppointment)
    {
        return toAjax(hospitalAppointmentService.insertHospitalAppointment(hospitalAppointment));
    }

    /**
     * 修改挂号预约记录
     */
    @PreAuthorize("@ss.hasPermi('system:appointmentKeshe:edit')")
    @Log(title = "挂号预约记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalAppointment hospitalAppointment)
    {
        return toAjax(hospitalAppointmentService.updateHospitalAppointment(hospitalAppointment));
    }

    /**
     * 删除挂号预约记录
     */
    @PreAuthorize("@ss.hasPermi('system:appointmentKeshe:remove')")
    @Log(title = "挂号预约记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{appointmentIds}")
    public AjaxResult remove(@PathVariable Long[] appointmentIds)
    {
        return toAjax(hospitalAppointmentService.deleteHospitalAppointmentByAppointmentIds(appointmentIds));
    }
}
