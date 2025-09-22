/**
 * router define
 * @author emiila
 * 
**/


import { createRouter, createWebHashHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import type { App } from 'vue'
// import layout from '@/layout/index.vue'
import { pageRoutes } from './pages'
import errorRoutes from './error'
import layout from '@/layout/index.vue'

// import { createAuthGuard } from './authGuard'

//
import Cockpit from '../views/cockpit/index.vue'
import Login from '../views/login/index.vue'
import Energy from '../views/Dashboards/energy/index.vue'
export const baseRoutes: RouteRecordRaw[] = [
  // 登录页
  {
    path: '/login',
    name: 'login',
    component: () => Login,
    meta: { title: '登录' }
  },

  // 驾驶舱
  // {
  //   path: '/cockpit',
  //   name: 'cockpit',
  //   component: () => Cockpit,
  //   meta: { title: '驾驶舱' }
  // },
  //数字孪生
  // {
  //   path: '/digitalTwin',
  //   component: () => import('@/views/digitalTwin/index.vue'),
  //   name: 'digitalTwin',
  //   // redirect: '/digitalTwin',
  //   meta: { title: '数字孪生场景' },
  // }
  // 404page为一级路由
  ...errorRoutes,
]
// 下面路由为
export const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: layout,
    redirect: '/energy',// 综合态势
    children: [
      // 首页
      {
        path: '/energy',
        name: 'energy',
        component: Energy,
        meta: { title: '仪表盘' }
      },
      ...pageRoutes,
      // 可以在这里添加其他模块的路由
    ]
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: [...baseRoutes, ...routes],
  strict: true,
  scrollBehavior: () => ({ left: 0, top: 0 })
})

export function setupRouter(app: App) {
  app.use(router)
  // 创建路由守卫
  // createRouterGuards(router)
}

export function resetRouter() {
  const newRouter = createRouter({
    history: createWebHashHistory(),
    routes
  })
  // @ts-expect-error
  router.matcher = newRouter.matcher
}

router.onError((error, to, from) => {
  console.error('路由信息:', error, to, from)
  console.error('路由错误:', error)
})

// 创建路由守卫
// createAuthGuard(router)

export default router
