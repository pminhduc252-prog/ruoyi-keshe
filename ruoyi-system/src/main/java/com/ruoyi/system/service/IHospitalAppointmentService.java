package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.HospitalAppointment;

/**
 * 挂号预约记录Service接口
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
public interface IHospitalAppointmentService 
{
    /**
     * 查询挂号预约记录
     * 
     * @param appointmentId 挂号预约记录主键
     * @return 挂号预约记录
     */
    public HospitalAppointment selectHospitalAppointmentByAppointmentId(Long appointmentId);

    /**
     * 查询挂号预约记录列表
     * 
     * @param hospitalAppointment 挂号预约记录
     * @return 挂号预约记录集合
     */
    public List<HospitalAppointment> selectHospitalAppointmentList(HospitalAppointment hospitalAppointment);

    /**
     * 新增挂号预约记录
     * 
     * @param hospitalAppointment 挂号预约记录
     * @return 结果
     */
    public int insertHospitalAppointment(HospitalAppointment hospitalAppointment);

    /**
     * 修改挂号预约记录
     * 
     * @param hospitalAppointment 挂号预约记录
     * @return 结果
     */
    public int updateHospitalAppointment(HospitalAppointment hospitalAppointment);

    /**
     * 批量删除挂号预约记录
     * 
     * @param appointmentIds 需要删除的挂号预约记录主键集合
     * @return 结果
     */
    public int deleteHospitalAppointmentByAppointmentIds(Long[] appointmentIds);

    /**
     * 删除挂号预约记录信息
     * 
     * @param appointmentId 挂号预约记录主键
     * @return 结果
     */
    public int deleteHospitalAppointmentByAppointmentId(Long appointmentId);
}
