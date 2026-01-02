package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.HospitalDoctor;

/**
 * 医生信息Mapper接口
 * 
 * @author Shuhan
 * @date 2026-01-01
 */
public interface HospitalDoctorMapper 
{
    /**
     * 查询医生信息
     * 
     * @param doctorId 医生信息主键
     * @return 医生信息
     */
    public HospitalDoctor selectHospitalDoctorByDoctorId(Long doctorId);

    /**
     * 查询医生信息列表
     * 
     * @param hospitalDoctor 医生信息
     * @return 医生信息集合
     */
    public List<HospitalDoctor> selectHospitalDoctorList(HospitalDoctor hospitalDoctor);

    /**
     * 新增医生信息
     * 
     * @param hospitalDoctor 医生信息
     * @return 结果
     */
    public int insertHospitalDoctor(HospitalDoctor hospitalDoctor);

    /**
     * 修改医生信息
     * 
     * @param hospitalDoctor 医生信息
     * @return 结果
     */
    public int updateHospitalDoctor(HospitalDoctor hospitalDoctor);

    /**
     * 删除医生信息
     * 
     * @param doctorId 医生信息主键
     * @return 结果
     */
    public int deleteHospitalDoctorByDoctorId(Long doctorId);

    /**
     * 批量删除医生信息
     * 
     * @param doctorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalDoctorByDoctorIds(Long[] doctorIds);
}
