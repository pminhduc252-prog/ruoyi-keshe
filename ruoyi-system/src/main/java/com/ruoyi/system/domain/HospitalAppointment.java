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
 * * @author Shuhan
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

    /** 排队号 */
    @Excel(name = "排队号")
    private Long queueNumber;

    /** 状态（0已预约 1已取消 2已完成 3已爽约） */
    @Excel(name = "状态", readConverterExp = "0=已预约,1=已取消,2=已完成,3=已爽约")
    private String status;

    /** 就诊状态（0未就诊 1就诊中 2已就诊） */
    @Excel(name = "就诊状态", readConverterExp = "0=未就诊,1=就诊中,2=已就诊")
    private String visitStatus;

    /** 挂号费 */
    @Excel(name = "挂号费")
    private BigDecimal regFee;

    /** 病情描述 */
    @Excel(name = "病情描述")
    private String symptomDesc;

    // ==========================================
    //  新增辅助字段 (用于列表展示姓名，而非ID)
    // ==========================================
    
    /** 患者姓名 (关联查询用) */
    @Excel(name = "患者姓名")
    private String patientName;

    /** 医生姓名 (关联查询用) */
    @Excel(name = "医生姓名")
    private String doctorName;

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    // ==========================================
    //  原有 Getter / Setter
    // ==========================================

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
            .append("patientName", getPatientName()) // 打印患者姓名
            .append("doctorId", getDoctorId())
            .append("doctorName", getDoctorName())   // 打印医生姓名
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