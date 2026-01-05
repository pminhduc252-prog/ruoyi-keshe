<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="医生姓名" prop="doctorName">
        <el-input
          v-model="queryParams.doctorName"
          placeholder="请输入医生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室名称" prop="doctorDeptName">
        <el-input
          v-model="queryParams.doctorDeptName"
          placeholder="请输入科室名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医生职称" prop="doctorJobTitle">
        <el-input
          v-model="queryParams.doctorJobTitle"
          placeholder="请输入职称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      
      <el-form-item label="出诊日期" prop="workDate">
        <el-date-picker clearable
          v-model="queryParams.workDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择出诊日期">
        </el-date-picker>
      </el-form-item>
      
      <el-form-item label="时段" prop="shiftType">
        <el-select v-model="queryParams.shiftType" placeholder="请选择时段" clearable>
          <el-option label="上午" value="1" />
          <el-option label="下午" value="2" />
          <el-option label="晚班" value="3" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:schedulingKeshe:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:schedulingKeshe:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:schedulingKeshe:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:schedulingKeshe:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="schedulingKesheList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="排班ID" align="center" prop="scheduleId" width="80" />
      
      <el-table-column label="科室" align="center" prop="doctorDeptName" width="120" />
      
      <el-table-column label="医生" align="center" prop="doctorName">
         <template slot-scope="scope">
            <span style="font-weight: bold;">{{ scope.row.doctorName }}</span>
         </template>
      </el-table-column>
      
      <el-table-column label="职称" align="center" prop="doctorJobTitle" width="120">
         <template slot-scope="scope">
            <el-tag size="small" type="info">{{ scope.row.doctorJobTitle }}</el-tag>
         </template>
      </el-table-column>

      <el-table-column label="出诊日期" align="center" prop="workDate" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.workDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="时段" align="center" prop="shiftType" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.shiftType == '1'" effect="dark">上午</el-tag>
          <el-tag v-else-if="scope.row.shiftType == '2'" type="warning" effect="dark">下午</el-tag>
          <el-tag v-else-if="scope.row.shiftType == '3'" type="danger" effect="dark">晚班</el-tag>
          <span v-else>{{ scope.row.shiftType }}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="号源" align="center">
          <template slot-scope="scope">
              <span :style="scope.row.availableQuota > 0 ? 'color:green' : 'color:red'">
                {{ scope.row.availableQuota }}
              </span> 
              / {{ scope.row.totalQuota }}
          </template>
      </el-table-column>
      
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停诊</el-tag>
        </template>
      </el-table-column>
      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:schedulingKeshe:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:schedulingKeshe:remove']"
          >删除</el-button>
          
          <el-button
            v-if="scope.row.availableQuota > 0"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleBook(scope.row)"
            v-hasRole="['patient']"
          >预约</el-button>
           <el-button
            v-else
            disabled
            size="mini"
            type="text"
            v-hasRole="['patient']"
          >已满</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="医生ID" prop="doctorId">
          <el-input v-model="form.doctorId" placeholder="请输入医生ID" />
        </el-form-item>
        <el-form-item label="出诊日期" prop="workDate">
          <el-date-picker clearable
            v-model="form.workDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出诊日期">
          </el-date-picker>
        </el-form-item>
        
        <el-form-item label="时段" prop="shiftType">
          <el-select v-model="form.shiftType" placeholder="请选择时段">
            <el-option label="上午" value="1" />
            <el-option label="下午" value="2" />
            <el-option label="晚班" value="3" />
          </el-select>
        </el-form-item>

        <el-form-item label="总号源数量" prop="totalQuota">
          <el-input v-model="form.totalQuota" placeholder="请输入总号源数量" />
        </el-form-item>
        <el-form-item label="剩余号源数量" prop="availableQuota">
          <el-input v-model="form.availableQuota" placeholder="请输入剩余号源数量" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="完善就诊信息" :visible.sync="patientOpen" width="500px" append-to-body :close-on-click-modal="false">
      <div style="margin-bottom: 20px; color: #E6A23C;">
        <i class="el-icon-warning"></i> 初次预约请先填写真实就诊信息，提交后将自动为您预约。
      </div>
      <el-form ref="patientForm" :model="patientForm" :rules="patientRules" label-width="80px">
        <el-form-item label="真实姓名" prop="name">
          <el-input v-model="patientForm.name" placeholder="请输入您的真实姓名" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="patientForm.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="patientForm.phone" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="patientForm.gender" placeholder="请选择性别">
            <el-option label="男" value="0" />
            <el-option label="女" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="patientForm.age" :min="0" :max="120" />
        </el-form-item>
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="patientForm.address" placeholder="请输入家庭住址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPatientForm">保存并继续预约</el-button>
        <el-button @click="patientOpen = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listSchedulingKeshe, getSchedulingKeshe, delSchedulingKeshe, addSchedulingKeshe, updateSchedulingKeshe } from "@/api/system/schedulingKeshe"
import { bookAppointment } from "@/api/system/appointmentKeshe"
import { addPatientProfile } from "@/api/system/patientKeshe" // 【修复】使用 addPatientProfile
import { mapGetters } from 'vuex' 

export default {
  name: "SchedulingKeshe",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 医生排班表格数据
      schedulingKesheList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      
      // 【新增】患者完善信息弹窗控制
      patientOpen: false,
      // 【新增】暂存当前要预约的排班信息
      pendingBookingRow: null,
      pendingBookingRemark: null,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        doctorId: null,
        doctorName: null,
        doctorDeptName: null,
        doctorJobTitle: null,
        workDate: null,
        shiftType: null,
        totalQuota: null,
        availableQuota: null,
        status: null,
      },
      // 表单参数 (排班)
      form: {},
      
      // 【新增】表单参数 (患者)
      patientForm: {},
      
      // 表单校验 (排班)
      rules: {
        doctorId: [
          { required: true, message: "医生ID不能为空", trigger: "blur" }
        ],
        workDate: [
          { required: true, message: "出诊日期不能为空", trigger: "blur" }
        ],
        shiftType: [
          { required: true, message: "时段不能为空", trigger: "change" }
        ],
        totalQuota: [
          { required: true, message: "总号源数量不能为空", trigger: "blur" }
        ],
        availableQuota: [
          { required: true, message: "剩余号源数量不能为空", trigger: "blur" }
        ],
      },
      // 【新增】表单校验 (患者)
      patientRules: {
        name: [{ required: true, message: "真实姓名不能为空", trigger: "blur" }],
        idCard: [{ required: true, message: "身份证号不能为空", trigger: "blur" }],
        phone: [{ required: true, message: "手机号码不能为空", trigger: "blur" }]
      }
    }
  },
  computed: {
    ...mapGetters(['userId'])
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询医生排班列表 */
    getList() {
      this.loading = true
      listSchedulingKeshe(this.queryParams).then(response => {
        this.schedulingKesheList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        scheduleId: null,
        doctorId: null,
        workDate: null,
        shiftType: null,
        totalQuota: null,
        availableQuota: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    // 【新增】重置患者表单
    resetPatientForm() {
      this.patientForm = {
        name: null,
        idCard: null,
        phone: null,
        gender: "0",
        age: null,
        address: null,
        userId: null
      }
      this.resetForm("patientForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.scheduleId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加医生排班"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const scheduleId = row.scheduleId || this.ids
      getSchedulingKeshe(scheduleId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改医生排班"
      })
    },
    /** 提交按钮 (排班) */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.scheduleId != null) {
            updateSchedulingKeshe(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSchedulingKeshe(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const scheduleIds = row.scheduleId || this.ids
      this.$modal.confirm('是否确认删除医生排班编号为"' + scheduleIds + '"的数据项？').then(function() {
        return delSchedulingKeshe(scheduleIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/schedulingKeshe/export', {
        ...this.queryParams
      }, `schedulingKeshe_${new Date().getTime()}.xlsx`)
    },
    
    /** 患者点击预约 */
    handleBook(row) {
      // 1. 输入病情
      this.$prompt('请输入您的就诊需求或简要病情：', '预约确认', {
        confirmButtonText: '确认预约',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '病情描述不能为空'
      }).then(({ value }) => {
        
        // 暂存数据，以便后续自动重试
        this.pendingBookingRow = row;
        this.pendingBookingRemark = value;
        
        // 执行预约
        this.doBookRequest(row.scheduleId, value);
        
      }).catch(() => {});
    },

    /** 执行真实的预约请求 */
    doBookRequest(scheduleId, remark) {
        let data = { 
          scheduleId: scheduleId, 
          remark: remark 
        }; 
        bookAppointment(data).then(response => {
            this.$modal.msgSuccess("预约成功！请在'我的挂号记录'中查看");
            this.getList(); 
            // 清空暂存
            this.pendingBookingRow = null;
            this.pendingBookingRemark = null;
        }).catch((err) => {
            // 【关键修复】兼容多种报错格式，确保能捕捉到错误信息
            let errorMsg = "";
            if (err && err.msg) {
                errorMsg = err.msg;
            } else if (err && err.message) {
                errorMsg = err.message;
            } else {
                errorMsg = String(err);
            }

            // 只要报错信息里包含这句话，就弹出填写框
            if (errorMsg.indexOf('尚未完善个人就诊档案') > -1) {
                // 1. 提示用户
                this.$modal.msgWarning("首次预约，请先完善您的就诊档案");
                
                // 2. 重置表单
                this.resetPatientForm();
                
                // 3. 【核心】强制打开弹窗
                this.patientOpen = true; 
            }
        });
    },

    /** 提交患者信息表单 */
    submitPatientForm() {
      this.$refs["patientForm"].validate(valid => {
        if (valid) {
          // 自动填入当前登录用户的ID
          this.patientForm.userId = this.userId;
          
          // 【核心修改】调用 addPatientProfile 接口 (允许患者调用)
          addPatientProfile(this.patientForm).then(response => {
            this.$modal.msgSuccess("档案建立成功");
            this.patientOpen = false;
            
            // 【自动重试预约】
            if (this.pendingBookingRow && this.pendingBookingRemark) {
                this.$modal.loading("正在为您重试预约...");
                setTimeout(() => {
                    this.$modal.closeLoading();
                    this.doBookRequest(this.pendingBookingRow.scheduleId, this.pendingBookingRemark);
                }, 1000); // 稍微延迟一下，确保数据库事务完成
            }
          });
        }
      });
    }
  }
}
</script>