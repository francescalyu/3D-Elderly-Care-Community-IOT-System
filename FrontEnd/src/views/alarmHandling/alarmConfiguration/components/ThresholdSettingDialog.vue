<!-- 阈值设置Dialog -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="1280px" :draggable="true" :close-on-click-modal="false"
    @close="handleClose" append-to-body>
    <template #header>
      <span class="el-dialog__title">{{ dialogTitle }}</span>
    </template>
    <el-form ref="formAlarm" :model="formData.rules" class="p-3" inline>
      <div v-for="(item, index) in formData.rules" :key="index" class="w-full">
        <el-form-item label="告警级别">
          <el-select v-model="item.alarmLevelId" style="width: 150px">
            <el-option v-for="item in alarmLevelList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="阈值范围">
          <el-input v-model="item.minValue" placeholder="最小值" style="width: 100px" />
          <span class="ml-[25px]">~</span>
        </el-form-item>

        <el-form-item label="">
          <el-input v-model="item.maxValue" placeholder="最大值" style="width: 100px" />
        </el-form-item>
        <el-form-item label="告警阈值类型">
          <el-select v-model="item.type" style="width: 150px">
            <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="死区">
          <el-input v-model="item.deadZone" placeholder="死区" style="width: 100px" />
          <span class="ml-[10px] item-plus" @click="addItem(index)">+</span>
          <span class="ml-[10px] item-del" @click="delItem(index)">删除</span>
        </el-form-item>
      </div>
    </el-form>

    <template #footer>
      <div class="text-center">
        <el-button type="primary" class="btn_cancel" @click="handleClose">
          取 消
        </el-button>
        <el-button type="primary" @click="submit"> 确 定 </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { getAlarmLevelList } from "../../ts/index";
//
const props = defineProps({
  type: { type: Number, default: () => 0 },
});
const emits = defineEmits(["settingSubmit"]);

//
const dialogVisible = ref(false);
const dialogTitle = "阈值设置";
const alarmLevelList = ref<any[]>([]);
const typeList = ref<any[]>([
  { name: "阈值内告警", id: 1 },
  { name: "阈值外告警", id: 0 },
]);
const formAlarm = ref();
const formData = ref<any>({
  rules: [{ alarmLevelId: "", minValue: "", maxValue: "", type: "", deadZone: "" }],
});
const alarmTypeList = ref<any[]>([]);

function addItem(index) {
  formData.value.rules.push({
    alarmLevelId: "",
    minValue: "",
    maxValue: "",
    type: "",
    deadZone: "",
  });
}
function delItem(index) {
  if (index !== 0) {
    formData.value.rules.splice(index, 1);
  }
}
const submit = async () => {
  await formAlarm.value?.validate();
  for (let i = 0; i < formData.value.rules.length; i++) {
    for (const j in formData.value.rules[i]) {
      if (formData.value.rules[i][j] === "") {
        ElMessage.warning("存在未填项");
        return
      }
    }
  }
  dialogVisible.value = false;
  emits("settingSubmit", formData.value.rules);
};
const handleClose = () => {
  // formData.alarmType = "";
  dialogVisible.value = false;
};

defineExpose({
  open: (data) => {
    dialogVisible.value = true;
  getAlarmLevelList({}).then((res) => {
      alarmLevelList.value = res.data;
    });
    if (data.length > 0) {
      formData.value.rules = data
    } else {
      formData.value.rules.forEach((e) => {
        e.alarmLevelId = ''
        e.minValue = ''
        e.maxValue = ''
        e.type = ''
        e.deadZone = ''
      });
    }
  },
});
</script>

<style lang="scss" scoped>
:deep(.el-dialog__body) {
  height: 600px;
}

:deep(.el-form-item__content) {
  width: 100%;
  margin-left: 0 !important;
}

.item-plus {
  &:hover {
    cursor: pointer;
  }
}

.item-del {
  color: red;

  &:hover {
    cursor: pointer;
  }
}
</style>
