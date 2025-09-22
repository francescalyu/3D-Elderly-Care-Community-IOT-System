<!-- 左侧菜单 -->
<template>
  <!-- <div class="curTitle text-center">{{ curTitle }}</div> -->
  <el-menu
    active-text-color="#19A4EC"
    background-color="transparent"
    class="left-menu"
    :unique-opened="true"
    text-color="#fff"
    router
    :default-active="defaultActiveRouteName"
    @open="handleOpen"
    @close="handleClose"
  >
    <menu-item :data-menu="menuData"> </menu-item>
  </el-menu>
</template>

<script setup lang="ts">
import { LocalStorageService } from "@/utils/storage";
import menuItem from "./menuItem.vue";
import { Dashboards, smartEnergyCarbon, smartMaintenance, smartSecurity, parkOperation, systemRoutes } from "@/router/pages";
// import { useUserStore } from "@/stores/user";
// import { useRouter } from 'vue-router';
//
interface TopMenuData {
  name: string;
  [key: string]: any;
}

const props = defineProps<{
  topMenuData: TopMenuData;
}>();
//
// const userStore = useUserStore();
// console.log("userStore: ", userStore);
const route = useRoute();
const defaultActiveRouteName = ref<any>('');
// const router = useRouter()
const curTitle = ref("首页");
// const activeIndex = ref('')
const menuData = ref<any>([]);
const topMenuClick = ref<any>();
// const curIndex = ref('')
// 仪表盘
const parkIntelligentControlRouter = Dashboards;
// 告警
const smartMaintenanceRouter = smartEnergyCarbon;
// 策略
const smartEnergyCarbonRouter =smartMaintenance;
// 关于
const smartSecurityRouter = smartSecurity;

//
const doLeftMenu = (val) => {
  defaultActiveRouteName.value = val.path
  console.log("val: ", val);
  if (val.routerName === "仪 表 盘") {
    menuData.value = parkIntelligentControlRouter;
  }
  if (val.routerName === "告 警") {
    menuData.value = smartMaintenanceRouter;
  }
  if (val.routerName === "策 略") {
    menuData.value = smartEnergyCarbonRouter;
  }
  if (val.routerName === "关 于") {
    menuData.value = smartSecurityRouter;
  }
};
const handleOpen = (index, indexPath) => {
  console.log(index, indexPath);
  LocalStorageService.set("leftChildrenMenuClick", indexPath[0]);
};

const handleClose = (index, indexPath) => {
  console.log(index, indexPath);
  LocalStorageService.set("leftChildrenMenuClick", '');
};
watch(
  () => props.topMenuData,
  (val) => {
    doLeftMenu(val);
  },
  { immediate: true, deep: true }
);
//
onMounted(() => {
  const curRouter = LocalStorageService.get("topMenuClick");
  topMenuClick.value = curRouter;
  doLeftMenu(curRouter)
  defaultActiveRouteName.value = LocalStorageService.get("leftMenuClick");
});

</script>

<style lang="scss" scoped>
.el-menu {
  border: none;
}

.curTitle {
  padding-top: 10px;
  font-size: 28px;
  font-family: SourceHanSansCN;
  font-weight: bold;
  color: #ffffff;
  line-height: 58px;
  background: linear-gradient(0deg, #0073e6 0%, #f2fdff 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

:deep() {
  .el-menu--vertical:not(.el-menu--collapse):not(.el-menu--popup-container)
    .el-menu-item {
    padding-left: 0;
  }
}
</style>
