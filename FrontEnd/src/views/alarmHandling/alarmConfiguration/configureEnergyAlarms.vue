<!-- 能源告警配置 -->
<template>
  <div class="content_warp h-full">
    <el-form ref="queryForm" :inline="true" :model="formData" class="flex flex-wrap">
      <el-form-item label="告警级别">
        <el-select v-model="formData.alarmLevelId" style="width: 150px">
          <el-option v-for="item in alarmLevelList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="告警类型">
        <el-select v-model="formData.alarmType" style="width: 150px">
          <el-option v-for="item in alarmTypeList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否音效提示">
        <el-select v-model="formData.soundCues" style="width: 150px">
          <el-option v-for="item in yesOrNoList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item style="flex:1;" class="flex query_Group">
        <XButton icon="Search" @click="query">
          查询
        </XButton>
        <XButton icon="refresh" @click="reset">
          重置
        </XButton>
        <XButton icon="Plus" @click="add">
          新增告警
        </XButton>
        <XButton icon="delete" @click="delBatch">
          批量删除
        </XButton>
      </el-form-item>
    </el-form>
    <el-table ref="table" :data="tableData" class="mb-16px customTable mt-16px"
      @selection-change="handleSelectionChange" :row-key="getRowKeys">
      <el-table-column align="center" type="selection" width="55" :reserve-selection="true" />
      <el-table-column label="序号" align="center" type="index" width="70" />
      <el-table-column prop="alarmContent" align="center" label="告警内容" min-width="100px" />
      <el-table-column prop="typeStr" align="center" label="告警类型" min-width="100px" />
      <el-table-column prop="alarmLevelStr" align="center" label="告警级别" min-width="100px" />
      <el-table-column prop="alarmNoticeTypeStr" align="center" label="告警提示方式" min-width="100px" />
      <el-table-column prop="enabled" align="center" label="告警开关" width="100px">
        <template #default="{ row }">
          <div>
            <el-switch v-model="row.enabled" class="" inline-prompt active-text="ON" inactive-text="OFF"
              style="--el-switch-on-color: #3774C8; --el-switch-off-color: #ff4949" size="large"
              @change="switchChange(row)" />
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="topScreen" label="操作" width="100px">
        <template #default="{ row }">
          <div>
            <el-link class="blue-link" @click="edit(row)" :disabled="row.enabled">
              编辑
            </el-link>
            <el-link class="red-link" @click="del(row.id)" :disabled="row.enabled">
              删除
            </el-link>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="pageParam.pageNo" v-model:page-size="pageParam.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="pageParam.total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" style="margin-right:50px" />
    <AddDialog ref="AddDialogRef" @submit="submit" />
    <AddEnergyConsumptionAlarmDetailDialog ref='AddEnergyConsumptionAlarmDetailDialogRef' @success='success'>
    </AddEnergyConsumptionAlarmDetailDialog>
    <AddEnergyEfficiencyAlarmDetailDialog ref="AddEnergyEfficiencyAlarmDetailDialogRef" @success='success'>
    </AddEnergyEfficiencyAlarmDetailDialog>
  </div>
</template>

<script lang="ts" setup>
import { getAlarmLevelList, getAlarmStrategyPage, alarmStrategyDelete, alarmStrategyDeleteBatch, getAlarmStrategyEnabled } from '../ts/index'
import AddDialog from './components/AddAlarmTypeDialog.vue'
import AddEnergyConsumptionAlarmDetailDialog from './components/AddEnergyConsumptionAlarmDetailDialog.vue'
import AddEnergyEfficiencyAlarmDetailDialog from './components/AddEnergyEfficiencyAlarmDetailDialog.vue'
// import AddThresholdAlarmDetailDialog from './components/AddThresholdAlarmDetailDialog.vue'
//
const AddDialogRef = ref()
const AddEnergyConsumptionAlarmDetailDialogRef = ref()
const AddEnergyEfficiencyAlarmDetailDialogRef = ref()
//
const formData = reactive({
  alarmLevelId: '',
  alarmType: '',
  soundCues: '',
  alarmStrategyType: 1,
})
const pageParam = ref<any>({
  pageSize: 10,
  total: 0,
  pageNo: 1,
})
const alarmLevelList = ref<any[]>([])
const alarmTypeList = ref<any[]>([
  { name: '用能告警', value: 4 },
  { name: '能效告警', value: 5 },
])
const yesOrNoList = ref<any[]>([
  { name: '是', value: true },
  { name: '否', value: false },
])
//
const selection = ref<any[]>([])
const selectionData = ref<any[]>([])
const tableData = ref<any[]>([{}])
//
function query() {
  getData()
}
function reset() {
  for (const k in formData) {
    formData[k] = ''
  }
  formData.alarmStrategyType = 1
  query()
}
const handleCurrentChange = (page: any) => {
  pageParam.value.pageNo = page
  query()
}
const handleSizeChange = (pageSize: any) => {
  pageParam.value.pageSize = pageSize
  query()
}
function switchChange(row) {
  getAlarmStrategyEnabled(row.id, row.enabled).then((res: any) => {
    if (res.code === 200) {
      ElMessage.success('操作成功！')
    } else {
      ElMessage.error(res.msg || '操作成功！')
    }
  })
}
function add() {
  AddDialogRef.value?.open(1)
}
function edit(row) {
  console.log('row: ', row);
  // 用能告警
  if (row.type === 4) {
    AddEnergyConsumptionAlarmDetailDialogRef.value?.open(row.id, 'edit')
  }
  // 能效告警
  if (row.type === 5) {
    AddEnergyEfficiencyAlarmDetailDialogRef.value?.open(row.id, 'edit')
  } alarmStrategyDeleteBatch
}
function delBatch() {
  if (selection.value.length === 0) {
    ElMessage.warning('请勾选表格项目')
  }
  else {
    ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' }).then(() => {
      alarmStrategyDeleteBatch(selection.value).then((res: any) => {
        if (res.code === 200) {
          ElMessage.success('删除成功！')
          selection.value = []
          getData()
        }
        else {
          ElMessage.error(res.msg || '删除失败')
        }
      })
    }).catch(() => { })
  }
}
function del(id) {
  ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' }).then(() => {
    alarmStrategyDelete([id]).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('删除成功！')
        getData()
      }
      else {
        ElMessage.error(res.msg || '删除失败')
      }
    })
  }).catch(() => { })
}
const getRowKeys = (row) => {
  return row.id
}
const handleSelectionChange = (val) => {
  selectionData.value = val
  selection.value = val.map((item) => { return item.id })
}
function submit(val) {
  console.log('val: ', val);
  // 能效告警
  if (val === 5) {
    AddEnergyEfficiencyAlarmDetailDialogRef.value.open('', 'add')
  }
  // 用能告警
  if (val === 4) {
    AddEnergyConsumptionAlarmDetailDialogRef.value.open('', 'add')
  }
}
function success() {
  getData()
}
function getData() {
  // getAlarmStrategyPage(formData, pageParam.value).then((res: any) => {
  //   if (res) {
  //     tableData.value = res.data.records || []
  //     pageParam.value.total = res.data.total ? res.data.total : 0
  //   }
  // })
}
onMounted(() => {
  getData()
  getAlarmLevelList({}).then(res => {
    alarmLevelList.value = res.data

  })
})
</script>

<style lang="scss" scoped>
.content_warp{
  margin-top: 20px;
  height: calc(100% - 20px);
}
:deep(.customTable) {
  height: calc(100% - 100px) !important;
  max-height: calc(100% - 100px) !important;

  img {
    display: inline-block;
  }

  >div.el-table__inner-wrapper {
    height: 100% !important;
  }
}

.des_div {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>
