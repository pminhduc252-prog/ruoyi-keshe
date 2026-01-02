package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HospitalDoctorMapper;
import com.ruoyi.system.domain.HospitalDoctor;
import com.ruoyi.system.service.IHospitalDoctorService;

/**
 * 医生信息Service业务层处理
 * 
 * @author Shuhan
 * @date 2026-01-01
 */
@Service
public class HospitalDoctorServiceImpl implements IHospitalDoctorService 
{
    @Autowired
    private HospitalDoctorMapper hospitalDoctorMapper;

    /**
     * 查询医生信息
     * 
     * @param doctorId 医生信息主键
     * @return 医生信息
     */
    @Override
    public HospitalDoctor selectHospitalDoctorByDoctorId(Long doctorId)
    {
        return hospitalDoctorMapper.selectHospitalDoctorByDoctorId(doctorId);
    }

    /**
     * 查询医生信息列表
     * 
     * @param hospitalDoctor 医生信息
     * @return 医生信息
     */
    @Override
    public List<HospitalDoctor> selectHospitalDoctorList(HospitalDoctor hospitalDoctor)
    {
        return hospitalDoctorMapper.selectHospitalDoctorList(hospitalDoctor);
    }

    /**
     * 新增医生信息
     * 
     * @param hospitalDoctor 医生信息
     * @return 结果
     */
    @Override
    public int insertHospitalDoctor(HospitalDoctor hospitalDoctor)
    {
        hospitalDoctor.setCreateTime(DateUtils.getNowDate());
        return hospitalDoctorMapper.insertHospitalDoctor(hospitalDoctor);
    }

    /**
     * 修改医生信息
     * 
     * @param hospitalDoctor 医生信息
     * @return 结果
     */
    @Override
    public int updateHospitalDoctor(HospitalDoctor hospitalDoctor)
    {
        hospitalDoctor.setUpdateTime(DateUtils.getNowDate());
        return hospitalDoctorMapper.updateHospitalDoctor(hospitalDoctor);
    }

    /**
     * 批量删除医生信息
     * 
     * @param doctorIds 需要删除的医生信息主键
     * @return 结果
     */
    @Override
    public int deleteHospitalDoctorByDoctorIds(Long[] doctorIds)
    {
        return hospitalDoctorMapper.deleteHospitalDoctorByDoctorIds(doctorIds);
    }

    /**
     * 删除医生信息信息
     * 
     * @param doctorId 医生信息主键
     * @return 结果
     */
    @Override
    public int deleteHospitalDoctorByDoctorId(Long doctorId)
    {
        return hospitalDoctorMapper.deleteHospitalDoctorByDoctorId(doctorId);
    }
}
