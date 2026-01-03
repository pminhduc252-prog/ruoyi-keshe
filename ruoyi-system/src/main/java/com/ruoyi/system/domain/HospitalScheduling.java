package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医生排班对象 hospital_scheduling
 * * @author Shuhan
 * @date 2026-01-02
 */
public class HospitalScheduling extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 排班ID */
    private Long scheduleId;

    /** 医生ID */
    @Excel(name = "医生ID")
    private Long doctorId;

    /** 医生姓名 (新增字段，用于展示) */
    @Excel(name = "医生姓名")
    private String doctorName;

    /** 出诊日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出诊日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workDate;

    /** 时段（1上午 2下午 3晚班） */
    @Excel(name = "时段", readConverterExp = "1=上午,2=下午,3=晚班")
    private String shiftType;

    /** 总号源数量 */
    @Excel(name = "总号源数量")
    private Long totalQuota;

    /** 剩余号源数量 */
    @Excel(name = "剩余号源数量")
    private Long availableQuota;

    /** 排班状态（0正常 1停诊） */
    @Excel(name = "排班状态", readConverterExp = "0=正常,1=停诊")
    private String status;

    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }

    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }

    public void setDoctorName(String doctorName) 
    {
        this.doctorName = doctorName;
    }

    public String getDoctorName() 
    {
        return doctorName;
    }

    public void setWorkDate(Date workDate) 
    {
        this.workDate = workDate;
    }

    public Date getWorkDate() 
    {
        return workDate;
    }

    public void setShiftType(String shiftType) 
    {
        this.shiftType = shiftType;
    }

    public String getShiftType() 
    {
        return shiftType;
    }

    public void setTotalQuota(Long totalQuota) 
    {
        this.totalQuota = totalQuota;
    }

    public Long getTotalQuota() 
    {
        return totalQuota;
    }

    public void setAvailableQuota(Long availableQuota) 
    {
        this.availableQuota = availableQuota;
    }

    public Long getAvailableQuota() 
    {
        return availableQuota;
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
            .append("scheduleId", getScheduleId())
            .append("doctorId", getDoctorId())
            .append("doctorName", getDoctorName())
            .append("workDate", getWorkDate())
            .append("shiftType", getShiftType())
            .append("totalQuota", getTotalQuota())
            .append("availableQuota", getAvailableQuota())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}