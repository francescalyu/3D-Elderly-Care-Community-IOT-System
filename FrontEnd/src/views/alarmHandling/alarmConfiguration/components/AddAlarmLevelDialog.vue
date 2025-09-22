<!-- 新增告警级别 -->
<template>
  <el-dialog v-model="dialogVisible" title="" width="500px" :draggable="true" :close-on-click-modal="false"
    @close="handleClose">
    <template #header>
      <span class="el-dialog__title">{{ dialogTitle }}</span>
    </template>
    <el-form ref="formAlarm" :model="form" :rules="rules" class="p-5" label-width="110">
      <el-form-item label="告警级别名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入告警级别" />
      </el-form-item>
      <el-form-item label="级别描述" prop="alarmLevelDesc">
        <el-input v-model="form.alarmLevelDesc" placeholder="请输入级别描述" />
      </el-form-item>
      <!-- <el-form-item label="是否音效提示" prop="soundCues">
        <el-select v-model="form.soundCues">
          <el-option v-for="item in yesOrNoList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item> -->
      <el-form-item label="霸屏状态">
        <el-radio-group v-model="form.topScreen" size="">
          <el-radio-button :label="true"> 开启 </el-radio-button>
          <el-radio-button :label="false"> 关闭 </el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="音效文件" v-if="form.topScreen" prop="alarmLevelVedioUploads">
        <div>
          <el-upload ref="uploadRef" :http-request="importFile" :limit="1" :show-file-list="false" accept=".mp3,.wma"
            action="#" class="mt-2px mx-12px" :on-change="handleChange" :on-exceed="handleOnExceed"
            :on-error="uploadError">
            <XButton icon="upload"> 上传文件 </XButton>
          </el-upload>
          <transition-group v-if="fileList.length > 0 && fileList[0].name" class="file-list" name="el-fade-in" tag="ul">
            <li v-for="(file, index) in fileList" :key="file.uid || file.name" class="file-list__item flex-wrap">
              <el-link :underline="false" target="_blank" class="ml-5px w-150px" @click="downloadFile(file)">
                <span>{{ file.name }}</span>
              </el-link>
              <div class="mr-10px mb-5px w-30px">
                <el-link :underline="false" type="danger" @click="handleRemove(index)"> 删除 </el-link>
              </div>
              <br />
              <div v-if="props.type === 'edit' || props.type === 'add'">
                <audio :src="getPreviewUrl() + form.alarmLevelVedioUploads.fileUrl" controls
                  style="width: 250px; height: 30px" />
              </div>
              <!--原始代码如下列-->
              <!-- <audio :src="getPreviewUrl() + file.vedioUrl" controls style="width: 250px; height: 30px" /> -->
            </li>
          </transition-group>
        </div>
      </el-form-item>
      <el-form-item label="关闭权限" v-if="form.topScreen" prop="closeUsernames">
        <el-select ref="selRef" v-model="closeUsernames" :disabled="!form.topScreen" multiple placeholder="请选择用户"
          clearable class="w-full" filterable fit-input-width collapse-tags :max-collapse-tags="2"
          @change="namesChanges">
          <el-option v-for="item in userList" :key="item.username" :label="item.realName"
            :value="item.username"></el-option>
          <!-- :label="item.realName" :value="item.username"></el-option> -->
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="text-center">
        <el-button type="primary" class="btn_cancel" @click="handleClose"> 取 消 </el-button>
        <el-button type="primary" @click="submit"> 确 定 </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
// import { addAlarmLevel, editAlarmLevel, upLoadFile } from '@/api/alarm-strategy'
import { downloadByUrl } from "@/utils/downloadFile";
import { getPreviewUrl } from "@/utils";
import { getBaseUserList, alarmLevelAdd, alarmLevelUpdate } from "../../ts/index";
import { upLoadFile } from "@/api/file.ts";

//
const props = defineProps({
  dialogData: { type: Object, default: () => { } },
  type: { type: String, default: () => "" },
});
const emit = defineEmits(["submit"]);
//
const dialogVisible = ref(false);
const dialogTitle = computed(() => (props.type === "add" ? "新增告警级别" : "编辑告警级别"));
const formAlarm = ref();
const form = reactive<any>({
  name: "",
  alarmLevelDesc: "",
  // soundCues: 1,
  alarmLevelVedioUploads: {
    bucket: "",
    extra: "",
    fileName: "",
    fileUrl: "",
    id: "",
    object: "",
  },
  topScreen: false,
  closeUsernames: [],
  id: 0,
});
// const yesOrNoList = ref<any[]>([
//   { name: "是", value: 1 },
//   { name: "否", value: 0 },
// ]);
const userList = ref<any[]>([]);
const rules = {
  name: [{ required: true, message: "请输入告警级别名称", trigger: "blur" }],
  alarmLevelDesc: [{ required: true, message: "请输入告警描述", trigger: "blur" }],
  closeUsernames: [{ required: true, message: "关闭权限不能为空", trigger: "blur" }],
};
// 上传相关处理
const fileSize = ref<any>(0);
const fileList = ref<any[]>([]);
const uploadRef = ref();
const selRef = ref();
const curSelect = ref<any[]>([]);
const closeUsernames = ref<any[]>([]);
const userId = ref<any[]>([]);
const submit = async () => {
  await formAlarm.value?.validate();
  form.closeUsernames = closeUsernames.value;
  if (form.topScreen && form.closeUsernames.length === 0) {
    ElMessage.error("请选择用户");
    return;
  }

  if (form.topScreen && form.alarmLevelVedioUploads.fileUrl === "") {
    ElMessage.warning("请上传音频文件");
    return;
  }
  if (form.topScreen && form.alarmLevelVedioUploads && Object.keys(form.alarmLevelVedioUploads).length === 0) {
    ElMessage.warning("请上传音频文件");
    return;
  }
  console.log('form: ', form);
  if (!form.topScreen) {
    delete form.alarmLevelVedioUploads;
    delete form.closeUsernames;
    // form.closeUsernames = [];
    // form.alarmLevelVedioUploads = {
    //   bucket: "",
    //   extra: "",
    //   fileName: "",
    //   fileUrl: "",
    //   id: "",
    //   object: "",
    // };
  }
  //
  // if (form.alarmLevelVedioUploads && Object.keys(form.alarmLevelVedioUploads).length === 0) {
  //   delete form.alarmLevelVedioUploads;
  // }
  if (props.type === "add") {
    delete form.id;
    // return
    alarmLevelAdd(form).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success("新增成功");
        dialogVisible.value = false;
        emit("submit");
      } else {
        ElMessage.error(res.msg || "新增失败");
      }
    });
  } else {
    // 编辑
    if (form.topScreen && form.closeUsernames.length === 0) {
      ElMessage.error("请选择用户");
      return;
    }
    if (form.topScreen && form.alarmLevelVedioUploads.fileUrl === "") {
      ElMessage.warning("请上传音频文件");
      return;
    }
    if (!form.topScreen) {
      // delete form.alarmLevelVedioUploads;
      form.alarmLevelVedioUploads = {
        bucket: "",
        extra: "",
        fileName: "",
        fileUrl: "",
        id: "",
        object: "",
      };
    }
    // return
    alarmLevelUpdate(form).then((res: any) => {
      console.log("form: ", form);
      if (res.code === 200) {
        ElMessage.success("编辑成功");
        dialogVisible.value = false;
        emit("submit");
      } else {
        ElMessage.error(res.msg || "编辑失败");
      }
    });
  }
};
const handleClose = () => {
  for (const k in form) form[k] = "";
  form.alarmLevelVedioUploads = {
    bucket: "",
    extra: "",
    fileName: "",
    fileUrl: "",
    id: "",
    object: "",
  };
  form.topScreen = false;
  form.closeUsernames = [];
  fileList.value = [];
  closeUsernames.value = [];
  dialogVisible.value = false;
};

const handleChange = (file, files) => {
  fileList.value = files;
  fileSize.value = Number(file.size / 1024 / 1024);
};
const uploadError = (val) => {
  if (val) {
    ElMessage.error("上传失败");
  }
};
const importFile = async (op) => {
  if (fileSize.value > 10) {
    ElMessage.error("最大上文件大小为10MB");
  } else {
    upLoadFile(op.file)
      .then((res: any) => {
        if (res.msg == "OK") {
          ElMessage.success("上传成功");
          form.alarmLevelVedioUploads = res.data;
        } else {
          ElMessage.success("上传失败");
          uploadRef.value?.clearFiles();
        }
      })
      .finally(() => {
        uploadRef.value?.clearFiles();
      });
  }
};
const handleOnExceed = () => {
  ElMessage.warning("超过最大上传数量1");
};
// 下载
const downloadFile = (file) => {
  if (props.type === "edit") downloadByUrl(getPreviewUrl() + file.vedioUrl, file.vedioName);
};

// 删除
const handleRemove = (index) => {
  fileList.value.splice(index, 1);
  form.alarmLevelVedioUploads = {
    bucket: "",
    extra: "",
    fileName: "",
    fileUrl: "",
    id: "",
    object: "",
  };
};
const namesChanges = () => {
  form.closeUsernames = closeUsernames.value;
};
watch(
  () => form.topScreen,
  (val) => {
    if (!val) {
      form.closeUsernames = [];
      closeUsernames.value = [];
    }
  }
);
defineExpose({
  open: (data) => {
    console.log("data: ", data);
    if (data) {
      form.name = data.name;
      form.alarmLevelDesc = data.alarmLevelDesc;
      // form.alarmLevelVedioUploads = data.alarmLevelVedioUploads
      form.topScreen = data.topScreen;
      // form.soundCues = data.soundCues;
      if (data.closeUsers && data.closeUsers.length > 0) {
        form.closeUsernames = data.closeUsers.map((item) => item.username) || [];
        closeUsernames.value = data.closeUsers.map((item) => item.username) || [];
        userId.value = data.closeUsers.map((item) => item.userId) || [];
        form.alarmLevelVedioUploads = data.alarmLevelVedioUploads;
      }
      form.id = data.id;
      fileList.value[0] = { name: data.vedioName, vedioUrl: getPreviewUrl() + data.vedioUrl };
    } else {
      for (const k in form) form[k] = "";
      form.alarmLevelVedioUploads = {};
      form.topScreen = false;
      form.closeUsernames = [];
      fileList.value = [];
      closeUsernames.value = [];
      userId.value = [];
      curSelect.value = [];
    }
    nextTick(() => {
      getBaseUserList().then((res) => {
        userList.value = res.data;
      });
    });
    dialogVisible.value = true;
  },
});
</script>

<style lang="scss" scoped>
:deep(.el-dialog__body) {
  height: 600px;
}

:deep(.el-input__inner) {
  width: 212px;
}

.file-list {
  line-height: 2;
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;

  &__item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border: 1px solid #e4e7ed;
    padding: 0.25rem;
    transition: all 0.5s cubic-bezier(0.55, 0, 0.1, 1);
    font-size: 14px;
    color: #606266;
    box-sizing: border-box;
    border-radius: 4px;
    width: 100%;

    &:first-child {
      margin-top: 10px;
    }

    &:not(:first-child) {
      margin-top: 5px;
    }
  }
}
</style>
