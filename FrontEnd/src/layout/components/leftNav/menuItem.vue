<template>
   <div class="leftMenu" v-for="item in props.dataMenu" :key="item.menuId">
    <!--含子目录的情况-->
    <el-sub-menu v-if="item.children && item.children.length && item.hidden !== 1" :index="item.path"
      :key="item.menuId">
      <template #title>
        <div class="flex items-center">
          <span v-if="item.meta && item.path == LocalStorageService.get('leftChildrenMenuClick')">
            <img :src="item.meta.iconSelected" alt="">
          </span>
          <span v-else>
            <img v-if="item.meta" :src="item.meta.icon" alt="">
          </span>
          <span class="ml-[10px]">{{ item.meta ? item.meta.title : item.menuName }}</span>
        </div>
      </template>
      <menu-item :data-menu="item.children" @getCurMenuName="getCurMenuName"></menu-item>
    </el-sub-menu>
    <!--不含子目录-->
    <el-menu-item v-else-if="!(item.children && item.children.length > 0) && item.hidden !== 1" :index="item.path"
      :key="`${item.menuId}-menu-item`" class="single-title">
      <div class="edit_menu" @click="getMenuName(item)">
        <div>
          <span class="text-[16px] flex items-center" :key-id="item.menuId">
            <span v-if="item.meta && item.path == LocalStorageService.get('leftMenuClick')">
              <img :src="item.meta.iconSelected" alt="">
            </span>
            <span v-else>
              <img v-if="item.meta" :src="item.meta.icon" alt="">
            </span>
            <span class="hover_text ml-[10px] w-[140px] truncate ... hover:text-clip">
              {{ hasChildrenMenuPath }}{{ item.meta ? item.meta.title : item.menuName }}</span>
          </span>
        </div>
      </div>
    </el-menu-item>
  </div>
</template>

<script setup lang="ts">
// import router from '@/router';
import { LocalStorageService } from "@/utils/storage";
// import { useUserStore } from '@/stores/user';
//icon

interface MenuItem {
  menuId: string;
  path: string;
  menuName: string;
  hidden?: number;
  meta?: { title: string; icon?: string; iconSelected?: string };
  children?: MenuItem[];
  systemId?: string;
}

const props = defineProps<{
  dataMenu: MenuItem[];
}>();

const emit = defineEmits(["getCurMenuName"]);
// const userStore = useUserStore()

// const curUser = computed(() => {
//   return userStore.user.userName
// })
const topMenuClick = ref("");
const hasChildrenMenuPath = ref<any>('')
//
const getCurMenuName = (item) => {
  console.log(item)
  getMenuName(item)
};

const getMenuName = (item) => {
  if (item.systemId) {
    LocalStorageService.set("systemId", item.systemId);
  }
  LocalStorageService.set("leftMenuClick", item.path);
  hasChildrenMenuPath.value = '';
};
watch(() => props.dataMenu, (val) => {
  topMenuClick.value = LocalStorageService.get("topMenuClick").path;
}, { immediate: true, deep: true })

onMounted(() => {
  // console.log(props.dataMenu);
});
// const getIcon = (data) => {
//     for (let i = 0; i < imgArr.value.length; i++) {
//         if (data === imgArr.value[i].name) {
//             return imgArr.value[i].value
//         }
//     }
// }
</script>

<style lang="scss" scoped>
// :deep(.leftMenu .el-menu-item.single-title) {
// padding-left: 45px !important;
// }

:deep(.is-active) {
  background-color: #eee;
  color: #028888;
  // background-image: url("./img/leftSelect.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-position: 100% 100%;
}

:deep(.leftMenu .el-menu-item.single-title) {
  &:hover {
    background-image: none;
    &:before {
      content: "";
      position: absolute;
      top: 50%;
      width: 5px;
      height: 2px;
      // background: #67c7ff;
      left: 20px;
    }
  }
}

:deep(.el-sub-menu__title),
.single-title {
  font-size: 18px !important;
  border-radius: 30px 0px 0px 30px;
  color: #fff;
  margin: 10px 0px;
  height: 65px;
  &:hover {
    background-color: #eee;
    color: #028888;
    // background-image: url("./img/leftSelect.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    background-position: 100% 100%;
    // width: 300px;
  }
}

:deep(.el-menu-item.is-active) {
  span {
    color: #028888;
  }
}

.edit_menu {
  display: flex;
  justify-content: space-between;
  // width: 160px;

  &:hover {
    .nav_stacked {
      opacity: 1;
    }
  }
}

.hover_text {
  &:hover {
    overflow: visible;
  }
}

.nav_stacked {
  float: right;
  opacity: 0;
}

:deep(.el-menu-item) {
  font-size: 18px;

  // &:hover {
  // background-color: transparent;
  // background-image: url("./img/leftSelect.png");
  // background-repeat: no-repeat;
  // background-size: 100% 100%;
  // background-position: 100% 100%;
  // width: 300px;
  // }
}

// 2层目录情况
:deep() {
  .el-sub-menu.is-opened {
    .single-title {
      // padding-left: 30px;

      font-size: 18px !important;
    }
  }

  .el-sub-menu.is-active.is-opened {
    .el-sub-menu__title {
      // background-color: #4c79ff;
      // background-image: url("./img/leftSelect.png");
      background-repeat: no-repeat;
      background-size: 100% 100%;
      background-position: 100% 100%;
      // color: #fff;
    }

    .single-title {
      background-image: none;
      // padding-left: 30px;

      span {
        &:hover {
          color: #fff;
        }
      }

      &:hover {
        &:before {
          content: "";
          position: absolute;
          top: 50%;
          width: 5px;
          height: 2px;
          background: #67c7ff;
          left: 30px;
        }
      }
    }

    //
    .el-menu-item.is-active.single-title:before {
      content: "";
      position: absolute;
      top: 50%;
      width: 5px;
      height: 2px;
      background: #67c7ff;
      left: 30px;
    }
  }

  // 第二层未选中情况
  // 第二层
  ul {
    .el-sub-menu {
      font-size: 18px !important;

      .el-sub-menu__title {
        &:hover {
          background-image: none;

          &:before {
            content: "";
            position: absolute;
            top: 50%;
            width: 5px;
            height: 2px;
            background: #67c7ff;
            left: 30px;
          }
        }
      }
    }
  }
}

// 3层目录情况
:deep() {
  // 默认情况
  .el-sub-menu {
    .el-sub-menu__title {
      font-size: 18px !important;
      // padding-left: 35px !important;
    }

    // 第二层
    ul {
      .el-menu-item.single-title {
        background-image: none;

        &:hover {
          background-image: none;

          &:before {
            display: none;
          }
        }
      }

      span {
        font-size: 18px !important;
      }

      // 未选中第三层
      ul {
        .el-menu-item.single-title {
          span {
            color: #fff;
          }

          &:hover {
            background-image: none;

            span {
              color: #fff !important;
            }
          }
        }

        .el-sub-menu {
          span {
            font-size: 18px !important;
          }
        }
      }
    }
  }

  // 展开情况
  .el-sub-menu.is-active.is-opened {
    .el-sub-menu__title {
      // background-color: #4c79ff;
      // background-image: url("./img/leftSelect.png");
      background-repeat: no-repeat;
      background-size: 100% 100%;
      background-position: 100% 100%;
      // color: #fff;
    }

    // 第二层
    ul {
      .el-sub-menu__title {
        background-image: none;

        // span {
        // font-size: 14px !important;
        // }
      }

      .el-sub-menu.is-active.is-opened {
        .el-sub-menu__title {
          background-image: none;

          &:before {
            content: "";
            position: absolute;
            top: 50%;
            width: 5px;
            height: 2px;
            background: #67c7ff;
            left: 30px;
          }

          &:hover {
            &:before {
              content: "";
              position: absolute;
              top: 50%;
              width: 5px;
              height: 2px;
              background: #67c7ff;
              left: 30px;
            }
          }

          // span {
          // font-size: 14px !important;
          // }
        }
      }

      // 第三层
      ul {
        .el-menu-item.is-active.single-title {
          &:before {
            display: none;
          }
        }

        .single-title {
          background-image: none;
          // padding-left: 30px;

          span {
            &:hover {
              color: #fff;
            }
          }

          &:hover {
            &:before {
              display: none;
            }
          }
        }

        .single-title.is-active {
          span {
            color: #fff;
          }
        }
      }
    }

    // .single-title {
    //   background-image: none;
    //   span {
    //     font-size: 14px !important;
    //   }
    // }
  }
}
</style>
