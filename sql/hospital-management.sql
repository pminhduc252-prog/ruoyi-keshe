-- ----------------------------
-- 1. 科室信息表
-- ----------------------------
DROP TABLE IF EXISTS `hospital_department`;
CREATE TABLE `hospital_department` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '科室ID',
  `dept_name` varchar(50) NOT NULL COMMENT '科室名称',
  `dept_code` varchar(20) DEFAULT NULL COMMENT '科室编号',
  `dept_intro` varchar(500) DEFAULT NULL COMMENT '科室简介',
  `dept_location` varchar(100) DEFAULT NULL COMMENT '科室位置',
  `leader` varchar(50) DEFAULT NULL COMMENT '科室负责人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='科室信息表';

-- ----------------------------
-- 2. 医生信息表
-- ----------------------------
DROP TABLE IF EXISTS `hospital_doctor`;
CREATE TABLE `hospital_doctor` (
  `doctor_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '医生ID',
  `dept_id` bigint(20) NOT NULL COMMENT '所属科室ID',
  `doc_name` varchar(50) NOT NULL COMMENT '医生姓名',
  `doc_code` varchar(20) NOT NULL COMMENT '工号',
  `gender` char(1) DEFAULT '0' COMMENT '性别（0男 1女 2未知）',
  `job_title` varchar(50) DEFAULT NULL COMMENT '职称（主任医师/副主任医师/主治医师等）',
  `specialty` varchar(500) DEFAULT NULL COMMENT '擅长领域',
  `consultation_fee` decimal(10,2) DEFAULT '0.00' COMMENT '挂号费用',
  `status` char(1) DEFAULT '0' COMMENT '状态（0在职 1离职）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='医生信息表';

-- ----------------------------
-- 3. 患者信息表 (敏感信息需代码层加密)
-- ----------------------------
DROP TABLE IF EXISTS `hospital_patient`;
CREATE TABLE `hospital_patient` (
  `patient_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '患者ID',
  `name` varchar(50) NOT NULL COMMENT '患者姓名',
  `gender` char(1) DEFAULT '0' COMMENT '性别（0男 1女 2未知）',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `id_card` varchar(255) NOT NULL COMMENT '身份证号(需加密存储)',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) DEFAULT NULL COMMENT '家庭住址',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1禁用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='患者信息表';

-- ----------------------------
-- 4. 医生排班表
-- ----------------------------
DROP TABLE IF EXISTS `hospital_scheduling`;
CREATE TABLE `hospital_scheduling` (
  `schedule_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '排班ID',
  `doctor_id` bigint(20) NOT NULL COMMENT '医生ID',
  `work_date` date NOT NULL COMMENT '出诊日期',
  `shift_type` char(1) NOT NULL COMMENT '时段（1上午 2下午 3晚班）',
  `total_quota` int(11) NOT NULL DEFAULT '0' COMMENT '总号源数量',
  `available_quota` int(11) NOT NULL DEFAULT '0' COMMENT '剩余号源数量',
  `status` char(1) DEFAULT '0' COMMENT '排班状态（0正常 1停诊）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`schedule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='医生排班表';

-- ----------------------------
-- 5. 挂号/预约记录表
-- ----------------------------
DROP TABLE IF EXISTS `hospital_appointment`;
CREATE TABLE `hospital_appointment` (
  `appointment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '挂号ID',
  `patient_id` bigint(20) NOT NULL COMMENT '患者ID',
  `doctor_id` bigint(20) NOT NULL COMMENT '医生ID',
  `schedule_id` bigint(20) NOT NULL COMMENT '排班ID',
  `visit_date` date NOT NULL COMMENT '就诊日期',
  `shift_type` char(1) DEFAULT NULL COMMENT '就诊时段',
  `queue_number` int(11) DEFAULT NULL COMMENT '排队序号(叫号用)',
  `status` char(1) DEFAULT '0' COMMENT '状态（0待就诊 1已就诊 2已取消 3已过期）',
  `visit_status` char(1) DEFAULT '0' COMMENT '叫号状态（0未叫号 1候诊中 2正在就诊 3完成）',
  `reg_fee` decimal(10,2) DEFAULT NULL COMMENT '挂号费用',
  `symptom_desc` varchar(500) DEFAULT NULL COMMENT '就诊需求/症状描述',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`appointment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='挂号预约记录表';