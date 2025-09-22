<!---登录页面-->
<template>
  <section class="login">
    <div class="login-form w-[300px] h-[350px]">
      <div class="title">xx智慧园区广利平台</div>
      <el-form label-width="80px" :model="formData">
        <el-form-item label="用户名">
          <el-input v-model="formData.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <template #label>
            <span>{{ "密" + `&nbsp;&nbsp;&nbsp;` + "码" }}</span>
          </template>
          <el-input type="password" v-model="formData.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label=""> <el-checkbox v-model="formData.remember" @change="rememberChange">记住密码</el-checkbox> </el-form-item>
      </el-form>
      <div class="mt-[50px]">
        <el-button type="primary" class="w-full" @click="login">登录</el-button>
      </div>
    </div>
  </section>
</template>

<script lang="ts" setup>
import router from "@/router";
import { LocalStorageService } from "@/utils/storage";

//
const formData = reactive({
  username: "",
  password: "",
  remember: false,
});
const login = () => {
  if (formData.username === "" || formData.password === "") {
    ElMessage.error("请输入用户名和密码");
    return;
  } else {
    LocalStorageService.set("token", 'token123456');
    router.push({ name: "maintenanceOverview" });
  }
};
const rememberChange = (val) => {
  console.log("val: ", val);
  if (val) {
    LocalStorageService.set("remember", formData);
  } else {
    LocalStorageService.remove("remember");
  }
};
onMounted(() => {
  const remember = LocalStorageService.get("remember");
  if (remember) {
    formData.username = remember.username;
    formData.password = remember.password;
    formData.remember = true;
  }
  // if (import.meta.env.MODE === "development") {
  //   formData.username = "admin";
  //   formData.password = "123456";
  // }
});
</script>

<style lang="scss" scoped>
.login {
  width: 100vw;
  height: 100vh;
  background-color: skyblue;
  .login-form {
    position: absolute;
    top: 50%;
    right: 5%;
    transform: translate(0%, -50%);
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
    .title {
      font-size: 28px;
      text-align: center;
      margin-bottom: 20px;
      color: #333;
    }
  }
}
</style>
