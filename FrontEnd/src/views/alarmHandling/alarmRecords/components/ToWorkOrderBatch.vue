<!-- 设备告警转工单--Dialog -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="400px" :draggable="true" :close-on-click-modal="false"
    @close="handleClose">
    <template #header>
      <span class="el-dialog__title">设备告警转工单</span>
    </template>
    <el-form ref="formAlarm" :model="formData" :rules="rules" class="p-5" label-width="100">
      <el-form-item label="处理人员" prop="handleUsername">
        <el-select ref="selRef" v-model="formData.handleUsername" placeholder="请选择用户" clearable class="w-full"
          filterable fit-input-width>
          <el-option v-for="item in userList" :key="item.username"
            :label="item.realName" :value="item.username"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="告警描述" prop="alarmDesc">
        <el-input v-model="formData.alarmDesc" type="textarea" :rows="3" placeholder="请输入内容" resize="none" show-word-limit
         maxlength="200">
        </el-input>
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
import { getBaseUserList,toWorkOrderBatch } from '../../ts/index'

//
const emits = defineEmits(['submit'])

//
const dialogVisible = ref(false)
const formAlarm = ref()
const formData = reactive<any>({
  type: "repair",
  handleUsername: "",
  alarmDesc: "",
  ids: '',
})
const userList = ref<any[]>([])
const rules = {
  handleUsername: [{ required: true, message: '请选择用户', trigger: 'blur' }],
}

const submit = async () => {
  await formAlarm.value?.validate()
  dialogVisible.value = false
  await toWorkOrderBatch(formData, formData.ids).then()
  emits('submit')
}
const handleClose = () => {
  formData.handleUsername = ''
  formData.alarmDesc = ''
  formData.ids = ''
  dialogVisible.value = false
}

defineExpose({
  open: (ids) => {
    formAlarm.value?.clearValidate()
    formData.ids = ids
    nextTick(() => {
      getBaseUserList().then(res => {
        userList.value = res.data
      })
    })
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
