/**
 * remain for user/login
 * @author emiila
 * 
**/



import { request } from '@/http'

// 用户登录
export function login(data: any) {
    // return request.post('/login', null, { params: data });
    return request({
      method: 'post',
      url: '/login',
      data
    })
}

// 用户注销登录
export function logout() {
  return request({
    method: 'post',
    url: '/logout'
  })
}

// 修改用户密码
export function updatePassword(data: any) {
  return request({
    method: 'post',
    url: '/userController/updatePasswd',
    data
  })
}

// 获取当前用户数据
export function getUserInfo() {
    return request({
      method: 'get',
      url: '/userController/getCurrentUser'
    })
}

// 获取当前用户的角色数据
export function getUserRoles(userName) {
    return request({
      method: 'get',
      url: `/userController/getRoleByUser/${userName}`
    })
}

// 获取用户菜单
export function getUserMenu(roleCodeList) {
    return request({
      method: 'get',
      url: `/roleController/userMenu?roleCodeList=${roleCodeList}`,
    })
}

// 通过角色的到用户
export function getUserByRoleCode(roleCode) {
    return request({
      url: `/userController/getUserByRoleCode/${roleCode}`,
      method: 'get',
    });
  }
  
  // 根据页面url获取当前页面权限按钮
  export function getCurrentUserMenuBtn(data) {
    return request({
      url: `/userController/getCurrentUserMenuBtn`,
      method: 'post',
      data,
    });
  }

  /** 用户管理 */
  // 获取全部用户
export function getAllUser(data = {}, paging = { page: 1, size: 9999 }) {
    return request({
      url: `userController/findUser/${paging.page}/${paging.size}`,
      method: 'post',
      data,
    });
  }
  
  // 新增用户
  export function addUser(data = {}) {
    return request({
      url: `userController/addUser`,
      method: 'post',
      data,
    });
  }
  
  // 删除用户
  export function delUser(userName) {
    return request({
      url: `userController/delUser/${userName}`,
      method: 'delete',
    });
  }
  
  /**
   * 批量删除用户
   * @param {Array<string>} userNames
   * @returns {Promise<AxiosResponse<any>>}
   */
  export function delBatchUser(userNames) {
    return request({
      url: `userController/delBatchUser?userNames=${userNames}`,
      method: 'delete',
      // params: {
      //   userNames: userNames.join(','),
      // },
    });
  }
  
  // 修改用户
  export function updateUser(data = {}) {
    return request({
      url: `userController/updateUser`,
      method: 'put',
      data,
    });
  }
  
  // 通过 userName 获取用户数据
  export function getUserByName(userName) {
    return request({
      url: `/userController/getUser/${userName}`,
      method: 'get',
    });
  }
  
  // 删除用户的 资源 权限
  export function delOrgUserByUserName(userName) {
    return request({
      url: `userController/delOrgUserByUserName/${userName}`,
      method: 'delete',
    });
  }
  
  // 添加用户资源权限
  export function saveOrgUserList(data = {}) {
    return request({
      url: `userController/saveOrgUserList`,
      method: 'post',
      data,
    });
  }
  
  // 日志管理列表
  export function pageLog(data = {}, paging = { pageNum: 1, pageSize: 9999 }) {
    return request({
      url: `logController/pageLog/${paging.pageNum}/${paging.pageSize}`,
      method: 'post',
      data,
    });
  }
  //日志管理导出
  export function exportLog(data = {}) {
    return request({
      url: `logController/exportLog`,
      method: 'post',
      headers: {
        'content-type': 'application/json; charset=utf-8',
      },
      responseType: 'blob',
      data,
    });
  }
  
  // 查询用户列表
export const geUserList = (page: any, data: any) => {
  return request({
      url: '/userController/findUser',
      method: 'post',
      data: { page, data }
  })
}