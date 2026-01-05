<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="医生ID" prop="doctorId" v-hasRole="['admin']">
        <el-input
          v-model="queryParams.doctorId"
          placeholder="请输入医生ID"
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
      
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="待就诊" value="0" />
          <el-option label="已就诊" value="1" />
          <el-option label="已取消" value="2" />
          <el-option label="已过期" value="3" />
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
      <el-table-column label="挂号单号" align="center" prop="appointmentId" width="80" />
      
      <el-table-column label="患者姓名" align="center" prop="patientName" width="100" />
      <el-table-column label="预约医生" align="center" prop="doctorName" width="100" />
      
      <el-table-column label="就诊日期" align="center" prop="visitDate" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.visitDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="时段" align="center" prop="shiftType" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.shiftType == '1'" effect="plain">上午</el-tag>
          <el-tag v-else-if="scope.row.shiftType == '2'" type="warning" effect="plain">下午</el-tag>
          <el-tag v-else-if="scope.row.shiftType == '3'" type="danger" effect="plain">晚班</el-tag>
          <span v-else>{{ scope.row.shiftType }}</span>
        </template>
      </el-table-column>

      <el-table-column label="排队号" align="center" prop="queueNumber" width="80">
         <template slot-scope="scope">
            <span style="font-weight: bold; font-size: 16px; color: #409EFF;">{{ scope.row.queueNumber || '-' }}</span>
         </template>
      </el-table-column>

      <el-table-column label="预约状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '0'" type="primary">待就诊</el-tag>
          <el-tag v-else-if="scope.row.status == '1'" type="success">已就诊</el-tag>
          <el-tag v-else-if="scope.row.status == '2'" type="info">已取消</el-tag>
          <el-tag v-else-if="scope.row.status == '3'" type="danger">已过期</el-tag>
          <span v-else>{{ scope.row.status }}</span>
        </template>
      </el-table-column>

      <el-table-column label="诊疗进度" align="center" prop="visitStatus" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.visitStatus == '0'" type="info">未报到</el-tag>
          <el-tag v-else-if="scope.row.visitStatus == '1'" type="warning">候诊中</el-tag>
          <el-tag v-else-if="scope.row.visitStatus == '2'" type="success" effect="dark">就诊中</el-tag>
          <el-tag v-else-if="scope.row.visitStatus == '3'" type="success">已完成</el-tag>
          <span v-else>{{ scope.row.visitStatus }}</span>
        </template>
      </el-table-column>

      <el-table-column label="病情描述" align="center" prop="symptomDesc" :show-overflow-tooltip="true" />
      
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
            v-if="scope.row.status == '0'"
            size="mini"
            type="text"
            icon="el-icon-close"
            style="color: #F56C6C;"
            @click="handleCancel(scope.row)"
          >取消预约</el-button>
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
        <el-form-item label="就诊日期" prop="visitDate">
          <el-date-picker clearable
            v-model="form.visitDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择就诊日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="状态" prop="status">
           <el-radio-group v-model="form.status">
             <el-radio label="0">待就诊</el-radio>
             <el-radio label="1">已就诊</el-radio>
             <el-radio label="2">已取消</el-radio>
           </el-radio-group>
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
        doctorId: [
          { required: true, message: "医生ID不能为空", trigger: "blur" }
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
        status: "0",
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
    },
    /** 取消预约逻辑 (修改状态为2) */
    handleCancel(row) {
        this.$modal.confirm('确认要取消这条预约吗？取消后不可恢复。').then(function() {
            // 复用更新接口，只传 ID 和新的状态
            return updateAppointmentKeshe({ appointmentId: row.appointmentId, status: '2' });
        }).then(() => {
            this.getList();
            this.$modal.msgSuccess("取消成功");
        }).catch(() => {});
    }
  }
}
</script>