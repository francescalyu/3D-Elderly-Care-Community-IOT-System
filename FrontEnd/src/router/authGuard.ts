/**
 * vue router define
 * @author emiila
 * 
**/


import { Router } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { LocalStorageService } from '@/utils/storage'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css' //
import { usePermissionStore } from '@/stores/permission'

export function createAuthGuard(router: Router) {
  // 白名单
  const whiteList = ['/login']

  // 路由拦截
  router.beforeEach(async (to, from, next) => {
    NProgress.start()

    // 设置标题

    const userStore = useUserStore()
    const permissionStore = usePermissionStore()
    const token = LocalStorageService.get('token')

    if (token) {
      if (to.path == '/login') {
        next('/')
        NProgress.done()
      } else {
        const { user, roles, menu } = userStore
        const hasUser = user && Object.keys(user).length > 0
        const hasMenu = menu && menu.length > 0
        const hasRoles = roles && roles.length > 0

        if (hasUser && hasMenu && hasRoles) {
          next()
        } else {
          try {
            // 获取用户信息
            await userStore.getUserInfo().then(async () => {
              // 动态生成路由
              const menu = userStore.menu
              const accessRoutes = await permissionStore.generateRoutes(menu)

              accessRoutes.forEach((route) => {
                router.addRoute(route);
              });

              next({ ...to, replace: true })
            })
          } catch (error: any) {
            await userStore.resetToken()
            console.log(error, 'err')
            ElMessage.error(error)
            next(`/login`)
            NProgress.done()
          }
        }
      }
    } else {
      if (whiteList.includes(to.path)) {
        next()
      } else {
        next(`/login`)
      }
      NProgress.done()
    }
  })

  router.afterEach(() => {
    NProgress.done()
  })

  router.onError((error) => {
    console.log(error, '路由错误:')

    ElMessage.error(error)
    NProgress.done()
  })
}
