package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.HospitalScheduling;

/**
 * 医生排班Mapper接口
 * 
 * @author Shuhan
 * @date 2026-01-02
 */
public interface HospitalSchedulingMapper 
{
    /**
     * 查询医生排班
     * 
     * @param scheduleId 医生排班主键
     * @return 医生排班
     */
    public HospitalScheduling selectHospitalSchedulingByScheduleId(Long scheduleId);

    /**
     * 查询医生排班列表
     * 
     * @param hospitalScheduling 医生排班
     * @return 医生排班集合
     */
    public List<HospitalScheduling> selectHospitalSchedulingList(HospitalScheduling hospitalScheduling);

    /**
     * 新增医生排班
     * 
     * @param hospitalScheduling 医生排班
     * @return 结果
     */
    public int insertHospitalScheduling(HospitalScheduling hospitalScheduling);

    /**
     * 修改医生排班
     * 
     * @param hospitalScheduling 医生排班
     * @return 结果
     */
    public int updateHospitalScheduling(HospitalScheduling hospitalScheduling);

    /**
     * 删除医生排班
     * 
     * @param scheduleId 医生排班主键
     * @return 结果
     */
    public int deleteHospitalSchedulingByScheduleId(Long scheduleId);

    /**
     * 批量删除医生排班
     * 
     * @param scheduleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalSchedulingByScheduleIds(Long[] scheduleIds);
}
