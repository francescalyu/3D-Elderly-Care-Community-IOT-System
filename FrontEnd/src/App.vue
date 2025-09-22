<template>
  <router-view />
  <!-- <RouterView v-if="isRouterActive" /> -->
</template>

<script setup lang="ts">
import { RouterView } from "vue-router";

const isRouterActive = ref(true);
provide("reload", () => {
  isRouterActive.value = false;
  nextTick(() => {
    isRouterActive.value = true;
  });
});
onMounted(() => {
  // 禁止用户缩放，禁用鼠标滚轮
  window.addEventListener(
    "mousewheel",
    (event: any) => {
      if (event.ctrlKey === true || event.metaKey) event.preventDefault();
    },
    { passive: false }
  );
});
</script>

<style lang="scss">
.topTitle {
  display: inline-block;
  font-size: 1.8vh;
  width: 4vw;
  color: #028888;
  font-weight: 600;
  position: relative;
  text-align: center;
  span {
    display: block;
    width: 4vw;
    height: 4px;
    background: linear-gradient(
      to right,
      rgba(2, 136, 136, 0.5),
      rgba(2, 136, 136, 0.4),
      rgba(2, 136, 136, 0.2),
      rgba(2, 136, 136, 0.1)
    );
    position: absolute;
    bottom: 3px;
  }
}

.topTitle_group {
  display: inline-block;
  margin-left: 20px;

  .el-radio {
    margin-right: 15px;
  }
}
.topTitle_group1 {
  position: relative;
  .el-radio-group {
    position: absolute;
    right: 0px;
    top: -25px;
  }
}
</style>
