<!-- 设备告警处理--处理--Dialog -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="400px" :draggable="true" :close-on-click-modal="false"
    @close="handleClose">
    <template #header>
      <span class="el-dialog__title">{{ curTitle }}处理</span>
    </template>
    <el-form ref="formAlarm" :model="formData" :rules="rules" class="p-5" label-width="100">
      <el-form-item label="处理结果" prop="handleUsername">
        <!-- <el-select ref="selRef" v-model="formData.handleUsername" placeholder="请选择用户" clearable class="w-full"
          filterable fit-input-width>
          <el-option v-for="item in userList" :key="item.username"
            :label="item.realName" :value="item.username"></el-option>
        </el-select> -->
        <el-select ref="selRef" v-model="formData.status" placeholder="请选择处理结果" clearable class="w-full" filterable
          fit-input-width :disabled="curDisabled">
          <el-option v-for="item in alarmStatusList" :key="item.value" :label="item.name"
            :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="告警描述" prop="alarmDesc">
        <el-input v-model="formData.alarmDesc" type="textarea" :rows="3" placeholder="请输入内容" resize="none"
          show-word-limit maxlength="200" :disabled="curDisabled">
        </el-input>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="text-center">
        <el-button type="primary" class="btn_cancel" @click="handleClose">
          取 消
        </el-button>
        <el-button type="primary" :disabled="curDisabled" @click="submit">
          确 定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { getBaseUserList, handleAlarmStrategyRecord, handleAlarmStrategyRecordOfSystem } from '../../ts/index'

//
// const props = defineProps({
//   type: { type: Number, default: () => 0 },
// })
const emits = defineEmits(['submit'])
const curTitle = ref('')
const curDisabled = ref(false)
//
const dialogVisible = ref(false)
const formAlarm = ref()
const formData = reactive<any>({
  // type: "repair",
  // handleUsername: "",
  status: "",
  alarmDesc: "",
  id: '',
})
const userList = ref<any[]>([])
const alarmStatusList = ref<any[]>([
  // { name: '处理中', value: 1 },
  { name: '已处理', value: 2 },
  { name: "待处理", value: 0 },
  // { name: '已解除', value: 3 },
  { name: '误报', value: 4 },
])
// const rules = {
//   handleUsername: [{ required: true, message: '请选择用户', trigger: 'blur' }],
// }

const submit = async () => {
  await formAlarm.value?.validate()
  dialogVisible.value = false
  await handleAlarmStrategyRecordOfSystem(formData, formData.id).then()
  emits('submit')
}
const handleClose = () => {
  formData.alarmType = ''
  dialogVisible.value = false
}

defineExpose({
  open: (id, name, type, data) => {
    if (type == 'edit') {
      formData.status = data.alarmStatus
      formData.alarmDesc = data.alarmDesc
      curDisabled.value = true
    } else {
      formData.status = ''
      formData.alarmDesc = ''
      curDisabled.value = false
    }
    curTitle.value = name
    formData.id = id
    // nextTick(() => {
    //   getBaseUserList().then(res => {
    //     userList.value = res.data
    //   })
    // })
    dialogVisible.value = true
  },
})
</script>

<style lang="scss" scoped>
:deep(.el-dialog__body) {
  height: 600px;
}

:deep(.el-textarea) {
  .el-textarea__inner {
    background-color: #2a3e62;
    box-shadow: none;
    color: #fff;
  }

  .el-input__count {
    background: transparent;
  }
}
</style>
