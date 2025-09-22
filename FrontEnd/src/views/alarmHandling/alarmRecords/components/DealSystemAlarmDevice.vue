<!-- 系统通讯设备告警处理--处理--Dialog -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="400px" :draggable="true" :close-on-click-modal="false"
    @close="handleClose">
    <template #header>
      <span class="el-dialog__title">通讯设备告警--处理</span>
    </template>
    <el-form ref="formAlarm" :model="formData" :rules="rules" class="p-5" label-width="100">
      <el-form-item label="处理结果" prop="status">
        <el-select ref="selRef" v-model="formData.status" placeholder="请选择处理结果" clearable class="w-full"
          filterable fit-input-width :disabled="curDisabled">
          <el-option v-for="item in alarmStatusList" :key="item.value"
            :label="item.name" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="处理描述" prop="alarmDesc">
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
import {  handleAlarmStrategyRecordOfSystem } from '../../ts/index'

//
// const props = defineProps({
//   type: { type: Number, default: () => 0 },
// })
const emits = defineEmits(['submit'])
const curDisabled = ref(false)
//
const dialogVisible = ref(false)
const formAlarm = ref()
const formData = reactive<any>({
  status: "",
  alarmDesc: "",
  id: '',
})
const alarmStatusList = ref<any[]>([
  { name: '已处理', value: 2 },
  { name: '误报', value: 4 },
])
const rules = {
  status: [{ required: true, message: '请选择处理结果', trigger: 'blur' }],
}

const submit = async () => {
  await formAlarm.value?.validate()
  await handleAlarmStrategyRecordOfSystem(formData, formData.id).then()
  dialogVisible.value = false
  emits('submit')
}
const handleClose = () => {
  formData.status = ''
  formData.alarmDesc = ''
  dialogVisible.value = false
}

defineExpose({
  open: (id,data) => {   
    if(data==='edit'){
      curDisabled.value = false
    }else{
      curDisabled.value = true
    }
    formData.id = id
    formData.status = data.alarmStatus
    formData.alarmDesc = data.alarmDesc
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
