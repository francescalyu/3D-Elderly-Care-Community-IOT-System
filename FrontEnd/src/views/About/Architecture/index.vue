<!-- 架构 -->
<template>
  <div class="item_body">
    <!-- <el-row :gutter="24">
      <el-col :span="24" v-for="(item, index) in ArchData">
        <el-card style="max-width: 100%" class="item_card">
          <div class="img_item">
            <img :src="item.picUrl" style="width: 100%" />
          </div>
          <div class="text_item">
            <text class="input_item" rows="" cols="" :id="'textarea' + index"></text>
          </div>
        </el-card>
      </el-col>
    </el-row> -->
    <div class="img_item">
      <img src="@/assets/img/item.png" alt="" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { getArch } from "../api/index";
const ArchData = ref<any>(null);
//架构数据
const getArchData = () => {
  getArch().then((res) => {
    ArchData.value = res;
    for (let i = 0; i < ArchData.value.length; i++) {
      let text = ArchData.value[i].author;
      text = text.replace(/\n/g, "<br>");
      setTimeout(function () {
        document.getElementById("textarea" + i).innerHTML = text;
      }, 500);
    }
  });
};
onMounted(() => {
  // getArchData();
});
</script>
<style lang="scss" scoped>
.item_body {
  width: 100%;
  height: 100%;
  el-card {
    width: 100%;
  }
  .img_item {
    width: 100%;
    height: 100%;
    // background-image: url("@/assets/img/item.png");
    // background-repeat: no-repeat;
    // background-size: 100% 100%;
    img {
      width: 100%; /* 图片宽度 */
  height: 100%; /* 图片高度 */
  object-fit: contain; /* 保持宽高比，同时裁剪多余的部分 */
    }
  }
  .item_card {
    margin-bottom: 10px;
    .img_item {
      width: 18%;
      display: inline-block;
      vertical-align: top;
    }
    .text_item {
      width: 80%;
      display: inline-block;
      vertical-align: top;
      min-height: 100px;
      height: 200px;
      overflow: auto;
      margin-left: 10px;
      .input_item {
        height: 100%;
        width: 100%;
      }
    }
  }
}
</style>
