<!-- 新增/编辑，阈值告警dialog，thresholdDialog -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="1680" :draggable="true" :close-on-click-modal="false"
    @close="handleClose" append-to-body top="3vh">
    <template #header>
      <span class="el-dialog__title">{{ dialogTitle }}</span>
    </template>
    <el-form ref="formAlarm" :model="formData" :rules="rules" class="p-5 flex justify-around" label-width="110" inline>
      <div class="w-1/2">
        <el-form-item label="告警内容" prop="alarmContent">
          <el-input v-model="formData.alarmContent" placeholder="告警内容" style="width: 150px" />
        </el-form-item>
        <el-form-item label="告警阈值设置">
          <div>
            <span>{{ formData.rules.length }} 条阈值规则</span>
            <span class="ml-[10px]  setting-btn" style="color:aqua" @click="settingClick">设置</span>
          </div>
        </el-form-item>
        <el-form-item label="是否过滤告警" prop="isFilterAlarm">
          <el-select v-model="formData.isFilterAlarm" style="width: 150px">
            <el-option v-for="item in yesOrNoList" :key="item.value" :label="item.name" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="告警过滤时间" style="width:360px">
          <el-input v-model="formData.filterTime" placeholder="请输入" style="width: 100px" :disabled="!formData.isFilterAlarm"/>
          <el-select v-model="formData.filterTimeUnit" style="width: 140px" class="ml-[10px]" :disabled="!formData.isFilterAlarm">
            <el-option v-for="item in timeTypeList" :key="item.value" :label="item.name" :value="item.value" />
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
      <div class="bot-left w-[300px] ">
        <div class="w-full flex items-center mt-[20px] ">
          <div class="mx-[15px]">设备类型</div>
          <el-select v-model="formData.thingModels[0].thingModelId" style="width: 200px" @change="deviceTypeChange"
            clearable>
            <el-option v-for="item in deviceTypeList" :key="item.id" :label="item.thingModelName" :value="item.id" />
          </el-select>
        </div>
        <div class=" mt-[20px]">
          <el-checkbox-group v-model="formData.thingModels[0].thingModelPropertyIds" class="text-left ml-20px">
            <el-checkbox v-for="(item, index) in pointArr" :key="index" :label="item.id" :value="item.id"
              class="w-[200px]" style="display:block;margin-left:50px">
              {{ item.thingModelPropertyName }}
            </el-checkbox>
          </el-checkbox-group>
        </div>
      </div>
      <div class="w-[1300px] overflow-auto">
        <el-form ref="queryForm" :inline="true" :model="queryFormData" class="flex flex-wrap mt-[10px] ml-[60px]">
          <el-form-item label="设备名称">
            <el-input v-model="queryFormData.equipmentName" placeholder="设备名称" style="width: 200px" />
          </el-form-item>
          <el-form-item label="设备编号">
            <el-input v-model="queryFormData.equipmentNumber" placeholder="设备编号" style="width: 200px" />
          </el-form-item>
          <el-form-item label="所属系统">
            <el-select v-model="queryFormData.equipmentSystem" style="width: 200px">
              <el-option v-for="item in systemList" :key="item.itemValue" :label="item.itemText"
                :value="item.itemValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="所在建筑">
            <el-select v-model="queryFormData.building" style="width: 200px">
              <el-option v-for="item in buildingList" :key="item.itemValue" :label="item.itemText"
                :value="item.itemValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="所在楼层">
            <el-select v-model="queryFormData.floor" style="width: 200px">
              <el-option v-for="item in floorsList" :key="item.itemValue" :label="item.itemText"
                :value="item.itemValue" />
            </el-select>
          </el-form-item>
          <el-form-item label="所在区域">
            <el-select v-model="queryFormData.equipmentLocation" style="width: 200px">
              <el-option v-for="item in areaList" :key="item.itemValue" :label="item.itemText"
                :value="item.itemValue" />
            </el-select>
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
          <el-table-column prop="equipmentNumber" align="center" label="设备编号" min-width="100px" />
          <el-table-column prop="equipmentName" align="center" label="设备名称" min-width="100px" />
          <el-table-column prop="equipmentSystemName" align="center" label="所属系统" min-width="100px" />
          <el-table-column prop="buildingName" align="center" label="所在建筑" min-width="100px" />
          <el-table-column prop="floorName" align="center" label="所在楼层" min-width="100px" />
          <el-table-column prop="equipmentLocationName" align="center" label="所在区域" min-width="100px" />
          <el-table-column prop="installLocation" align="center" label="安装位置" min-width="100px" />
        </el-table>
        <!-- <el-pagination v-model:current-page="pageParam.pageNo" v-model:page-size="pageParam.pageSize"
          layout="total, sizes, prev, pager, next, jumper" :total="pageParam.total" @size-change="handleSizeChange"
          @current-change="handleCurrentChange" /> -->
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
    <ThresholdSettingDialog ref="ThresholdSettingDialogRef" @settingSubmit="settingSubmit"></ThresholdSettingDialog>
  </el-dialog>
</template>

<script lang="ts" setup>
import {
   getBaseUserList, getAlarmEquipmentThingModels,
  getAlarmEquipmentEquipments, alarmStrategyAdd, getAlarmStrategyById, alarmStrategyUpdate
} from '../../ts/index'
import { getDict } from '@/api/common.ts'
import ThresholdSettingDialog from './ThresholdSettingDialog.vue'
//
// const props = defineProps({
//   type: { type: Number, default: () => 0 },
// })
const emits = defineEmits(['success'])

//
const ThresholdSettingDialogRef =ref()
const tableRef = ref()
const dialogVisible = ref(false)
const dialogTitle = ref('新增阈值告警')
const formAlarm = ref()
const formData = ref<any>({
  id: '',
  name: '',
  type: 2,
  alarmContent: '',
  notices: [],
  webNoticeUsernames: [],
  appNoticeUsernames: [],
  msgNoticeUsernames: [],
  alarmLevelId: '',
  isFilterAlarm: false,
  filterTime: '',
  filterTimeUnit: '',
  rules: [
    // { alarmLevelId: '', minValue: '', maxValue: '', type: '', deadZone: '' }
  ],
  thingModels: [
    { thingModelId: '', thingModelPropertyIds: [] }
  ],
  equipmentIds: []
})
const queryFormData = reactive<any>({
  thingModelIds: '',
  equipmentName: '',
  equipmentNumber: '',
  equipmentSystem: '',
  building: '',
  floor: '',
  equipmentLocation: '',
})
const pointArr = ref<any[]>([])
const systemList = ref<any[]>([])
const buildingList = ref<any[]>([])
const floorsList = ref<any[]>([])
const areaList = ref<any[]>([])
const userList = ref<any[]>([])
const timeTypeList = ref<any[]>([
  { name: '时', value: 2 },
  { name: '分', value: 1 },
  { name: '秒', value: 0 },
])
const yesOrNoList = ref<any[]>([
  { name: '是', value: true },
  { name: '否', value: false },
])
const rules = {
  alarmContent: [{ required: true, message: '请输入告警内容', trigger: 'blur' }],
  alarmLevelId: [{ required: true, message: '请选择告警级别', trigger: 'change' }],
  isFilterAlarm: [{ required: true, message: '请选择是否过滤告警', trigger: 'blur' }],
}
const deviceTypeList = ref<any[]>([])
const selection = ref<any[]>([])
const selectionData = ref<any[]>([])
const tableData = ref<any[]>([])
const pageParam = ref<any>({
  pageSize: 9999,
  total: 0,
  pageNo: 1,
})
//
const settingClick = ()=>{
  ThresholdSettingDialogRef.value?.open(formData.value.rules)
}
const getRowKeys = (row) => {
  return row.equipmentId
}
const handleSelectionChange = (val) => {
  selectionData.value = val
  selection.value = val.map((item) => { return item.equipmentId })
  formData.value.equipmentIds = selection.value
}
const query = () => {
  queryFormData.thingModelId = formData.value.thingModels[0].thingModelId
  getAlarmEquipmentEquipments(queryFormData, pageParam.value).then((res: any) => {
    tableData.value = res.data.records || []
    pageParam.value.total = res.data.total ? res.data.total : 0
  })

}
const reset = () => {
  tableRef.value?.clearSelection()
  for (const key in queryFormData) {
    queryFormData[key] = ''
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
const deviceTypeChange = (val) => {
  selection.value = []
  selectionData.value = []
  if (val) {
    for (let i = 0; i < deviceTypeList.value.length; i++) {
      if (deviceTypeList.value[i].properties && deviceTypeList.value[i].id === val) {
        pointArr.value = deviceTypeList.value[i].properties
      }
    }
  } else {
    pointArr.value = []
    formData.value.thingModels[0].thingModelPropertyIds = []
  }
  queryFormData.thingModelIds = val
  query()
}
function settingSubmit(val){
  formData.value.rules = val
}
//
function clearForm() {
  formData.value.id = ''
  formData.value.name = ''
  formData.value.type = 2
  formData.value.alarmContent = ''
  formData.value.notices = []
  formData.value.webNoticeUsernames = []
  formData.value.appNoticeUsernames = []
  formData.value.msgNoticeUsernames = []
  formData.value.alarmLevelId = ''
  formData.value.isFilterAlarm = ''
  formData.value.filterTime = ''
  formData.value.filterTimeUnit = ''
  formData.value.rules = []
  formData.value.thingModels[0].thingModelId = ''
  formData.value.thingModels[0].thingModelPropertyIds = []
  formData.value.equipmentIds = []
  formAlarm.value?.clearValidate()
}
const submit = async () => {
  await formAlarm.value?.validate()
  formData.value.equipmentIds = selection.value
  if (formData.value.isFilterAlarm && formData.value.filterTime === '' && formData.value.filterTimeUnit === '') {
    ElMessage.error('请输入告警过滤时间')
    return
  }
  if (!formData.value.id) {
    alarmStrategyAdd(formData.value).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('新增成功')
        dialogVisible.value = false
        //
        reset()
        clearForm()
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
        reset()
        clearForm()
        //
        emits('success')
      } else {
        ElMessage.error(res.msg || '编辑失败')
      }
    })
  }
}
const handleClose = () => {
  clearForm()
  reset()
  pointArr.value = []
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
    formAlarm.value?.clearValidate()
    dialogVisible.value = true
    getBaseUserList().then(res => {
      userList.value = res.data
    })
    getAlarmEquipmentThingModels({ alarmType: 2 }).then((res: any) => {
      deviceTypeList.value = res.data
    })
    // 查字典
    getDict('equipment_system').then((res: any) => {
      if (res.code === 200 && res.data.length > 0)
        systemList.value = res.data
    })
    getDict('equipment_building').then((res: any) => {
      if (res.code === 200 && res.data.length > 0)
        buildingList.value = res.data
    })
    getDict('equipment_floor').then((res: any) => {
      if (res.code === 200 && res.data.length > 0)
        floorsList.value = res.data
    })
    getDict('equipment_area').then((res: any) => {
      if (res.code === 200 && res.data.length > 0)
        areaList.value = res.data
    })
    if (id) {
      dialogTitle.value = '编辑阈值告警'
      getAlarmStrategyById(id).then(async (res: any) => {
        formData.value = res.data
        deviceTypeChange(res.data.thingModels[0].thingModelId)
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
      query()
      dialogTitle.value = '新增阈值告警'
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

  .bot-left {
    border-right: 1px solid #fff;
  }
}
.setting-btn{
  &:hover{
    cursor: pointer;
  }
}
</style>