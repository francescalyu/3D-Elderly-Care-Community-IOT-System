/**
 * remain for user/login
 * @author emiila
 * 
**/


import http from '@/utils/http/api'
import { request } from '@/http'

/**
 * 获取用户菜单
 * @param {Array<string>} roleCodeList
 * @returns {Promise<AxiosResponse<any>>}
 */
export function getUserMenu(roleCodeList = []) {
  return http.get('/roleController/userMenu', { params: { roleCodeList: roleCodeList.join(',') } });
}

/**
 * 保存角色按钮
 * @param roleCode
 * @param buttonIdList
 * @returns {Promise<AxiosResponse<any>>}
 */
export function saveRoleButton(roleCode, buttonIdList) {
  return http.post('/roleController/roleButton', { roleCode, buttonIdList });
}

/**
 * 获取角色资源
 * @returns {Promise<AxiosResponse<any>>}
 */
export function getOrgTreeObject(roleCode) {
  // return http.get('/roleController/orgTreeObject', { params: { roleCode } });
  return request({
    url: `roleController/orgTreeObject?roleCode=${roleCode}`,
    method: 'get',
    // params: { roleCode },
  });
}

/**
 * 保存关联关系
 * @param {string} roleCode
 * @param {Array<string>} orgIdList
 * @returns {Promise<AxiosResponse<any>>}
 */
export function saveRoleOrg(roleCode, orgIdList) {
  return http.post('/roleController/roleOrg', { roleCode, orgIdList });
}

/**
 * 获取角色菜单
 * @returns {Promise<AxiosResponse<any>>}
 */
export function getMenuTreeObject(roleCode) {
  return http.get(`/roleController/menuTreeObject?roleCode=${roleCode}`);
}

/**
 * 保存关联关系
 * @param {string} roleCode
 * @param {Array<string>} menuIdList
 * @returns {Promise<AxiosResponse<any>>}
 */
export function saveRoleMenu(roleCode, menuIdList) {
  return http.post('/roleController/roleMenu', { roleCode, menuIdList });
}

// 查询角色
export function getRoleData(data = {}, paging = { page: 1, size: 1000 }) {
  return request({
    url: `roleController/findRole/${paging.page}/${paging.size}`,
    method: 'post',
    data,
  });
}

/**
 * 通过 角色 code 获取对应的角色
 * @param roleCode
 * @returns {Promise<AxiosResponse<any>>}
 */
export function getRoleByCode(roleCode) {
  return http.get(`/roleController/role/${roleCode}`);
}

/**
 * 新增用户
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 */
export function addRole(data = {}) {
  return http.post('/roleController/addRole', data);
}

/**
 * 修改角色
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 */
export function updateRole(data = {}) {
  return http.put('/roleController/updateRole', data);
}

// 删除角色
export function delRole(roleCode) {
  return request({
    url: `roleController/delRole/${roleCode}`,
    method: 'delete',
  });
}
