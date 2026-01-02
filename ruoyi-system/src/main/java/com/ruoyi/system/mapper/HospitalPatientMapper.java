package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.HospitalPatient;

/**
 * 患者信息Mapper接口
 * 
 * @author Shuhan
 * @date 2025-12-31
 */
public interface HospitalPatientMapper 
{
    /**
     * 查询患者信息
     * 
     * @param patientId 患者信息主键
     * @return 患者信息
     */
    public HospitalPatient selectHospitalPatientByPatientId(Long patientId);

    /**
     * 查询患者信息列表
     * 
     * @param hospitalPatient 患者信息
     * @return 患者信息集合
     */
    public List<HospitalPatient> selectHospitalPatientList(HospitalPatient hospitalPatient);

    /**
     * 新增患者信息
     * 
     * @param hospitalPatient 患者信息
     * @return 结果
     */
    public int insertHospitalPatient(HospitalPatient hospitalPatient);

    /**
     * 修改患者信息
     * 
     * @param hospitalPatient 患者信息
     * @return 结果
     */
    public int updateHospitalPatient(HospitalPatient hospitalPatient);

    /**
     * 删除患者信息
     * 
     * @param patientId 患者信息主键
     * @return 结果
     */
    public int deleteHospitalPatientByPatientId(Long patientId);

    /**
     * 批量删除患者信息
     * 
     * @param patientIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalPatientByPatientIds(Long[] patientIds);
}
