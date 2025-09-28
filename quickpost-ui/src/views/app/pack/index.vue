<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="取件码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入取件码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['app:pack:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['app:pack:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['app:pack:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['app:pack:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="packList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="包裹ID" align="center" prop="packId" />
      <el-table-column label="文件ID列表" align="center" prop="fileIds" />
      <el-table-column label="取件码" align="center" prop="code" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="有效期类型" align="center" prop="validTimeType" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['app:pack:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['app:pack:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改包裹信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="packRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件ID列表" prop="fileIds">
          <el-input v-model="form.fileIds" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="取件码" prop="code">
          <el-input v-model="form.code" placeholder="请输入取件码" />
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Pack">
import { listPack, getPack, delPack, addPack, updatePack } from "@/api/app/pack"

const { proxy } = getCurrentInstance()

const packList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    fileIds: null,
    code: null,
    userId: null,
    validTimeType: null,
    status: null,
    ver: null
  },
  rules: {
    fileIds: [
      { required: true, message: "文件ID列表不能为空", trigger: "blur" }
    ],
    code: [
      { required: true, message: "取件码不能为空", trigger: "blur" }
    ],
    validTimeType: [
      { required: true, message: "有效期类型不能为空", trigger: "change" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询包裹信息列表 */
function getList() {
  loading.value = true
  listPack(queryParams.value).then(response => {
    packList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    packId: null,
    fileIds: null,
    code: null,
    userId: null,
    validTimeType: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    status: null,
    ver: null
  }
  proxy.resetForm("packRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.packId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加包裹信息"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _packId = row.packId || ids.value
  getPack(_packId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改包裹信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["packRef"].validate(valid => {
    if (valid) {
      if (form.value.packId != null) {
        updatePack(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addPack(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _packIds = row.packId || ids.value
  proxy.$modal.confirm('是否确认删除包裹信息编号为"' + _packIds + '"的数据项？').then(function() {
    return delPack(_packIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('app/pack/export', {
    ...queryParams.value
  }, `pack_${new Date().getTime()}.xlsx`)
}

getList()
</script>
