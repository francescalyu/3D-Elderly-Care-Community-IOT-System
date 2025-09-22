
/**
 * user router
 * @author emiila
 * 
**/

import { routes } from '../router/index'
import { defineStore } from 'pinia'

export const usePermissionStore = defineStore('permission', {
  state: () => {
    return {
      routes: [],
      addRoutes: []
    }
  },
  actions: {
    generateRoutes(menu) {
        return new Promise((resolve) => {
          this.routes = generateUserRoutes(menu, routes)
          this.addRoutes = this.routes
          resolve(this.routes)
        })
    }
  }
})

/**
 * @description 生成用户路由
 * @param routes asyncRoutes
 * @param menu 用户菜单数据
 */
function generateUserRoutes(menu, router) {
  let route = []
  router[0].children.forEach((v) => {
    for (let d of menu) {
      let userPath = d.urlAddress.split('/')
      let routerPath = v.path.split('/')

      if (userPath[userPath.length - 1] == ':id') userPath.length = userPath.length - 1
      if (userPath[userPath.length - 1].split('?').length > 1)
        userPath[userPath.length - 1] = userPath[userPath.length - 1].split('?')[0]
      if (userPath[userPath.length - 1] === routerPath[0]) {
        if (d.children && d.children.length > 0 && v.children && v.children.length > 0) {
          v.children = generateUserRoutes(d.children, v.children)
        }
        route.push(v)
      }
    }
  })

  router[0].children = route
  return router
}
