<template>
  <section class="th-header">
    <div class="th-title h_1">
      <div class="header-user fs30">
        <!-- <h1>{{ title }}</h1> -->
      </div>
    </div>
    <!--topMenu-->
    <div class="topMenu justify-between">
      <div
        v-for="(item, index) in topMenuArr"
        @click="toRouterPush(item, index)"
        class="tabs_item"
        :class="act == index ? 'actStyle' : ''"
      >
        <span> {{ item.routerName }}</span>
      </div>
    </div>
    <div class="th-header-user flex items-center user_item">
      <change-station style="display: block" />
      <img
        class="user-icon tab_item_icon1"
        title="数字孪生"
        src="@/assets/img/0dp.png"
        alt=""
      />
      <img
        class="user-icon tab_item_icon2"
        title="设置"
        src="@/assets/img/0sz.png"
        alt=""
      />
      <img
        class="user-icon tab_item_icon3"
        title="用户信息"
        src="@/assets/img/tx.png"
        alt=""
      />
      <!-- <warn-info :data-number="warnNumber" :data-msg="warnNotify" @click="goJump({ name: 'alarmManagement' })" /> -->

      <user-info @logout="logout" @editPassword="editPassword" />
    </div>

    <!--修改密码-->
    <model-edit-password
      :data-flag="modelEdit.flag"
      @close="modelEditPasswordClose"
      @submit="modelEditPasswordSubmit"
    ></model-edit-password>
    <!--退出登录-->
    <!-- <model-logout :data-flag="logoutModel.flag" @close="modelLogoutClose"
            @submit="modelLogoutSubmit"></model-logout> -->
  </section>
</template>

<script lang="ts" setup>
import { LocalStorageService } from "@/utils/storage";
import changeStation from "./components/changeStation.vue";
import userInfo from "./components/userInfo.vue";
// import selTheme from './components/selTheme.vue';
// import warnInfo from './components/warnInfo.vue';
import modelEditPassword from "./model/editPassword.vue";
// import modelLogout from './model/logout.vue'
import { useRouter } from "vue-router";
// import logo from '@/assets/theme/LOGO.png'
import { useUserStore } from "@/stores/user";
// import { updatePassword } from '@/api/user'
// import { getSystemLogoNew } from '@/api/common';
//
const emit = defineEmits(["topMenuClick"]);
const router = useRouter();
const userStore = useUserStore();
const title = ref("小 洪 山 科 学 城 智 慧 园 区");
// const img = reactive({
//     logo: logo
// })
// const warnNumber = ref(0)
// const warnNotify = ref(null)
// const warnWebSocket = ref(null)
const act = ref(0);
const modelEdit = reactive({
  flag: false,
});
const topMenuArr = ref([
  { name: "energy", path: "/energy", routerName: "仪 表 盘" },
  {
    name: "alarmPage",
    path: "/alarmPage",
    routerName: "告 警",
  },
  { name: "alarmStrategy", path: "/alarmStrategy", routerName: "策 略" },
  { name: "smartEnergyCarbon1", path: "/smartEnergyCarbon1", routerName: "关 于" },
  // { name: "maintenanceOverview", path: "/maintenanceOverview", routerName: "智慧运维" },
  // { name: "parkOperation1", path: "/parkOperation1", routerName: "园区运营" },
]);
//
const toRouterPush = (item, index) => {
  console.log("item: ", item);
  act.value = index;
  router.push({ name: item.name });
  emit("topMenuClick", item);
  LocalStorageService.set("topMenuClick", item);
  LocalStorageService.set("leftMenuClick", topMenuArr.value[index].path);
};
onMounted(() => {
  let topMenuClick = LocalStorageService.get("topMenuClick");
  console.log("topMenuArr: ", topMenuArr.value, topMenuClick);
  if (topMenuClick.path == "/cockpit") {
    act.value = 0;
  } else {
    topMenuArr.value.forEach((item, index) => {
      console.log("item: ", item);
      if (item.path == topMenuClick.path) {
        act.value = index;
      }
    });
  }
  let path;
  if (import.meta.env.MODE === "development") {
    path = import.meta.env.VITE_GLOB_API_URL + "/minio";
  } else {
    path = window.location.origin + "/minio";
  }

  // getSystemLogoNew().then(res => {
  //     if (res) {
  //         title.value = res.data.clientTitle ? res.data.clientTitle : '环境智能物联机电交互控制基础平台3.0基线版'
  //         img.logo = path + res.data.assembleLogUrl || logo;
  //     };
  // })
});

// 修改密码-弹框显示/关闭
const editPassword = () => {
  modelEdit.flag = true;
};
const modelEditPasswordClose = () => {
  modelEdit.flag = false;
};

// 修改密码-弹框提交
const modelEditPasswordSubmit = (val: string) => {
  const data = { userName: userStore.user.username, password: val };
  console.log("data: ", data);
  modelEdit.flag = false;
  // updatePassword(data).then((res: any) => {
  //     ElMessage.success('密码修改成功！')
  // });
};

// 跳驾驶舱
// const toCockpit = () => {
//     router.push({ name: 'cockpit' })
// }
// 跳数据孪生
// const goToDT = () => {
//     router.push({ name: 'digitalTwin' })
// }
// 跳系统设置
const goSystemSetting = () => {
  act.value = null;
  router.push({ name: 'logOnUser' })
  let goSystemSettingData = {
    name: "userManagement",
    path: "/userManagement",
    redirect: '/logOnUser',
    routerName: "系统设置",
  }
  emit("topMenuClick", goSystemSettingData);
  LocalStorageService.set("topMenuClick", goSystemSettingData);
  LocalStorageService.set("leftMenuClick", '/logOnUser');
  LocalStorageService.set("leftChildrenMenuClick", '/userManagement');
}
// 注销
const logout = () => {
  // logoutModel.flag = true
  ElMessageBox.confirm("是否确定退出?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      modelLogoutSubmit();
    })
    .catch(() => {});
};

// 注销-弹框提交
const modelLogoutSubmit = () => {
  // store.dispatch('user/logout').then(() => {
  //     router.push({ name: 'login' })
  // })
  userStore.logout().then(() => {
    router.push({ name: "login" });
  });
};
</script>

<style lang="scss" scoped>
.th-header {
  // display: flex;
  // align-items: center;
  // justify-content: space-between;
  width: 100%;
  height: 60px;
  line-height: 60px;
  background: #028888;
  // background-image: url("../../../assets/img/bg0.png");
  // background-size: 100% 100%;
  // background-repeat: no-repeat;
  // background-position: center;
  // background: #fff;
  line-height: 100px;
  .h_1 {
    display: inline-block;
    width: 12%;
    height: 100%;
    
    .th-title {
      width: 100%;
      display: flex;
      align-items: center;
      height: 100%;
      > div {
        // float: left;
      }
    }
  }

  .header-user {
    margin-left: 30px;
    line-height: 80px;
    color: #295ffd;
    float: left;
    padding-bottom: 20px;
    > h1 {
      margin: 0;
      font-size: 36px;
      font-family: SourceHanSansCN;
      font-weight: bold;
      font-style: italic;
      color: #295ffd;
      letter-spacing: 2px;
    }
  }

  > .th-header-user {
    float: right;
    padding-right: 28px;
    line-height: 80px;
    font-size: 16px;

    > * {
      margin-left: 40px;
      cursor: pointer;
    }
  }
  .topMenu {
    /* width: 100%; */
    height: 60px;
    display: inline-block;
    width: 70%;
    line-height: 60px;
    position: relative;
    top: -22px;
    padding-left: 50px;
    border-radius: 26px 0px 0px 0px;
    background: #fff;
    .tabs_item {
      display: inline-block;
      width: 10%;
      text-align: center;
      height: 100%;
      line-height: 60px;
      // background-image: url("../../../assets/img/tab.png");
      // background-size: 100% 100%;
      // background-repeat: no-repeat;
      // background-position: center;
      color: #333;
      transform: skew(-10deg);
      font-size: 1.5rem;
      font-weight: 700;
      cursor: pointer;
      span {
        position: relative;
        right: 10px;
      }
    }
    .actStyle {
      // background-image: url("../../../assets/img/tab1.png");
      // background-size: 100% 100%;
      // background-repeat: no-repeat;
      // background-position: center;
      font-weight: 700;
      color: #028888;
      border-bottom: 3px solid;
      line-height: 61px;
    }
  }
  .user_item {
    width: 18%;
    height: 60px;
    background: #fff;
    .tab_item_icon1 {
      cursor: pointer;
      display: inline-block;
      margin-left: 10%;
      width: 35px;
      height: 35px;
      background-image: url(./img/0dp.png);
      background-size: 100% 100%;
      background-repeat: no-repeat;
      background-position: center;
    }
    .tab_item_icon2 {
      cursor: pointer;
      display: inline-block;
      margin-left: 10%;
      width: 35px;
      height: 35px;
      background-image: url(./img/0sz.png);
      background-size: 100% 100%;
      background-repeat: no-repeat;
      background-position: center;
    }
    .tab_item_icon3 {
      cursor: pointer;
      display: inline-block;
      margin-left: 10%;
      width: 38px;
      height: 38px;
      background-image: url(./img/0sz.png);
      background-size: 100% 100%;
      background-repeat: no-repeat;
      background-position: center;
    }
  }
}
@media (min-width: 1280px) and (max-width: 1440px) {
  h1 {
    font-size: 29px !important;
  }
}
</style>
