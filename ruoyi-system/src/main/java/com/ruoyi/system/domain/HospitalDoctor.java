package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医生信息对象 hospital_doctor
 * 
 * @author Shuhan
 * @date 2026-01-01
 */
public class HospitalDoctor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 医生ID */
    private Long doctorId;

    /** 所属科室ID */
    @Excel(name = "所属科室ID")
    private Long deptId;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String docName;

    /** 工号 */
    @Excel(name = "工号")
    private String docCode;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    /** 职称（主任医师/副主任医师/主治医师等） */
    @Excel(name = "职称", readConverterExp = "主=任医师/副主任医师/主治医师等")
    private String jobTitle;

    /** 擅长领域 */
    @Excel(name = "擅长领域")
    private String specialty;

    /** 挂号费用 */
    @Excel(name = "挂号费用")
    private BigDecimal consultationFee;

    /** 状态（0在职 1离职） */
    @Excel(name = "状态", readConverterExp = "0=在职,1=离职")
    private String status;

    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setDocName(String docName) 
    {
        this.docName = docName;
    }

    public String getDocName() 
    {
        return docName;
    }

    public void setDocCode(String docCode) 
    {
        this.docCode = docCode;
    }

    public String getDocCode() 
    {
        return docCode;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setJobTitle(String jobTitle) 
    {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() 
    {
        return jobTitle;
    }

    public void setSpecialty(String specialty) 
    {
        this.specialty = specialty;
    }

    public String getSpecialty() 
    {
        return specialty;
    }

    public void setConsultationFee(BigDecimal consultationFee) 
    {
        this.consultationFee = consultationFee;
    }

    public BigDecimal getConsultationFee() 
    {
        return consultationFee;
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
            .append("doctorId", getDoctorId())
            .append("deptId", getDeptId())
            .append("docName", getDocName())
            .append("docCode", getDocCode())
            .append("gender", getGender())
            .append("jobTitle", getJobTitle())
            .append("specialty", getSpecialty())
            .append("consultationFee", getConsultationFee())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
