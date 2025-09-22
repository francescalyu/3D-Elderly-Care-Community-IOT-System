<template>
    <el-dropdown @command="userOperate" class="user" trigger="click">
        <!-- <span class="el-dropdown-link">
            <i class="th-icon-user"></i>
            {{ getUserName }}
        </span> -->
        <span class="el-dropdown-link">
            <span class="">{{ getUserName }}</span>
            <!-- <img class="user-icon" src="@/assets/header/userIcon.png" alt="" /> -->
        </span>
        <template #dropdown>
            <el-dropdown-menu class="userConfig">
                <el-dropdown-item command="editPassword">修改密码</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
        </template>
    </el-dropdown>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/user';

const userStore = useUserStore()
const emit = defineEmits(['editPassword', 'logout'])

const getUserName = computed(() => {
    return userStore.user.userName
})

const userOperate = (type) => {
    emit(type)
}

</script>

<style lang="scss" scoped>
:deep(.el-popper.is-light .el-popper__arrow::before) {
    background: #fff !important;    
    border: none;

}
.el-dropdown {
    color: #2a3e62 !important;
}

.userConfig {
    // top: 65px !important;
    min-width: 70px !important;
}

.th-icon-user {
    // background-image: url('@/assets/header/userIcon.png');
}

.user {
    // background: url('@/assets/header/userBorder.png') no-repeat;
    background-size: 100% 100%;
    // float: right;
    height: 30px;
    line-height: 30px;
    padding-right: 0;
    font-size: 16px;
    color: white;
    // top: 20px;
    width: 107px;
    text-align: center;
    margin-right: 25px;
}

.el-dropdown-link {
    display: inline-block;
    width: 100%;
    height: 100%;
    position: relative;
}

.user-icon {
    position: absolute;
    top: -5px;
    right: -30px;
}
</style>