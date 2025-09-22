/**
 * 
 * @author emiila
 * 
**/


import { defineStore } from 'pinia'
import { LocalStorageService } from '@/utils/storage'
import { login, logout, getUserInfo, getUserRoles, getUserMenu } from '@/api/user'
import { generateTree } from '@/utils'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: LocalStorageService.get('token') || '',
    user: {} as any,
    roles: [],
    menu: LocalStorageService.get('menuTree') || [],
    buttonMap: {}
  }),
  actions: {
    // 登录
    async login(data: any) {
      try {
        const res = await login(data)
        console.log(res, 'rr')
        if (res?.code == 200) {
          this.setToken(res.data.token)
          this.token = res.data.token
          await this.getUserInfo()
          return res
        }
      } catch (error) {
        console.error('Failed to login', error)
      }
    },

    // 登出
    async logout() {
      try {
        await logout()
        this.resetToken()
      } catch (error) {
        console.error('Failed to logout', error)
      }
    },

    // 获取用户信息
    async getUserInfo() {
      try {
        const res = await getUserInfo()
        if (res && res.code == 200 && res.data) {
          this.user = res.data
          const { data: roles } = await getUserRoles(res.data.userName)
          this.roles = roles
          const roleList = roles.map((e) => e.roleCode).join(',')
          const result = await getUserMenu(roleList)
          const { normal: rawMenuData, global } = result.data
          const path2ButtonAuth = {}
          const cb = (el) => {
            const needCheck = el['buttonList'].length > 0
            const buttonKeyList = el['buttonKeyList']
            const urlAddress =
              el['menuType'] === 'menu_lv.1_4' ? `${el.urlAddress}/${el.dicCode}` : el.urlAddress
            path2ButtonAuth[urlAddress] = { needCheck, buttonKeyList }
          }
          // 生成菜单树
          const menuTree = generateTree(
            rawMenuData.concat(),
            '',
            { idName: 'menuId', parentIdName: 'parentMenuId' },
            cb
          )
          console.log(menuTree, 'menuTree');
          this.menu = menuTree as any
          LocalStorageService.set('menuTree', menuTree)
          this.buttonMap = {
            global: {
              needCheck: global['buttonList'].length > 0,
              buttonKeyList: global['buttonKeyList']
            },
            normal: path2ButtonAuth
          }
        } else {
          this.resetToken()
        }
      } catch (error) {
        console.error('Failed to get user info', error)
      }
    },

    // 重置token
    async resetToken() {
      try {
        // await resetTokenAPI();
        this.token = ''
        this.token = ''
        this.user = {}
        this.menu = []
        this.roles = []
        LocalStorageService.remove('token')
      } catch (error) {
        console.error('Failed to reset token', error)
      }
    },
    // 设置token
    setToken(token: string) {
      this.token = token
      LocalStorageService.set('token', token)
    }
  }
})
