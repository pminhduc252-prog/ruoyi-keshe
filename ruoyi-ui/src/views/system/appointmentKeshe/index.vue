<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="患者ID" prop="patientId">
        <el-input
          v-model="queryParams.patientId"
          placeholder="请输入患者ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医生ID" prop="doctorId">
        <el-input
          v-model="queryParams.doctorId"
          placeholder="请输入医生ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排班ID" prop="scheduleId">
        <el-input
          v-model="queryParams.scheduleId"
          placeholder="请输入排班ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="就诊日期" prop="visitDate">
        <el-date-picker clearable
          v-model="queryParams.visitDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择就诊日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="排队序号(叫号用)" prop="queueNumber">
        <el-input
          v-model="queryParams.queueNumber"
          placeholder="请输入排队序号(叫号用)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="挂号费用" prop="regFee">
        <el-input
          v-model="queryParams.regFee"
          placeholder="请输入挂号费用"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['system:appointmentKeshe:add']"
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
          v-hasPermi="['system:appointmentKeshe:edit']"
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
          v-hasPermi="['system:appointmentKeshe:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:appointmentKeshe:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appointmentKesheList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="挂号ID" align="center" prop="appointmentId" />
      <el-table-column label="患者ID" align="center" prop="patientId" />
      <el-table-column label="医生ID" align="center" prop="doctorId" />
      <el-table-column label="排班ID" align="center" prop="scheduleId" />
      <el-table-column label="就诊日期" align="center" prop="visitDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.visitDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="就诊时段" align="center" prop="shiftType" />
      <el-table-column label="排队序号(叫号用)" align="center" prop="queueNumber" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="叫号状态" align="center" prop="visitStatus" />
      <el-table-column label="挂号费用" align="center" prop="regFee" />
      <el-table-column label="就诊需求/症状描述" align="center" prop="symptomDesc" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:appointmentKeshe:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:appointmentKeshe:remove']"
          >删除</el-button>
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

    <!-- 添加或修改挂号预约记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="患者ID" prop="patientId">
          <el-input v-model="form.patientId" placeholder="请输入患者ID" />
        </el-form-item>
        <el-form-item label="医生ID" prop="doctorId">
          <el-input v-model="form.doctorId" placeholder="请输入医生ID" />
        </el-form-item>
        <el-form-item label="排班ID" prop="scheduleId">
          <el-input v-model="form.scheduleId" placeholder="请输入排班ID" />
        </el-form-item>
        <el-form-item label="就诊日期" prop="visitDate">
          <el-date-picker clearable
            v-model="form.visitDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择就诊日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="排队序号(叫号用)" prop="queueNumber">
          <el-input v-model="form.queueNumber" placeholder="请输入排队序号(叫号用)" />
        </el-form-item>
        <el-form-item label="挂号费用" prop="regFee">
          <el-input v-model="form.regFee" placeholder="请输入挂号费用" />
        </el-form-item>
        <el-form-item label="就诊需求/症状描述" prop="symptomDesc">
          <el-input v-model="form.symptomDesc" type="textarea" placeholder="请输入内容" />
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
  </div>
</template>

<script>
import { listAppointmentKeshe, getAppointmentKeshe, delAppointmentKeshe, addAppointmentKeshe, updateAppointmentKeshe } from "@/api/system/appointmentKeshe"

export default {
  name: "AppointmentKeshe",
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
      // 挂号预约记录表格数据
      appointmentKesheList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientId: null,
        doctorId: null,
        scheduleId: null,
        visitDate: null,
        shiftType: null,
        queueNumber: null,
        status: null,
        visitStatus: null,
        regFee: null,
        symptomDesc: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientId: [
          { required: true, message: "患者ID不能为空", trigger: "blur" }
        ],
        doctorId: [
          { required: true, message: "医生ID不能为空", trigger: "blur" }
        ],
        scheduleId: [
          { required: true, message: "排班ID不能为空", trigger: "blur" }
        ],
        visitDate: [
          { required: true, message: "就诊日期不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询挂号预约记录列表 */
    getList() {
      this.loading = true
      listAppointmentKeshe(this.queryParams).then(response => {
        this.appointmentKesheList = response.rows
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
        appointmentId: null,
        patientId: null,
        doctorId: null,
        scheduleId: null,
        visitDate: null,
        shiftType: null,
        queueNumber: null,
        status: null,
        visitStatus: null,
        regFee: null,
        symptomDesc: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
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
      this.ids = selection.map(item => item.appointmentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加挂号预约记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const appointmentId = row.appointmentId || this.ids
      getAppointmentKeshe(appointmentId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改挂号预约记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.appointmentId != null) {
            updateAppointmentKeshe(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAppointmentKeshe(this.form).then(response => {
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
      const appointmentIds = row.appointmentId || this.ids
      this.$modal.confirm('是否确认删除挂号预约记录编号为"' + appointmentIds + '"的数据项？').then(function() {
        return delAppointmentKeshe(appointmentIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/appointmentKeshe/export', {
        ...this.queryParams
      }, `appointmentKeshe_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
