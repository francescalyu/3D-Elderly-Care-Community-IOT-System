<!-- 布局页面 -->
<template>
  <el-container class="th-container" direction="vertical">
    <!-- <head-nav v-if="headerShow" class="header"></head-nav> -->
    <el-header class="header" height="80px">
      <HeadNav @topMenuClick="topMenuClick"></HeadNav>
    </el-header>
    <el-container class="th-body" style="flex-direction: initial">
      <el-aside class="th-aside" v-if="asideShow">
        <div class="aside_item">
          <!-- <div class="aside_title">{{ topMenuName }}</div> -->
          <LeftNav :topMenuData="topMenuData"></LeftNav>
        </div>
      </el-aside>
      <el-main class="th-main">
        <div class="theme-main">
          <router-view></router-view>
        </div>
      </el-main>
    </el-container>
    <AllScreenAlarm ref="AllScreenAlarmRef" />
  </el-container>
</template>

<script lang="ts" setup>
import HeadNav from "./components/headNav/headNav.vue";
import LeftNav from "./components/leftNav/leftNav.vue";
import AllScreenAlarm from "./Dialog/AllScreenAlarm.vue";
import { LocalStorageService } from "@/utils/storage";
import { useUserStore } from "@/stores/user";
import { useAlarmStore } from "@/stores/alarm";
const router = useRouter();
//
const userStore = useUserStore();
const curUseAlarmStore = useAlarmStore() as any;
const AllScreenAlarmRef = ref();
const curUserName = userStore.user.userName;
const asideShow = ref(false);
const topMenuData = ref<any>({});
const topMenuName = ref("");
// if(LocalStorageService.get('curUser')){
//   curUserName.value = LocalStorageService.get('curUser').username
// }else{
//   curUserName.value = ''
// }
//
const topMenuClick = (item: any) => {
  console.log(item);
  topMenuData.value = item;
  topMenuName.value = item.routerName;
};
//
const messageNumber = ref<any>(0);
// 普通告警ws
const wsUrl = `ws://${location.hostname}:${window.location.port}/websocket/alarm/${curUserName}`;

// 全局霸屏告警ws
// const wsUrlTopScreen = `ws://${location.hostname}:${window.location.port}/alarm/${curUserName.username}`

// 浩子调试时使用
// const wsUrlTopScreen = 'ws://192.168.2.116:4105/alarm/TOP_SCREEN/admin'
// const wsUrl = 'ws://192.168.2.152:4105/alarm/admin'
// const wsUrl = 'ws://192.168.100.13:8908/websocket/alarm/root'
//
// 初始化全屏告警ws
function initWebSocket() {
  const websocket = new WebSocket(wsUrl);
  websocket.onmessage = async (e) => {
    if (e.data !== "PONG") {
      // console.log(`接收到消息：${e.data}`)
      const data = JSON.parse(e.data);
      // console.log('data: ', data);
      // console.log('data: ', data.record)
      const type = data.type;
      if (type === "top_screen" && !data.recordIds) {
        curUseAlarmStore.ids.push(data.record.id);
        AllScreenAlarmRef?.value.open(data);
      }
      if (type === "web") {
        // 清空上次出现个同类弹窗
        const msg = data.record;
        await ElNotification.closeAll();
        ElNotification({
          title: msg.alarmTypeStr,
          dangerouslyUseHTMLString: true,
          message: `<div>告警级别：${msg.alarmLevelName}</div><div>安装位置：${msg.installLocation}</div><div>告警内容：${msg.alarmContent}</div><div>告警时间：${msg.alarmTime}</div>`,
          duration: 10000,
          position: "bottom-right",
          type: "warning",
        });
      }
      if (type === "unread_message_count") {
        messageNumber.value = data.unreadMessageCount;
      }
    }
  };
  setInterval(() => {
    if (websocket.readyState === WebSocket.OPEN) {
      websocket.send("PING");
    }
  }, 10000);
}
watch(
  () => router.currentRoute.value,
  () => {
    // console.log(router.currentRoute.value.path);
    asideShow.value = false;
    if (router.currentRoute.value.path !== "/cockpit") {
      asideShow.value = true;
    }
  }
);
//
onMounted(() => {
  let topMenuClick = LocalStorageService.get("topMenuClick");
  topMenuName.value = topMenuClick.routerName;
  // console.log(topMenuClick.path);
  asideShow.value = false;
  if (topMenuClick.path !== "/cockpit") {
    asideShow.value = true;
  }
  // initWebSocket()
});
</script>

<style lang="scss" scoped>
:deep(.el-header) {
  padding: 0 !important;
}

.th-container {
  width: 100%;
  height: 100%;
  // height: 100vh;
  overflow: hidden;
  // background-image: url('../assets/theme/background.jpg');
  // background-repeat: no-repeat;
  // background-size: 100% 100%;

  .header {
    // background: url('@/assets/theme/header_bg.png') no-repeat;
    // background: rebeccapurple;
    background-size: 100% 100%;
    padding-bottom: 10px;
    height: 60px;
  }

  .th-body {
    // background: url("@/assets/img/bg2.png") no-repeat;
    // background-size: 100% 100%;
    // background: lightgoldenrodyellow;
  }

  .th-main {
    padding: 0;
    // margin: 15px 15px 15px 0;
    overflow-y: auto;
    overflow-x: hidden;
    height: calc(100vh - 60px);
    padding: 15px 0px 15px 12%;
    width: calc(100% - 12%);
    background:#eee;
    .theme-main {
      // background: #fff;
      height: 100%;
      // padding: 30px 30px 0;
    }

    > * {
      // position: absolute;
      // top: 0;
      // left: 0px;
      width: 100%;
      height: 100%;
      // padding: 20px;
      padding-left: 15px;
      padding-right: 15px;
      padding-bottom: 0px;
      // padding-top: 70px;
      box-sizing: border-box;
      // border-bottom: 30px solid transparent;
      // border-image: url(../assets/img/main-border.png) 2 56 30 56;
      // border-image-width: 2px 56px 30px 56px;
      // border-image-outset: 0 16px 0 28px;
    }
  }

  .el-aside {
    width: 12%;
    // height: 100%;
    height: calc(100vh - 0px);
    margin: 0;
    // padding-bottom: 20px;
    box-sizing: border-box;
    // background-image: linear-gradient(to top, #162336, #194058);
    box-sizing: border-box;
    overflow: hidden;
    overflow-y: auto;
    // padding: 15px;
    position: absolute;
    z-index: 999;
    top: 0px;
    background: #028888;
    .aside_item {
      height: calc(100% - 10px);
      padding: 0px 0px;

      text-align: left;
      padding-left: 20%;
      padding-top: 60%;
      .aside_title {
        text-align: center;
        font-size: 20px;
        color: #4c79ff;
        font-weight: bold;
        padding: 20px 0px;
      }
    }
  }
}
</style>
