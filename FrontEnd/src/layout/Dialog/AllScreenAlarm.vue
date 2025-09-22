<!-- 全屏告警提示，霸屏 -->
<template>
  <el-dialog
    :model-value="visible"
    title=""
    width="1440px"
    center
    top="20vh"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :draggable="true"
    :show-close="false"
    :z-index="3000"
    @before-close="handleClose"
  >
    <template #header>
      <div class="flex justify-between w-full">
        <span text="[26px]"> {{ curTitle }}</span>
        <span text="[16px] [#DC1E32]" class="btn_x" @click="handleClose">X</span>
      </div>
    </template>
    <div text="[28px]" font="bold">
      <el-table ref="table" :data="tableData" class="customTable">
        <el-table-column label="序号" align="center" type="index" width="80" />
        <el-table-column align="center" prop="alarmContent" label="告警内容" min-width="200" />
        <el-table-column align="center" prop="alarmLevelName" label="告警级别" width="100" />
        <el-table-column align="center" prop="alarmTypeStr" label="告警类型" width="120" />
        <el-table-column align="center" prop="equipmentName" label="告警设备" width="200" />
        <!-- <el-table-column align="center" prop="equipmentLocation" label="设备区域" min-width="100" /> -->
        <el-table-column align="center" prop="installLocation" label="安装位置" min-width="120" />
        <el-table-column align="center" prop="alarmTime" label="告警时间" width="200" />
        <el-table-column align="center" prop="alarmDetail" label="告警详情" min-width="200" />
      </el-table>
    </div>
  </el-dialog>
</template>

<script lang="ts" setup>
import { Howl } from "howler";
import { closeTopScreen } from "@/views/alarmHandling/ts/index";
import { getPreviewUrl } from "@/utils";
import defaultMp3 from "../Alarm01.mp3";
import { useAlarmStore } from "@/stores/alarm";

//
const emits = defineEmits(["close"]);
// const props = defineProps({
//   alarmDataTop: {
//     type: Object,
//     default: () => { },
//   },
// })
//
const curUseAlarmStore = useAlarmStore();
const alarmStoreIds = ref<any>(curUseAlarmStore.ids);
//
const visible = ref(false);
const curTitle = ref("霸屏告警");
const tableData = ref<any[]>([]);
const curVideoData = ref();
const timer = ref();
//

// const sound = new Howl({
//   // src: (curVideoData.value && curVideoData.value[0].videoUrl) ? getPreviewUrl() + curVideoData.value[0].videoUrl : defaultMp3,
//   src: (curVideoData.value && curVideoData.value.videoUrl) ? getPreviewUrl() + curVideoData.value.videoUrl : defaultMp3,
//   loop: true,
// })
const sounds = ref<any>([]);
const handleClose = () => {
  // const ids = tableData.value.map(item => item.id)
  // if (sound.value) {
  //   console.log("点击关闭后");
  //   sound.value.stop();
  // }
  sounds.value.forEach((sound) => {
    sound.stop();
    sound.unload(); // 卸载音频资源
    sound = null; // 移除引用，允许垃圾回收
  });

  closeTopScreen([...new Set(alarmStoreIds.value)]).then((res: any) => {
    if (res && res.code === 500) {
      visible.value = true;
      ElMessage.error(res.msg);
    } else {
      clearTimeout(timer.value);
      emits("close");
      visible.value = false;
    }
  });
};
defineExpose({
  open: (data) => {
    // console.log("data: ", data);
    visible.value = true;
    tableData.value = [];
    //
    // 根据data对象里的type，判断是，更新表格数据或关闭弹窗
    curVideoData.value = data.record;
    if (curVideoData.value && curVideoData.value.soundCues === 1) {
      // sound.value = new Howl({
      //   src: curVideoData.value && curVideoData.value.videoUrl ? getPreviewUrl() + curVideoData.value.videoUrl : defaultMp3,
      //   loop: true,
      // });
      sounds.value.push(
        new Howl({
          src: curVideoData.value && curVideoData.value.videoUrl ? getPreviewUrl() + curVideoData.value.videoUrl : defaultMp3,
          loop: true,
        })
      );
      timer.value = setTimeout(() => {
        //
        sounds.value[sounds.value.length - 1].play();
      }, 500);
    } else {
      // sound.value = new Howl({
      //   src: '',
      //   loop: true,
      // })
      // sound.value.stop()
    }
    tableData.value.push(data.record);

    // if (!data.isAdd && data.recordIds && data.recordIds.length > 0) {
    //   console.log(33333333333333333);
    //   console.log('其他情况');
    //   sound.value.stop()
    //   visible.value = false
    //   emits('close')
    //   tableData.value.forEach((e, index) => {
    //     if (e.id === data.record.id) {
    //       tableData.value.splice(index, 1)
    //     }
    //   });
    // }
    // else {
    //   console.log(444444444444);
    //   tableData.value.push(data.record)
    //   // 播放声音
    //   sound.value.stop()
    //   timer.value = setTimeout(() => {
    //     sound.value.play()
    //   }, 500)
    //   visible.value = true
    // }
  },
});
onBeforeUnmount(() => {
  curUseAlarmStore.ids = []
})
</script>

<style lang="scss" scoped>
:deep(.customTable) {
  height: calc(100% - 220px) !important;
  max-height: calc(100% - 220px) !important;
  margin-bottom: 0 !important;
  // width: calc(100% - 500px)!important;
}

.des_div {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.btn_x {
  &:hover {
    cursor: pointer;
  }
}
</style>
