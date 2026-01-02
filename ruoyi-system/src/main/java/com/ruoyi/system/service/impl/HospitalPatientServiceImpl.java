package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HospitalPatientMapper;
import com.ruoyi.system.domain.HospitalPatient;
import com.ruoyi.system.service.IHospitalPatientService;

/**
 * 患者信息Service业务层处理
 * 
 * @author Shuhan
 * @date 2025-12-31
 */
@Service
public class HospitalPatientServiceImpl implements IHospitalPatientService 
{
    @Autowired
    private HospitalPatientMapper hospitalPatientMapper;

    /**
     * 查询患者信息
     * 
     * @param patientId 患者信息主键
     * @return 患者信息
     */
    @Override
    public HospitalPatient selectHospitalPatientByPatientId(Long patientId)
    {
        return hospitalPatientMapper.selectHospitalPatientByPatientId(patientId);
    }

    /**
     * 查询患者信息列表
     * 
     * @param hospitalPatient 患者信息
     * @return 患者信息
     */
    @Override
    public List<HospitalPatient> selectHospitalPatientList(HospitalPatient hospitalPatient)
    {
        return hospitalPatientMapper.selectHospitalPatientList(hospitalPatient);
    }

    /**
     * 新增患者信息
     * 
     * @param hospitalPatient 患者信息
     * @return 结果
     */
    @Override
    public int insertHospitalPatient(HospitalPatient hospitalPatient)
    {
        hospitalPatient.setCreateTime(DateUtils.getNowDate());
        return hospitalPatientMapper.insertHospitalPatient(hospitalPatient);
    }

    /**
     * 修改患者信息
     * 
     * @param hospitalPatient 患者信息
     * @return 结果
     */
    @Override
    public int updateHospitalPatient(HospitalPatient hospitalPatient)
    {
        hospitalPatient.setUpdateTime(DateUtils.getNowDate());
        return hospitalPatientMapper.updateHospitalPatient(hospitalPatient);
    }

    /**
     * 批量删除患者信息
     * 
     * @param patientIds 需要删除的患者信息主键
     * @return 结果
     */
    @Override
    public int deleteHospitalPatientByPatientIds(Long[] patientIds)
    {
        return hospitalPatientMapper.deleteHospitalPatientByPatientIds(patientIds);
    }

    /**
     * 删除患者信息信息
     * 
     * @param patientId 患者信息主键
     * @return 结果
     */
    @Override
    public int deleteHospitalPatientByPatientId(Long patientId)
    {
        return hospitalPatientMapper.deleteHospitalPatientByPatientId(patientId);
    }
}
