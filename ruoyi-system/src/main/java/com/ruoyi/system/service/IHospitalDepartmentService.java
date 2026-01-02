package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.HospitalDepartment;

/**
 * 科室信息Service接口
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
public interface IHospitalDepartmentService 
{
    /**
     * 查询科室信息
     * 
     * @param deptId 科室信息主键
     * @return 科室信息
     */
    public HospitalDepartment selectHospitalDepartmentByDeptId(Long deptId);

    /**
     * 查询科室信息列表
     * 
     * @param hospitalDepartment 科室信息
     * @return 科室信息集合
     */
    public List<HospitalDepartment> selectHospitalDepartmentList(HospitalDepartment hospitalDepartment);

    /**
     * 新增科室信息
     * 
     * @param hospitalDepartment 科室信息
     * @return 结果
     */
    public int insertHospitalDepartment(HospitalDepartment hospitalDepartment);

    /**
     * 修改科室信息
     * 
     * @param hospitalDepartment 科室信息
     * @return 结果
     */
    public int updateHospitalDepartment(HospitalDepartment hospitalDepartment);

    /**
     * 批量删除科室信息
     * 
     * @param deptIds 需要删除的科室信息主键集合
     * @return 结果
     */
    public int deleteHospitalDepartmentByDeptIds(Long[] deptIds);

    /**
     * 删除科室信息信息
     * 
     * @param deptId 科室信息主键
     * @return 结果
     */
    public int deleteHospitalDepartmentByDeptId(Long deptId);
}
