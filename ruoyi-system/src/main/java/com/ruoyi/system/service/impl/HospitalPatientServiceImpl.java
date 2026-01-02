package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.sign.AesUtils; // 【重点1】引入刚才写的工具类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HospitalPatientMapper;
import com.ruoyi.system.domain.HospitalPatient;
import com.ruoyi.system.service.IHospitalPatientService;

/**
 * 患者信息Service业务层处理
 * * @author Shuhan
 * @date 2025-12-31
 */
@Service
public class HospitalPatientServiceImpl implements IHospitalPatientService 
{
    @Autowired
    private HospitalPatientMapper hospitalPatientMapper;

    /**
     * 查询患者信息 (详情页)
     * * @param patientId 患者信息主键
     * @return 患者信息
     */
    @Override
    public HospitalPatient selectHospitalPatientByPatientId(Long patientId)
    {
        HospitalPatient patient = hospitalPatientMapper.selectHospitalPatientByPatientId(patientId);
        
        // 【重点2】查询详情时：解密身份证，让管理员/用户看到真实的号码
        if (patient != null && patient.getIdCard() != null) {
            String realIdCard = AesUtils.decrypt(patient.getIdCard());
            patient.setIdCard(realIdCard);
        }
        
        return patient;
    }

    /**
     * 查询患者信息列表 (列表页)
     * * @param hospitalPatient 患者信息
     * @return 患者信息
     */
    @Override
    public List<HospitalPatient> selectHospitalPatientList(HospitalPatient hospitalPatient)
    {
        // 这里的逻辑有点特殊：因为数据库里存的是密文，如果用户在搜索框按“真实身份证”搜索，
        // 我们需要把用户输入的“真实身份证”加密成“密文”再去数据库匹配。
        if (hospitalPatient.getIdCard() != null && !"".equals(hospitalPatient.getIdCard())) {
            String encryptIdCard = AesUtils.encrypt(hospitalPatient.getIdCard());
            hospitalPatient.setIdCard(encryptIdCard);
        }

        List<HospitalPatient> list = hospitalPatientMapper.selectHospitalPatientList(hospitalPatient);

        // 【重点3】查询列表时：循环解密，让列表也能显示明文（或者你也可以选择不解密，显示密文保护隐私）
        for (HospitalPatient p : list) {
            if (p.getIdCard() != null) {
                p.setIdCard(AesUtils.decrypt(p.getIdCard()));
            }
        }
        
        return list;
    }

    /**
     * 新增患者信息
     * * @param hospitalPatient 患者信息
     * @return 结果
     */
    @Override
    public int insertHospitalPatient(HospitalPatient hospitalPatient)
    {
        // 【重点4】新增前：将身份证号加密存储
        if (hospitalPatient.getIdCard() != null) {
            String encryptIdCard = AesUtils.encrypt(hospitalPatient.getIdCard());
            hospitalPatient.setIdCard(encryptIdCard);
        }
        
        hospitalPatient.setCreateTime(DateUtils.getNowDate());
        return hospitalPatientMapper.insertHospitalPatient(hospitalPatient);
    }

    /**
     * 修改患者信息
     * * @param hospitalPatient 患者信息
     * @return 结果
     */
    @Override
    public int updateHospitalPatient(HospitalPatient hospitalPatient)
    {
        // 【重点5】修改前：前端传过来的是解密后的明文，存入数据库前需再次加密
        if (hospitalPatient.getIdCard() != null) {
            String encryptIdCard = AesUtils.encrypt(hospitalPatient.getIdCard());
            hospitalPatient.setIdCard(encryptIdCard);
        }

        hospitalPatient.setUpdateTime(DateUtils.getNowDate());
        return hospitalPatientMapper.updateHospitalPatient(hospitalPatient);
    }

    /**
     * 批量删除患者信息
     * * @param patientIds 需要删除的患者信息主键
     * @return 结果
     */
    @Override
    public int deleteHospitalPatientByPatientIds(Long[] patientIds)
    {
        return hospitalPatientMapper.deleteHospitalPatientByPatientIds(patientIds);
    }

    /**
     * 删除患者信息信息
     * * @param patientId 患者信息主键
     * @return 结果
     */
    @Override
    public int deleteHospitalPatientByPatientId(Long patientId)
    {
        return hospitalPatientMapper.deleteHospitalPatientByPatientId(patientId);
    }
}