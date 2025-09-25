<!-- 作者 -->
<template>
  <div class="item_body">
    <el-row :gutter="24">
      <el-col :span="24" v-for="(item, index) in AuthorData">
        <el-card style="max-width: 100%" class="item_card">
          <div class="img_item">
            <img :src="item.picUrl" style="width: 100%" />
          </div>
          <div class="text_item">
            <div class="name_item">{{item.name}}</div>
            <text class="input_item" rows="" cols="" :id="'textarea' + index"></text>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { getAuthor } from "../api/index";
import  img1  from "@/views/About/img/XUEYAN LYU.jpg";
import  img2  from "@/views/About/img/Jiaxin Yang.jpg";
import  img3  from "@/views/About/img/ZhiWen Xiao.jpg";
import  img4  from "@/views/About/img/JiaYing Wei.jpg";
const AuthorData = ref<any>([
  {
    picUrl: img1,
    name: "XUEYAN LYU (Francesca)",
    author:
      "Act as the Team Leader , Responsible for the selection and installation of smart devices, building the IoT cloud platform, and developing IoT Java Spring backend code.MySQL database modeling, UI/UX design for the S3eccis.site website, and 3D model creation.",
  },
  {
    picUrl: img2,
    name: "Jiaxin Yang (Emilla)",
    author:
      "Responsible for the installation and integration of smart devices, development of S3eccis.site API interfaces, Vue frontend UI development,3D model presentation with Tres.js, and research on open-source code frameworks.",
  },
  {
    picUrl: img3,
    name: "ZhiWen Xiao (Sean)",
    author:
      "As the IoT hardware engineer and DevOps for the project, responsible for the selection, procurement, on-site installation, and configuration of smart devices, as well as IoT MQTT configuration and cloud connectivity.Manages Tencent Cloud ECS and network configuration, uses Jenkins and Docker for code packaging and deployment, and handles WEB website registration, maintenance, and certificate management.",
  },
  {
    picUrl: img4,
    name: "JiaYing Wei (Ivanka)",
    author:
      "Responsible for the project's UI and UX design, WEB testing and debugging. Utilizes 3D Max to complete 3D modeling and icon creation, and establishes the connection between the digital twin and devices.Completes various user documentation for the project, creates presentation PPTs, provides on-site support and guidance for elderly users. Handles project budget establishment and estimation, as well as equipment procurement and logistics management.",
  },
]);
//作者数据
const getAuthorData = () => {
  // getAuthor().then((res) => {
  // AuthorData.value = res;
  console.log(AuthorData.value)
  for (let i = 0; i < AuthorData.value.length; i++) {
    let text = AuthorData.value[i].author;
    text = text.replace(/\n/g, ",");
    setTimeout(function () {
      document.getElementById("textarea" + i).innerHTML = text;
    }, 500);
  }
  // });
};
onMounted(() => {
  getAuthorData();
});
</script>
<style lang="scss" scoped>
.item_body {
  width: 100%;
  height: 100%;
  el-card {
    width: 100%;
  }
  .item_card {
    margin-bottom: 10px;
    .img_item {
      width: 18%;
      display: inline-block;
      vertical-align: top;
      border: 1px solid rgba(94, 186, 189, 0.5);
      border-radius: 12px;
      padding: 1%;
      margin-right: 1vh;
      
    }
    .name_item{
      font-size: 2.4vh;
      font-weight: 800;
      margin-bottom: 1vh;
      transform: skew(-10deg);
      
    }
    .text_item {
      width: 80%;
      display: inline-block;
      vertical-align: top;
      min-height: 100px;
      height: 200px;
      overflow: auto;
      margin-left: 10px;
      overflow: hidden;
      .input_item {
        height: 100%;
        width: 100%;
        font-size: 2vh;
        color: #666;
      }
    }
  }
}
</style>
