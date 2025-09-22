<template>
  <el-dialog title="修改密码" width="30%" :model-value="props.dataFlag" @close="close" :close-on-click-modal="false">
    <el-form :model="formData" :rules="rules" :label-position="'right'" label-width="80px" ref="form">
      <!-- 修改密码 -->
      <el-form-item label="原密码" prop="originalPassword">
        <el-input v-model="formData.originalPassword" placeholder="请输入密码" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input v-model="formData.password" placeholder="请输入密码" show-password></el-input>
      </el-form-item>
      <el-form-item label="重复密码" prop="checkPassword">
        <el-input v-model="formData.checkPassword" placeholder="请再次输入密码" show-password></el-input>
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button class="default_btn_2" type="primary" @click="modalCancel">取 消</el-button>
      <el-button class="default_btn_2" type="primary" @click="submit">确 定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import md5 from 'js-md5';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();

const props = defineProps({
  dataFlag: {
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['close', 'submit']);

const formData = ref({
  password: '',
  checkPassword: '',
  originalPassword: '',
});

const userPassword = ref('');

const form = ref<any>(null);

const oriValidatePass = (rule: any, value: string, callback: (error?: Error) => void) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else if (value.length < 6) {
    callback(new Error('密码长度不小于6位'));
  } else if (md5(value) !== userPassword.value) {
    callback(new Error('原密码不正确'));
  } else {
    callback();
  }
};

const validatePass = (rule: any, value: string, callback: (error?: Error) => void) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else if (value.length < 6) {
    callback(new Error('密码长度不小于6位'));
  } else {
    if (formData.value.checkPassword !== '') {
      form.value?.validateField('checkPassword');
    }
    callback();
  }
};

const validatePass2 = (rule: any, value: string, callback: (error?: Error) => void) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== formData.value.password) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};

const rules = ref({
  originalPassword: [{ validator: oriValidatePass, trigger: 'blur' }],
  password: [{ validator: validatePass, trigger: 'blur' }],
  checkPassword: [{ validator: validatePass2, trigger: 'blur' }],
});

const init = () => {
  userPassword.value = userStore.user.passWord;
  // console.log(userPassword.value, 'pass');

};

const close = () => {
  formData.value.originalPassword = '';
  formData.value.password = '';
  formData.value.checkPassword = '';
  emit('close');
};

const modalCancel = () => {
  close();
  form.value?.resetFields();
};

const submit = () => {
  form.value?.validate((valid: boolean) => {
    if (valid) {
      emit('submit', formData.value.password);
    }
  });
};

init();

</script>

<style></style>
