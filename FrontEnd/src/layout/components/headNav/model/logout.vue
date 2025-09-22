<template>
  <el-dialog width="500px" :model-value="props.dataFlag" @close="close" :close-on-click-modal="false">
    <template #header>
      <div class="el-dialog__title">退出登录</div>
    </template>
    <span>是否确定退出?</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="modalCancel"> 取消 </el-button>
        <el-button type="primary" @click="submit"> 确认 </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">

const props = defineProps({
  dataFlag: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['close', 'submit'])

const close = () => {
  emit('close')
}

const submit = () => {
  emit('submit')
}

const modalCancel = () => {
  close()
}

</script>

<style lang="scss" scoped>
:deep(.el-dialog__body ){
  color: #fff;
}
</style>
