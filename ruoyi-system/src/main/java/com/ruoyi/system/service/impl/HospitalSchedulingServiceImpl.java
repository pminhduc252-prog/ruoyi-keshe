package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.exception.ServiceException; // 引入异常类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 引入相关的 Mapper 和 Domain
import com.ruoyi.system.mapper.HospitalSchedulingMapper;
import com.ruoyi.system.domain.HospitalScheduling;
import com.ruoyi.system.service.IHospitalSchedulingService;
import com.ruoyi.system.mapper.HospitalDoctorMapper; // 必须引入医生Mapper
import com.ruoyi.system.domain.HospitalDoctor;     // 必须引入医生实体

/**
 * 医生排班Service业务层处理
 * * @author Shuhan
 * @date 2026-01-02
 */
@Service
public class HospitalSchedulingServiceImpl implements IHospitalSchedulingService 
{
    @Autowired
    private HospitalSchedulingMapper hospitalSchedulingMapper;

    @Autowired
    private HospitalDoctorMapper hospitalDoctorMapper; // 注入医生Mapper用于查询

    /**
     * 查询医生排班
     * * @param scheduleId 医生排班主键
     * @return 医生排班
     */
    @Override
    public HospitalScheduling selectHospitalSchedulingByScheduleId(Long scheduleId)
    {
        return hospitalSchedulingMapper.selectHospitalSchedulingByScheduleId(scheduleId);
    }

    /**
     * 查询医生排班列表
     * * @param hospitalScheduling 医生排班
     * @return 医生排班
     */
    @Override
    public List<HospitalScheduling> selectHospitalSchedulingList(HospitalScheduling hospitalScheduling)
    {
        return hospitalSchedulingMapper.selectHospitalSchedulingList(hospitalScheduling);
    }

    /**
     * 新增医生排班 (包含核心校验逻辑)
     * * @param hospitalScheduling 医生排班
     * @return 结果
     */
    @Override
    public int insertHospitalScheduling(HospitalScheduling hospitalScheduling)
    {
        // ---------------------------------------------------------
        // 1. 查人：校验医生是否存在
        // ---------------------------------------------------------
        Long doctorId = hospitalScheduling.getDoctorId();
        if (doctorId == null) {
            throw new ServiceException("排班失败：必须选择一名医生！");
        }

        // 使用 doctorId 查询医生信息
        HospitalDoctor doctor = hospitalDoctorMapper.selectHospitalDoctorByDoctorId(doctorId);
        if (doctor == null) {
            throw new ServiceException("排班失败：ID为 " + doctorId + " 的医生不存在！");
        }

        // ---------------------------------------------------------
        // 2. 校验：排班时间不能早于当前时间
        // ---------------------------------------------------------
        if (hospitalScheduling.getWorkDate() != null && 
            hospitalScheduling.getWorkDate().before(DateUtils.getNowDate())) {
            throw new ServiceException("排班失败：排班时间不能早于当前时间！");
        }

        // ---------------------------------------------------------
        // 3. 查重：同一医生 + 同一天 + 同一班次 不能重复排班
        // ---------------------------------------------------------
        HospitalScheduling query = new HospitalScheduling();
        query.setDoctorId(doctorId);
        query.setWorkDate(hospitalScheduling.getWorkDate());
        query.setShiftType(hospitalScheduling.getShiftType()); // 注意：字段名是 shiftType
        
        List<HospitalScheduling> exists = hospitalSchedulingMapper.selectHospitalSchedulingList(query);
        if (exists.size() > 0) {
            String dateStr = DateUtils.parseDateToStr("yyyy-MM-dd", hospitalScheduling.getWorkDate());
            // 翻译班次显示，让提示更友好
            String shiftStr = hospitalScheduling.getShiftType();
            if ("1".equals(shiftStr)) shiftStr = "上午";
            else if ("2".equals(shiftStr)) shiftStr = "下午";
            else if ("3".equals(shiftStr)) shiftStr = "晚班";
            
            throw new ServiceException("操作失败：医生 " + doctor.getDocName() + " 在 " + dateStr + " 的 " + shiftStr + " 已经排过班了！");
        }

        // ---------------------------------------------------------
        // 4. 补充默认数据并保存
        // ---------------------------------------------------------
        // 如果前端没填号源数，默认给30个
        if (hospitalScheduling.getTotalQuota() == null) {
            hospitalScheduling.setTotalQuota(30L);
        }
        // 可用号源初始值 = 总号源
        if (hospitalScheduling.getAvailableQuota() == null) {
            hospitalScheduling.setAvailableQuota(hospitalScheduling.getTotalQuota());
        }
        
        hospitalScheduling.setCreateTime(DateUtils.getNowDate());
        return hospitalSchedulingMapper.insertHospitalScheduling(hospitalScheduling);
    }

    /**
     * 修改医生排班
     * * @param hospitalScheduling 医生排班
     * @return 结果
     */
    @Override
    public int updateHospitalScheduling(HospitalScheduling hospitalScheduling)
    {
        hospitalScheduling.setUpdateTime(DateUtils.getNowDate());
        return hospitalSchedulingMapper.updateHospitalScheduling(hospitalScheduling);
    }

    /**
     * 批量删除医生排班
     * * @param scheduleIds 需要删除的医生排班主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSchedulingByScheduleIds(Long[] scheduleIds)
    {
        return hospitalSchedulingMapper.deleteHospitalSchedulingByScheduleIds(scheduleIds);
    }

    /**
     * 删除医生排班信息
     * * @param scheduleId 医生排班主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSchedulingByScheduleId(Long scheduleId)
    {
        return hospitalSchedulingMapper.deleteHospitalSchedulingByScheduleId(scheduleId);
    }
}