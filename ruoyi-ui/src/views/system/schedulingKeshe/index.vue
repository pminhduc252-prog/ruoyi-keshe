<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="医生ID" prop="doctorId">
        <el-input
          v-model="queryParams.doctorId"
          placeholder="请输入医生ID"
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
      <el-form-item label="总号源数量" prop="totalQuota">
        <el-input
          v-model="queryParams.totalQuota"
          placeholder="请输入总号源数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="剩余号源数量" prop="availableQuota">
        <el-input
          v-model="queryParams.availableQuota"
          placeholder="请输入剩余号源数量"
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
      <el-table-column label="排班ID" align="center" prop="scheduleId" />
      <el-table-column label="医生ID" align="center" prop="doctorId" />
      <el-table-column label="出诊日期" align="center" prop="workDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.workDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="时段" align="center" prop="shiftType" />
      <el-table-column label="总号源数量" align="center" prop="totalQuota" />
      <el-table-column label="剩余号源数量" align="center" prop="availableQuota" />
      <el-table-column label="排班状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
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

    <!-- 添加或修改医生排班对话框 -->
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
  </div>
</template>

<script>
import { listSchedulingKeshe, getSchedulingKeshe, delSchedulingKeshe, addSchedulingKeshe, updateSchedulingKeshe } from "@/api/system/schedulingKeshe"

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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        doctorId: null,
        workDate: null,
        shiftType: null,
        totalQuota: null,
        availableQuota: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
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
      }
    }
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
    /** 提交按钮 */
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
    }
  }
}
</script>
