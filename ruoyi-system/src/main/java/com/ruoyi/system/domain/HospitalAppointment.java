package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 挂号预约记录对象 hospital_appointment
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
public class HospitalAppointment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 挂号ID */
    private Long appointmentId;

    /** 患者ID */
    @Excel(name = "患者ID")
    private Long patientId;

    /** 医生ID */
    @Excel(name = "医生ID")
    private Long doctorId;

    /** 排班ID */
    @Excel(name = "排班ID")
    private Long scheduleId;

    /** 就诊日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "就诊日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitDate;

    /** 就诊时段 */
    @Excel(name = "就诊时段")
    private String shiftType;

    /** 排队序号(叫号用) */
    @Excel(name = "排队序号(叫号用)")
    private Long queueNumber;

    /** 状态（0待就诊 1已就诊 2已取消 3已过期） */
    @Excel(name = "状态", readConverterExp = "0=待就诊,1=已就诊,2=已取消,3=已过期")
    private String status;

    /** 叫号状态（0未叫号 1候诊中 2正在就诊 3完成） */
    @Excel(name = "叫号状态", readConverterExp = "0=未叫号,1=候诊中,2=正在就诊,3=完成")
    private String visitStatus;

    /** 挂号费用 */
    @Excel(name = "挂号费用")
    private BigDecimal regFee;

    /** 就诊需求/症状描述 */
    @Excel(name = "就诊需求/症状描述")
    private String symptomDesc;

    public void setAppointmentId(Long appointmentId) 
    {
        this.appointmentId = appointmentId;
    }

    public Long getAppointmentId() 
    {
        return appointmentId;
    }

    public void setPatientId(Long patientId) 
    {
        this.patientId = patientId;
    }

    public Long getPatientId() 
    {
        return patientId;
    }

    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }

    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }

    public void setVisitDate(Date visitDate) 
    {
        this.visitDate = visitDate;
    }

    public Date getVisitDate() 
    {
        return visitDate;
    }

    public void setShiftType(String shiftType) 
    {
        this.shiftType = shiftType;
    }

    public String getShiftType() 
    {
        return shiftType;
    }

    public void setQueueNumber(Long queueNumber) 
    {
        this.queueNumber = queueNumber;
    }

    public Long getQueueNumber() 
    {
        return queueNumber;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setVisitStatus(String visitStatus) 
    {
        this.visitStatus = visitStatus;
    }

    public String getVisitStatus() 
    {
        return visitStatus;
    }

    public void setRegFee(BigDecimal regFee) 
    {
        this.regFee = regFee;
    }

    public BigDecimal getRegFee() 
    {
        return regFee;
    }

    public void setSymptomDesc(String symptomDesc) 
    {
        this.symptomDesc = symptomDesc;
    }

    public String getSymptomDesc() 
    {
        return symptomDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("appointmentId", getAppointmentId())
            .append("patientId", getPatientId())
            .append("doctorId", getDoctorId())
            .append("scheduleId", getScheduleId())
            .append("visitDate", getVisitDate())
            .append("shiftType", getShiftType())
            .append("queueNumber", getQueueNumber())
            .append("status", getStatus())
            .append("visitStatus", getVisitStatus())
            .append("regFee", getRegFee())
            .append("symptomDesc", getSymptomDesc())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
