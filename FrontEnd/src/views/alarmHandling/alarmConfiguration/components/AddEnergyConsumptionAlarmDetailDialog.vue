<!-- 新增/编辑，用能告警Dialog -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="1680" :draggable="true" :close-on-click-modal="false"
    @close="handleClose" append-to-body top="3vh">
    <template #header>
      <span class="el-dialog__title">{{ dialogTitle }}</span>
    </template>
    <el-form ref="formAlarm" :model="formData" :rules="rules" class="p-5 flex justify-around" label-width="108" inline>
      <div class="w-1/2">
        <el-form-item label="告警内容" prop="alarmContent">
          <el-input v-model="formData.alarmContent" placeholder="告警内容" style="width: 150px" />
        </el-form-item>
        <el-form-item label="告警级别" prop="alarmLevelId">
          <el-select v-model="formData.alarmLevelId" style="width: 150px">
            <el-option v-for="item in alarmLevelList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
      </div>
      <div class="w-1/2">
        <el-form-item label="提醒方式">
          <el-checkbox-group v-model="formData.notices" class="w-full flex">
            <div class="items-center flex">
              <el-checkbox :label="1" :value="1">
                <span>网页弹窗</span>
              </el-checkbox>
              <el-select ref="selRef" v-model="formData.webNoticeUsernames"
                :disabled="formData.notices.indexOf(1) === -1" multiple placeholder="请选择用户" clearable
                class="w-full ml-[20px]" filterable fit-input-width collapse-tags :max-collapse-tags="1"
                style="width: 220px">
                <el-option v-for="item in userList" :key="item.username"
                  :label="item.realName" :value="item.username"></el-option>
              </el-select>
            </div>
            <!-- <div class="items-center">
              <el-checkbox :label="2" :value="2" class="">
                <span>APP通知</span>
              </el-checkbox>
              <el-select ref="selRef" v-model="formData.appNoticeUsernames"
                :disabled="formData.notices.indexOf(2) === -1" multiple placeholder="请选择用户" clearable
                class="w-full ml-[0px]" filterable fit-input-width collapse-tags :max-collapse-tags="1"
                style="width: 220px">
                <el-option v-for="item in userList" :key="item.username"
                  :label="item.realName" :value="item.username"></el-option>
              </el-select>
            </div> -->
            <!-- <div class="items-center flex ml-[50px]">
              <el-checkbox :label="3" :value="3" class="">
                <span>消息记录</span>
              </el-checkbox>
              <el-select ref="selRef" v-model="formData.msgNoticeUsernames"
                :disabled="formData.notices.indexOf(3) === -1" multiple placeholder="请选择用户" clearable
                class="w-full ml-[20px]" filterable fit-input-width collapse-tags :max-collapse-tags="1"
                style="width: 220px">
                <el-option v-for="item in userList" :key="item.username"
                  :label="item.realName" :value="item.username"></el-option>
              </el-select>
            </div> -->
          </el-checkbox-group>
        </el-form-item>
      </div>
    </el-form>
    <!--下半部分-->
    <section class="flex bot-section h-[550px]">
      <div class="w-full overflow-auto">
        <el-form ref="queryForm" :inline="true" :model="queryFormData" class="flex flex-wrap mt-[10px] ml-[60px]">
          <el-form-item label="能源类别名称">
            <el-input v-model="queryFormData.name" placeholder="设备名称" style="width: 200px" />
          </el-form-item>
          <el-form-item label="能源所属">
            <el-select v-model="queryFormData.energyBelong" style="width: 200px">
              <el-option v-for="item in energyBelongList" :key="item.itemValue" :label="item.itemText"
                :value="item.itemValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="能源类型">
            <el-select v-model="queryFormData.energyType" style="width: 200px">
              <el-option v-for="item in energyTypeList" :key="item.itemValue" :label="item.itemText"
                :value="item.itemValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="配额年度">
            <el-date-picker v-model="formData.year" type="year" format="YYYY" value-format="YYYY">
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
        <el-table ref="tableRef" :data="tableData" class="mb-16px customTable mt-16px"
          @selection-change="handleSelectionChange" :row-key="getRowKeys">
          <el-table-column align="center" type="selection" width="55" :reserve-selection="true" />
          <el-table-column label="序号" align="center" type="index" width="70" />
          <el-table-column prop="name" align="center" label="能源类别名称" min-width="100px" />
          <el-table-column prop="type" align="center" label="能源类型" min-width="100px" />
          <el-table-column prop="remoteIp" align="center" label="能源所属" min-width="100px" />
          <el-table-column prop="intervalSecs" align="center" label="配额年度" min-width="100px" />
          <el-table-column prop="intervalSecs" align="center" label="年度配额" min-width="100px" />
          <el-table-column prop="intervalSecs" align="center" label="查看月度配额" min-width="100px" >
            <template #default="{ row }">
          <div>
            <el-link class="blue-link" @click="view(row)">
              查看
            </el-link>
          </div>
        </template>
          </el-table-column>
        </el-table>
        <el-pagination v-model:current-page="pageParam.pageNo" v-model:page-size="pageParam.pageSize"
          layout="total, sizes, prev, pager, next, jumper" :total="pageParam.total" @size-change="handleSizeChange"
          @current-change="handleCurrentChange" />
      </div>
    </section>
    <template #footer>
      <div class="text-center">
        <el-button type="primary" class="btn_cancel" @click="handleClose">
          取 消
        </el-button>
        <el-button type="primary" @click="submit">
          确 定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import {
  getAlarmLevelList, getBaseUserList, getAlarmCommunicationEquipments, alarmStrategyAdd, getAlarmStrategyById, alarmStrategyUpdate
} from '../../ts/index'

//
// const props = defineProps({
//   type: { type: Number, default: () => 0 },
// })
const emits = defineEmits(['success'])

//
const tableRef = ref()
const dialogVisible = ref(false)
const dialogTitle = ref('新增用能告警')
const formAlarm = ref()
const formData = ref<any>({
  id: '',
  name: '',
  type: 4,
  alarmContent: '',
  notices: [],
  webNoticeUsernames: [],
  appNoticeUsernames: [],
  msgNoticeUsernames: [],
  alarmLevelId: '',
  rules: [],
  equipmentIds: []
})
const queryFormData = reactive<any>({
  name: '',
  energyBelong: '',
  energyType: '',
  year: '',
})
const energyBelongList = ref<any[]>([
  { itemText: '部门1', itemValue: '1' }
])
const energyTypeList = ref<any[]>([
  { itemText: '电', itemValue: '1' },
  { itemText: '水', itemValue: '2' },
  { itemText: '热', itemValue: '3' },
])
const alarmLevelList = ref<any[]>([])
const userList = ref<any[]>([])

const rules = {
  alarmContent: [{ required: true, message: '请输入告警内容', trigger: 'blur' }],
  alarmLevelId: [{ required: true, message: '请选择告警级别', trigger: 'change' }],
}
const selection = ref<any[]>([])
const selectionData = ref<any[]>([])
const tableData = ref<any[]>([{}])
const pageParam = ref<any>({
  pageSize: 10,
  total: 0,
  pageNo: 1,
})
//
const getRowKeys = (row) => {
  return row.equipmentId
}
const handleSelectionChange = (val) => {
  selectionData.value = val
  selection.value = val.map((item) => { return item.equipmentId })
  formData.value.equipmentIds = selection.value
}
const query = () => {
  // getAlarmCommunicationEquipments(queryFormData, pageParam.value).then((res: any) => {
  //   tableData.value = res.data.records || []
  //   pageParam.value.total = res.data.total ? res.data.total : 0
  // })
}
const reset = () => {
  pageParam.value.pageSize = 10
  pageParam.value.pageNo = 1
  selection.value = []
  selectionData.value = []
  for (const k in queryFormData) {
    queryFormData[k] = ''
  }
  query()
}
const handleCurrentChange = (page: any) => {
  pageParam.value.pageNo = page
  query()
  formData.value.equipmentIds.forEach(w => {
    tableData.value.forEach(v => {
      if (v.equipmentId === w) tableRef.value?.toggleRowSelection(v, true)
    })
  })
}
const handleSizeChange = (pageSize: any) => {
  pageParam.value.pageSize = pageSize
  query()
  formData.value.equipmentIds.forEach(w => {
    tableData.value.forEach(v => {
      if (v.equipmentId === w) tableRef.value?.toggleRowSelection(v, true)
    })
  })
}
function view(){
  
}
//
function clearForm() {
  formData.value.id = ''
  formData.value.name = ''
  formData.value.type = 4
  formData.value.alarmContent = ''
  formData.value.notices = []
  formData.value.webNoticeUsernames = []
  formData.value.appNoticeUsernames = []
  formData.value.msgNoticeUsernames = []
  formData.value.alarmLevelId = ''
  formData.value.rules = []
  formData.value.equipmentIds = []
  formAlarm.value?.clearValidate()
}
const submit = async () => {
  await formAlarm.value?.validate()
  formData.value.equipmentIds = selection.value
  if (!formData.value.id) {
    alarmStrategyAdd(formData.value).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('新增成功')
        dialogVisible.value = false
        //
        clearForm()
        reset()
        //
        emits('success')
      } else {
        ElMessage.error(res.msg || '新增失败')
      }
    })
  } else {
    alarmStrategyUpdate(formData.value).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('编辑成功')
        dialogVisible.value = false
        //
        clearForm()
        reset()
        //
        emits('success')
      } else {
        ElMessage.error(res.msg || '编辑失败')
      }
    })
  }
}
const handleClose = () => {
  reset()
  clearForm()
  dialogVisible.value = false
}
watch(() => formData.value.notices, (val) => {
  if (val.indexOf(1) === -1) {
    formData.value.webNoticeUsernames = []
  }
  if (val.indexOf(2) === -1) {
    formData.value.appNoticeUsernames = []
  }
  if (val.indexOf(3) === -1) {
    formData.value.msgNoticeUsernames = []
  }
})

defineExpose({
  open: async (id) => {
    console.log('id: ', id);
    formAlarm.value?.clearValidate()
    dialogVisible.value = true
    getAlarmLevelList({}).then(res => {
      alarmLevelList.value = res.data
    })
    getBaseUserList().then(res => {
      userList.value = res.data
    })
    await query()
    if (id) {
      dialogTitle.value = '编辑用能告警'
      getAlarmStrategyById(id).then(async (res: any) => {
        formData.value = res.data
        setTimeout(() => {
          if (formData.value.equipmentIds && formData.value.equipmentIds.length > 0) {
            formData.value.equipmentIds.forEach(w => {
              tableData.value.forEach(v => {
                if (v.equipmentId === w) {
                  tableRef.value?.toggleRowSelection(v, true)
                }
              })
            })
          }
        }, 200);
      })
    } else {
      dialogTitle.value = '新增用能告警'
    }
  },
})
</script>

<style lang="scss" scoped>
:deep(.el-dialog__body) {
  height: 600px;
}

:deep(.customTable) {
  height: 400px !important;
  // max-height: calc(100% - 100px) !important;
  padding-left: 20px;
}

:deep(.el-input__wrapper) {
  width: 150px
}

.bot-section {
  border: 1px solid #fff;
  min-height: 450px;
}
</style>