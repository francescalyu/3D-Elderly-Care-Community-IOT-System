<!-- 新增告警Dialog -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="400px" :draggable="true" :close-on-click-modal="false"
    @close="handleClose">
    <template #header>
      <span class="el-dialog__title">{{ dialogTitle }}</span>
    </template>
    <el-form ref="formAlarm" :model="formData" :rules="rules" class="p-5" label-width="100">
      <el-form-item label="告警类型" prop="alarmType">
        <el-select v-model="formData.alarmType">
          <el-option v-for="item in alarmTypeList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
    </el-form>

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
//
// const props = defineProps({
//   type: { type: Number, default: () => 0 },
// })
const emits = defineEmits(['submit'])

//
const dialogVisible = ref(false)
const dialogTitle = '新增告警'
const formAlarm = ref()
const formData = reactive<any>({
  alarmType: ''
})
const alarmTypeList = ref<any[]>([])
// 1-故障告警 2-阈值告警 3- 采集数据异常 4-用能告警 5-能效告警 6-通讯设备告警
const deviceTypeList = ref<any[]>([
  { name: '故障告警', value: 1 },
  { name: '阈值告警', value: 2 },
])
const systemTypeList = ref<any[]>([
  { name: '系统通讯设备告警', value: 6 },
  { name: '系统采集数据告警', value: 3 },
])
const energyTypeList = ref<any[]>([
  { name: '用能告警', value: 4 },
  { name: '能效告警', value: 5 },
])
const rules = {
  alarmType: [{ required: true, message: '请选择告警类型', trigger: 'blur' }],
}

const submit = async () => {
  await formAlarm.value?.validate()
  dialogVisible.value = false
  emits('submit', formData.alarmType)
}
const handleClose = () => {
  formData.alarmType = ''
  dialogVisible.value = false
}

defineExpose({
  open: (type) => {
    dialogVisible.value = true
    // 设备告警
    if (type === 0) {
      alarmTypeList.value = deviceTypeList.value
    }
    // 系统告警
    if (type === 2) {
      alarmTypeList.value = systemTypeList.value
    }
    // 能源告警
    if (type === 1) {
      alarmTypeList.value = energyTypeList.value
    }
  },
})
</script>

<style lang="scss" scoped>
:deep(.el-dialog__body) {
  height: 600px;
}
</style>
