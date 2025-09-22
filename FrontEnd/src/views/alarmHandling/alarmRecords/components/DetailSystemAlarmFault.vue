<!-- 设备告警故障告警--查看--Dialog -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="600px" :draggable="true" :close-on-click-modal="false"
    @close="handleClose">
    <template #header>
      <span class="el-dialog__title">设备告警故障告警--查看</span>
    </template>
    <section class="content flex justify-between">
      <div class="left w-[300px]">
        <div>
          <span class="w-[80px]">告警内容：</span>
          <span>{{ formData.alarmContent }}</span>
        </div>
        <div>
          <span class="w-[80px]">告警设备：</span>
          <span>{{ formData.equipmentName }}</span>
        </div>
        <div>
          <span class="w-[80px]">告警类型：</span>
          <span>{{ formData.alarmTypeStr }}</span>
        </div>
        <div class="w-[600px]">
          <span class="w-[80px]">告警详情：</span>
          <span style="display: inline-block;width:460px;white-space:normal" :title="formData.alarmDetail">{{ formData.alarmDetail }}</span>
        </div>
      </div>
      <div class="right w-[300px]">
        <div>
          <span class="w-[80px]">告警级别：</span>
          <span>{{ formData.alarmLevelName }}</span>
        </div>
        <div>
          <span class="w-[80px]">安装位置：</span>
          <span>{{ formData.installLocation }}</span>
        </div>
        <div>
          <span class="w-[80px]">告警时间：</span>
          <span>{{ formData.alarmTime }}</span>
        </div>
      </div>
    </section>

    <template #footer>
      <div class="text-right">
        <el-button type="primary" @click="submit" :disabled="formData.alarmStatus!==0">
          告警处理
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
// import { getBaseUserList, } from '../../ts/index'

//
// const props = defineProps({
//   type: { type: Number, default: () => 0 },
// })
const emits = defineEmits(['toDealAlarm'])
const curId = ref<any>(null)
//
const dialogVisible = ref(false)
const formData = ref<any>({
  alarmContent: '',
  equipmentName: '',
  alarmTypeStr: '',
  alarmDetail: '',
  alarmLevelName: '',
  installLocation: '',
  alarmTime: ''
})

const submit = async () => {
  emits('toDealAlarm', 1,curId.value)
  dialogVisible.value = false
}
const handleClose = () => {
  dialogVisible.value = false
}

defineExpose({
  open: (rowData) => {
    // nextTick(() => {
    // })
    formData.value = rowData
    curId.value = rowData.id
    dialogVisible.value = true
  },
})
</script>

<style lang="scss" scoped>
:deep(.el-dialog__body) {
  height: 600px;
}

.content {
  div {
    font-size: 16px;
    line-height: 50px;

    div {
      display: flex;

      span:nth-child(2) {
        width: 200px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        text-align: left;
      }
    }
  }

  span {
    display: inline-block;
    text-align: right;
  }
}
</style>
