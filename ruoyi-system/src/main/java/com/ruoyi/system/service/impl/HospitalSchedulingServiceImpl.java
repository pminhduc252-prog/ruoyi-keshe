package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HospitalSchedulingMapper;
import com.ruoyi.system.domain.HospitalScheduling;
import com.ruoyi.system.service.IHospitalSchedulingService;

/**
 * 医生排班Service业务层处理
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
@Service
public class HospitalSchedulingServiceImpl implements IHospitalSchedulingService 
{
    @Autowired
    private HospitalSchedulingMapper hospitalSchedulingMapper;

    /**
     * 查询医生排班
     * 
     * @param scheduleId 医生排班主键
     * @return 医生排班
     */
    @Override
    public HospitalScheduling selectHospitalSchedulingByScheduleId(Long scheduleId)
    {
        return hospitalSchedulingMapper.selectHospitalSchedulingByScheduleId(scheduleId);
    }

    /**
     * 查询医生排班列表
     * 
     * @param hospitalScheduling 医生排班
     * @return 医生排班
     */
    @Override
    public List<HospitalScheduling> selectHospitalSchedulingList(HospitalScheduling hospitalScheduling)
    {
        return hospitalSchedulingMapper.selectHospitalSchedulingList(hospitalScheduling);
    }

    /**
     * 新增医生排班
     * 
     * @param hospitalScheduling 医生排班
     * @return 结果
     */
    @Override
    public int insertHospitalScheduling(HospitalScheduling hospitalScheduling)
    {
        hospitalScheduling.setCreateTime(DateUtils.getNowDate());
        return hospitalSchedulingMapper.insertHospitalScheduling(hospitalScheduling);
    }

    /**
     * 修改医生排班
     * 
     * @param hospitalScheduling 医生排班
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
     * 
     * @param scheduleIds 需要删除的医生排班主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSchedulingByScheduleIds(Long[] scheduleIds)
    {
        return hospitalSchedulingMapper.deleteHospitalSchedulingByScheduleIds(scheduleIds);
    }

    /**
     * 删除医生排班信息
     * 
     * @param scheduleId 医生排班主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSchedulingByScheduleId(Long scheduleId)
    {
        return hospitalSchedulingMapper.deleteHospitalSchedulingByScheduleId(scheduleId);
    }
}
