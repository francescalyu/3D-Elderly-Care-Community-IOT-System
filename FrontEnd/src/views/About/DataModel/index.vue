<!-- 模型 -->
<template>
  <div class="item_body">
    <!-- <el-row :gutter="24">
      <el-col :span="24" v-for="(item, index) in ModelData">
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
      <img src="@/assets/img/DataModel.png" alt="" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { getModel } from "../api/index";
const ModelData = ref<any>(null);
//架构数据
const getModelData = () => {
  getModel().then((res) => {
    ModelData.value = res;
    for (let i = 0; i < ModelData.value.length; i++) {
      let text = ModelData.value[i].author;
      text = text.replace(/\n/g, "<br>");
      setTimeout(function () {
        document.getElementById("textarea" + i).innerHTML = text;
      }, 500);
    }
  });
};
onMounted(() => {
  getModelData();
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
    // position: relative;
    img {
      // width: 100%; /* 图片宽度 */
      // height: 100%; /* 图片高度 */
    // position: relative;
    // top: -50%;
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
