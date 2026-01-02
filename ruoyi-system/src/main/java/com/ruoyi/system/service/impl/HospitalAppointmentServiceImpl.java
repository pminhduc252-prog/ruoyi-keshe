package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils; // 【新增】引入权限工具类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HospitalAppointmentMapper;
import com.ruoyi.system.domain.HospitalAppointment;
import com.ruoyi.system.service.IHospitalAppointmentService;

/**
 * 挂号预约记录Service业务层处理
 * * @author Shuhan
 * @date 2026-01-02
 */
@Service
public class HospitalAppointmentServiceImpl implements IHospitalAppointmentService 
{
    @Autowired
    private HospitalAppointmentMapper hospitalAppointmentMapper;

    /**
     * 查询挂号预约记录
     * * @param appointmentId 挂号预约记录主键
     * @return 挂号预约记录
     */
    @Override
    public HospitalAppointment selectHospitalAppointmentByAppointmentId(Long appointmentId)
    {
        return hospitalAppointmentMapper.selectHospitalAppointmentByAppointmentId(appointmentId);
    }

    /**
     * 查询挂号预约记录列表
     * * @param hospitalAppointment 挂号预约记录
     * @return 挂号预约记录
     */
    @Override
    public List<HospitalAppointment> selectHospitalAppointmentList(HospitalAppointment hospitalAppointment)
    {
        // 【修改】核心逻辑：实现“患者只能看自己的挂号单”
        
        // 1. 获取当前登录用户的ID
        try {
            Long userId = SecurityUtils.getUserId();
            
            // 2. 判断是否为管理员（ID=1通常是超级管理员）
            // 如果不是管理员，说明是患者登录
            if (!SecurityUtils.isAdmin(userId)) {
                // 3. 强制设置过滤条件：create_by（创建者）必须等于当前登录账号
                // 这样 MyBatis 在查询时就会生成 WHERE create_by = 'zhangsan'
                hospitalAppointment.setCreateBy(SecurityUtils.getUsername());
            }
        } catch (Exception e) {
            // 防止在非登录环境下（如定时任务）调用报错，一般可忽略或记录日志
        }

        return hospitalAppointmentMapper.selectHospitalAppointmentList(hospitalAppointment);
    }

    /**
     * 新增挂号预约记录
     * * @param hospitalAppointment 挂号预约记录
     * @return 结果
     */
    @Override
    public int insertHospitalAppointment(HospitalAppointment hospitalAppointment)
    {
        hospitalAppointment.setCreateTime(DateUtils.getNowDate());
        // 若依的 BaseEntity 默认会在 Controller 层自动设置 createBy，
        // 但为了保险起见，或者如果是内部调用，这里可以不用动，依靠 AOP 或者 Controller 处理即可。
        return hospitalAppointmentMapper.insertHospitalAppointment(hospitalAppointment);
    }

    /**
     * 修改挂号预约记录
     * * @param hospitalAppointment 挂号预约记录
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
     * * @param appointmentIds 需要删除的挂号预约记录主键
     * @return 结果
     */
    @Override
    public int deleteHospitalAppointmentByAppointmentIds(Long[] appointmentIds)
    {
        return hospitalAppointmentMapper.deleteHospitalAppointmentByAppointmentIds(appointmentIds);
    }

    /**
     * 删除挂号预约记录信息
     * * @param appointmentId 挂号预约记录主键
     * @return 结果
     */
    @Override
    public int deleteHospitalAppointmentByAppointmentId(Long appointmentId)
    {
        return hospitalAppointmentMapper.deleteHospitalAppointmentByAppointmentId(appointmentId);
    }
}