<!-- 系统告警配置 -->
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
    <AddCommunicationDeviceAlarmDetailDialog ref='AddCommunicationDeviceAlarmDetailDialogRef' @success='success'>
    </AddCommunicationDeviceAlarmDetailDialog>
    <AddDataCollectionAlarmDetailDialog ref="AddDataCollectionAlarmDetailDialogRef" @success='success'>
    </AddDataCollectionAlarmDetailDialog>
  </div>
</template>

<script lang="ts" setup>
import { getAlarmLevelList, getAlarmStrategyPage, alarmStrategyDelete,alarmStrategyDeleteBatch, getAlarmStrategyEnabled } from '../ts/index'
import AddDialog from './components/AddAlarmTypeDialog.vue'
import AddCommunicationDeviceAlarmDetailDialog from './components/AddCommunicationDeviceAlarmDetailDialog.vue'
import AddDataCollectionAlarmDetailDialog from './components/AddDataCollectionAlarmDetailDialog.vue'
// import AddThresholdAlarmDetailDialog from './components/AddThresholdAlarmDetailDialog.vue'
//
const AddDialogRef = ref()
const AddCommunicationDeviceAlarmDetailDialogRef = ref()
const AddDataCollectionAlarmDetailDialogRef = ref()
//
const formData = reactive({
  alarmLevelId: '',
  alarmType: '',
  soundCues: '',
  alarmStrategyType: 2,
})
const pageParam = ref<any>({
  pageSize: 10,
  total: 0,
  pageNo: 1,
})
const alarmLevelList = ref<any[]>([])
const alarmTypeList = ref<any[]>([
  { name: '系统通讯设备告警', value: 6 },
  { name: '系统采集数据告警', value: 3 },
])
const yesOrNoList = ref<any[]>([
  { name: '是', value: true },
  { name: '否', value: false },
])
//
const selection = ref<any[]>([])
const selectionData = ref<any[]>([])
const tableData = ref<any[]>([])
//
function query() {
  getData()
}
function reset() {
  for (const k in formData) {
    formData[k] = ''
  }
  formData.alarmStrategyType = 2
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
  AddDialogRef.value?.open(2)
}
function edit(row) {
  // 通讯设备
  if (row.type === 6) {
    AddCommunicationDeviceAlarmDetailDialogRef.value?.open(row.id, 'edit')
  }
  // 采集异常
  if (row.type === 3) {
    AddDataCollectionAlarmDetailDialogRef.value?.open(row.id, 'edit')
  }
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
  // 系统采集数据告警
  if (val === 3) {
    AddDataCollectionAlarmDetailDialogRef.value.open('', 'add')
  }
  // 系统通讯设备告警
  if (val === 6) {
    AddCommunicationDeviceAlarmDetailDialogRef.value.open('', 'add')
  }
}
function success() {
  getData()
}
function getData() {
  getAlarmStrategyPage(formData, pageParam.value).then((res: any) => {
    if (res) {
      tableData.value = res.data.records || []
      pageParam.value.total = res.data.total ? res.data.total : 0
    }
  })
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
