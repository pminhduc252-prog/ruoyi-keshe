package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.ruoyi.system.domain.HospitalPatient; // 引入患者实体
import com.ruoyi.system.mapper.HospitalPatientMapper; // 引入患者Mapper

/**
 * 挂号预约记录Controller
 * * @author Shuhan
 * @date 2026-01-02
 */
@RestController
@RequestMapping("/system/appointmentKeshe")
public class HospitalAppointmentController extends BaseController
{
    @Autowired
    private IHospitalAppointmentService hospitalAppointmentService;

    @Autowired
    private com.ruoyi.system.service.IHospitalSchedulingService schedulingService;

    // 【新增】注入患者Mapper，用于查询当前用户的档案
    @Autowired
    private HospitalPatientMapper hospitalPatientMapper;

    /**
     * 查询挂号预约记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:appointmentKeshe:list') || @ss.hasRole('patient')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalAppointment hospitalAppointment)
    {
        // 1. 获取当前用户ID
        Long userId = getUserId();

        // 2. 权限判断逻辑优化
        // 判断是否为超级管理员 (ID=1) 或者拥有 'admin' 角色
        boolean isAdmin = com.ruoyi.common.utils.SecurityUtils.isAdmin(userId) 
                       || com.ruoyi.common.utils.SecurityUtils.hasRole("admin");

        // 只有 "非管理员" 且 "拥有患者角色" 时，才强制筛选
        if (!isAdmin && com.ruoyi.common.utils.SecurityUtils.hasRole("patient")) {
            // 查出该用户的档案
            HospitalPatient patient = hospitalPatientMapper.selectHospitalPatientByUserId(userId);
            
            if (patient != null) {
                // 只查询属于这个 patientId 的记录
                hospitalAppointment.setPatientId(patient.getPatientId());
            } else {
                // 是患者但没档案，直接返回空
                return getDataTable(new java.util.ArrayList<>());
            }
        }

        // 3. 执行查询 (管理员查所有，普通患者查自己)
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
     * 【核心】患者端在线预约
     */
    @PreAuthorize("@ss.hasRole('patient')")
    @Log(title = "在线预约", businessType = BusinessType.INSERT)
    @PostMapping("/book")
    @Transactional
    public AjaxResult book(@RequestBody HospitalAppointment appointment)
    {
        // 1. 获取前端传来的排班ID
        Long scheduleId = appointment.getScheduleId();
        if (scheduleId == null) {
            return error("预约失败：未选择排班");
        }

        // 2. 【核心修改】查询当前登录用户绑定的患者档案
        Long currentUserId = getUserId();
        HospitalPatient patient = hospitalPatientMapper.selectHospitalPatientByUserId(currentUserId);

        if (patient == null) {
            return error("预约失败：您尚未完善个人就诊档案！请先前往'患者信息管理'添加您的信息。");
        }

        // 3. 校验排班
        com.ruoyi.system.domain.HospitalScheduling schedule = schedulingService.selectHospitalSchedulingByScheduleId(scheduleId);
        if (schedule == null) {
            return error("该排班不存在");
        }
        if ("1".equals(schedule.getStatus())) {
            return error("该排班已停诊");
        }
        if (schedule.getAvailableQuota() <= 0) {
            return error("手慢了，该时段号源已抢完！");
        }

        // 4. 扣减库存
        schedule.setAvailableQuota(schedule.getAvailableQuota() - 1);
        schedulingService.updateHospitalScheduling(schedule);

        // 5. 组装数据
        appointment.setAppointmentId(null);
        
        // 【重点】这里不再填 userId，而是填真实的 patient_id
        appointment.setPatientId(patient.getPatientId()); 
        
        appointment.setDoctorId(schedule.getDoctorId());
        appointment.setVisitDate(schedule.getWorkDate()); 
        appointment.setStatus("0"); // 0=待就诊
        
        if (appointment.getRemark() != null) {
            appointment.setSymptomDesc(appointment.getRemark());
        }

        int rows = hospitalAppointmentService.insertHospitalAppointment(appointment);

        // 6. 模拟短信
        if (rows > 0) {
            System.out.println("============== 短信通知 ==============");
            System.out.println("发送给：" + patient.getName() + " (手机:" + patient.getPhone() + ")");
            System.out.println("内容：预约成功！医生：" + schedule.getDoctorName() + "，时间：" + schedule.getWorkDate());
            System.out.println("======================================");
        }

        return toAjax(rows);
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