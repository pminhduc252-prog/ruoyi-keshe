package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HospitalAppointmentMapper;
import com.ruoyi.system.domain.HospitalAppointment;
import com.ruoyi.system.service.IHospitalAppointmentService;

/**
 * 挂号预约记录Service业务层处理
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
@Service
public class HospitalAppointmentServiceImpl implements IHospitalAppointmentService 
{
    @Autowired
    private HospitalAppointmentMapper hospitalAppointmentMapper;

    /**
     * 查询挂号预约记录
     * 
     * @param appointmentId 挂号预约记录主键
     * @return 挂号预约记录
     */
    @Override
    public HospitalAppointment selectHospitalAppointmentByAppointmentId(Long appointmentId)
    {
        return hospitalAppointmentMapper.selectHospitalAppointmentByAppointmentId(appointmentId);
    }

    /**
     * 查询挂号预约记录列表
     * 
     * @param hospitalAppointment 挂号预约记录
     * @return 挂号预约记录
     */
    @Override
    public List<HospitalAppointment> selectHospitalAppointmentList(HospitalAppointment hospitalAppointment)
    {
        return hospitalAppointmentMapper.selectHospitalAppointmentList(hospitalAppointment);
    }

    /**
     * 新增挂号预约记录
     * 
     * @param hospitalAppointment 挂号预约记录
     * @return 结果
     */
    @Override
    public int insertHospitalAppointment(HospitalAppointment hospitalAppointment)
    {
        hospitalAppointment.setCreateTime(DateUtils.getNowDate());
        return hospitalAppointmentMapper.insertHospitalAppointment(hospitalAppointment);
    }

    /**
     * 修改挂号预约记录
     * 
     * @param hospitalAppointment 挂号预约记录
     * @return 结果
     */
    @Override
    public int updateHospitalAppointment(HospitalAppointment hospitalAppointment)
    {
        hospitalAppointment.setUpdateTime(DateUtils.getNowDate());
        return hospitalAppointmentMapper.updateHospitalAppointment(hospitalAppointment);
    }

    /**
     * 批量删除挂号预约记录
     * 
     * @param appointmentIds 需要删除的挂号预约记录主键
     * @return 结果
     */
    @Override
    public int deleteHospitalAppointmentByAppointmentIds(Long[] appointmentIds)
    {
        return hospitalAppointmentMapper.deleteHospitalAppointmentByAppointmentIds(appointmentIds);
    }

    /**
     * 删除挂号预约记录信息
     * 
     * @param appointmentId 挂号预约记录主键
     * @return 结果
     */
    @Override
    public int deleteHospitalAppointmentByAppointmentId(Long appointmentId)
    {
        return hospitalAppointmentMapper.deleteHospitalAppointmentByAppointmentId(appointmentId);
    }
}
