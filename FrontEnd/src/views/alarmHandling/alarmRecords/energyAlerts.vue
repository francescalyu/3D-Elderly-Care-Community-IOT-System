<!-- 能源告警 -->
<template>
  <div class="content_warp h-full">
    <el-form ref="queryForm" :inline="true" :model="formData" class="flex flex-wrap">
      <el-form-item label="告警类型">
        <el-select v-model="formData.alarmType" style="width: 150px">
          <el-option v-for="item in alarmTypeList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="告警级别">
        <el-select v-model="formData.alarmLevel" style="width: 150px">
          <el-option v-for="item in alarmLevelList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="告警时间">
        <el-date-picker v-model="formData.alarmTime" type="daterange" range-separator="至" start-placeholder="开始日期"
          end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD">
        </el-date-picker>
      </el-form-item>
      <el-form-item style="flex:1;" class="flex query_Group">
        <XButton icon="Search" @click="query">
          查询
        </XButton>
        <XButton icon="refresh" @click="reset">
          重置
        </XButton>
      </el-form-item>
    </el-form>
    <el-table ref="table" :data="tableData" class="mb-[32px] customTable mt-[5px]"
      @selection-change="handleSelectionChange" :row-key="getRowKeys">
      <el-table-column align="center" type="selection" width="55"  />
      <el-table-column label="序号" align="center" type="index" width="70" />
      <el-table-column prop="alarmLevel" align="center" label="告警内容" min-width="100px" />
      <el-table-column prop="alarmLevel" align="center" label="告警类型" min-width="100px" />
      <el-table-column prop="alarmLevel" align="center" label="能源类型" min-width="100px" />
      <el-table-column prop="alarmLevel" align="center" label="告警所属" min-width="100px" />
      <el-table-column prop="alarmLevel" align="center" label="告警时间" width="200px" />
      <el-table-column align="center" prop="topScreen" label="操作" width="180px">
        <template #default="{ row }">
          <div>
            <el-link class="blue-link" @click="view(row)">
              查看
            </el-link>
            <el-link class="yellow-link" @click="doDeal(row)">
              诊断
            </el-link>
            <el-link class="red-link" @click="del(row.id)">
              删除
            </el-link>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="pageParam.pageNo" v-model:page-size="pageParam.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="pageParam.total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" style="margin-right:50px" />
    <DetailSystemAlarmEnergy ref="DetailSystemAlarmEnergyRef"></DetailSystemAlarmEnergy>
  </div>
</template>

<script lang="ts" setup>
import { getAlarmStrategyRecordPage, getAlarmLevelList,delAlarmStrategyRecord } from '../ts/index'
import DetailSystemAlarmEnergy from './components/DetailSystemAlarmEnergy.vue'
import dayjs from 'dayjs'

//
const DetailSystemAlarmEnergyRef = ref()
//
const formData = reactive({
  alarmType: '',
  alarmLevel: '',
  alarmTime: [],
  alarmStrategyType: 1
})
const pageParam = ref<any>({
  pageSize: 10,
  total: 0,
  pageNo: 1,
})
const alarmTypeList = ref<any[]>([
  { name: '用能告警', value: 4 },
  { name: '用能预警', value: 7 },
  { name: '能效告警', value: 5 },
  { name: '能效预警', value: 8 },
])
const alarmLevelList = ref<any[]>([])
//
const selection = ref<any[]>([])
const selectionData = ref<any[]>([])
const tableData = ref<any[]>([{}])
//
function query() {
  console.log('formData: ', formData);
  getData()
}
function reset() {
  for (const k in formData) {
    formData[k] = ''
  }
  formData.alarmStrategyType = 1
  formData.alarmTime = []
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
function view(row) {
  // console.log('row: ', row);
  DetailSystemAlarmEnergyRef.value?.open(row)
}
// 诊断
function doDeal(row) {
  // 页面跳转
  console.log('row: ', row);
}
function del(id) {
  ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' }).then(() => {
    delAlarmStrategyRecord(id).then((res:any) => {
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

// function submit() {
//   getData()
// }
function getData() {
  let pushData = formData as any
  console.log('pushData: ', pushData);
  if (pushData.alarmTime.length > 0) {
    pushData.alarmTime[0] = dayjs(pushData.alarmTime[0]).valueOf()
    pushData.alarmTime[1] = dayjs(pushData.alarmTime[1]).valueOf()
  }
  getAlarmStrategyRecordPage(pushData, pageParam.value).then((res) => {
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
  height: calc(100% - 150px) !important;
  max-height: calc(100% - 150px) !important;

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
