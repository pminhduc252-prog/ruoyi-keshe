package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HospitalDepartmentMapper;
import com.ruoyi.system.domain.HospitalDepartment;
import com.ruoyi.system.service.IHospitalDepartmentService;

/**
 * 科室信息Service业务层处理
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
@Service
public class HospitalDepartmentServiceImpl implements IHospitalDepartmentService 
{
    @Autowired
    private HospitalDepartmentMapper hospitalDepartmentMapper;

    /**
     * 查询科室信息
     * 
     * @param deptId 科室信息主键
     * @return 科室信息
     */
    @Override
    public HospitalDepartment selectHospitalDepartmentByDeptId(Long deptId)
    {
        return hospitalDepartmentMapper.selectHospitalDepartmentByDeptId(deptId);
    }

    /**
     * 查询科室信息列表
     * 
     * @param hospitalDepartment 科室信息
     * @return 科室信息
     */
    @Override
    public List<HospitalDepartment> selectHospitalDepartmentList(HospitalDepartment hospitalDepartment)
    {
        return hospitalDepartmentMapper.selectHospitalDepartmentList(hospitalDepartment);
    }

    /**
     * 新增科室信息
     * 
     * @param hospitalDepartment 科室信息
     * @return 结果
     */
    @Override
    public int insertHospitalDepartment(HospitalDepartment hospitalDepartment)
    {
        hospitalDepartment.setCreateTime(DateUtils.getNowDate());
        return hospitalDepartmentMapper.insertHospitalDepartment(hospitalDepartment);
    }

    /**
     * 修改科室信息
     * 
     * @param hospitalDepartment 科室信息
     * @return 结果
     */
    @Override
    public int updateHospitalDepartment(HospitalDepartment hospitalDepartment)
    {
        hospitalDepartment.setUpdateTime(DateUtils.getNowDate());
        return hospitalDepartmentMapper.updateHospitalDepartment(hospitalDepartment);
    }

    /**
     * 批量删除科室信息
     * 
     * @param deptIds 需要删除的科室信息主键
     * @return 结果
     */
    @Override
    public int deleteHospitalDepartmentByDeptIds(Long[] deptIds)
    {
        return hospitalDepartmentMapper.deleteHospitalDepartmentByDeptIds(deptIds);
    }

    /**
     * 删除科室信息信息
     * 
     * @param deptId 科室信息主键
     * @return 结果
     */
    @Override
    public int deleteHospitalDepartmentByDeptId(Long deptId)
    {
        return hospitalDepartmentMapper.deleteHospitalDepartmentByDeptId(deptId);
    }
}
