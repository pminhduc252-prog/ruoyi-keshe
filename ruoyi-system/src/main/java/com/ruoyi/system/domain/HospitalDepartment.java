package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 科室信息对象 hospital_department
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
public class HospitalDepartment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 科室ID */
    private Long deptId;

    /** 科室名称 */
    @Excel(name = "科室名称")
    private String deptName;

    /** 科室编号 */
    @Excel(name = "科室编号")
    private String deptCode;

    /** 科室简介 */
    @Excel(name = "科室简介")
    private String deptIntro;

    /** 科室位置 */
    @Excel(name = "科室位置")
    private String deptLocation;

    /** 科室负责人 */
    @Excel(name = "科室负责人")
    private String leader;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }

    public void setDeptCode(String deptCode) 
    {
        this.deptCode = deptCode;
    }

    public String getDeptCode() 
    {
        return deptCode;
    }

    public void setDeptIntro(String deptIntro) 
    {
        this.deptIntro = deptIntro;
    }

    public String getDeptIntro() 
    {
        return deptIntro;
    }

    public void setDeptLocation(String deptLocation) 
    {
        this.deptLocation = deptLocation;
    }

    public String getDeptLocation() 
    {
        return deptLocation;
    }

    public void setLeader(String leader) 
    {
        this.leader = leader;
    }

    public String getLeader() 
    {
        return leader;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("deptCode", getDeptCode())
            .append("deptIntro", getDeptIntro())
            .append("deptLocation", getDeptLocation())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
