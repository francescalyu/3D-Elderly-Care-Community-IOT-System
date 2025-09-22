/**
 * common define
 * @author emiila
 * 
**/

// import http from '@/utils/http/api'
// import request from "@/utils/http/axios";
import { request } from "@/http"

//获取字典通用
export function getDict(code:String){
    return request({
      method: "get",
      url: `/dicController/getDictItemsByCode?dictCode=${code}`
    })
}

export function getDictType(code:String){
  return request({
    method: "get",
    url: `/dicController/getDictItemTreeByCode?dictCode=${code}`
  })
}

// 字典翻译
export function dictTrans(data:Array<any>,text:any){
    const dictMap = {} as any
    console.log(data, 'dd');
    
    data.forEach((item) => {
        dictMap[item.itemValue] = item.itemText
      })
      return text !== undefined&&Object.keys(dictMap).length>0? dictMap[text] : ''
}
// 字典策略区域翻译
export function dictTrees(data:Array<any>,text:any){
  const dictMap = {} as any
  data.forEach((item) => {
      dictMap[item.id] = item.name
    })
    return text !== undefined&&Object.keys(dictMap).length>0? dictMap[text] : ''
}
// 获取部位树
// export function getDepartTree(params: any) {
//     return request.get({ url: '/depart/departTree?needPopulation=true', params })
//   }
export function getDepartTree(params: any) {
    return request({ 
    method: "get",
    url: '/depart/departTree?needPopulation=true', 
    params 
  })
  }

// 通过部门岗位id获取用户列表
export const getUserList = (departId: string) => {
    // return request.get({ url: `/userController/${departId}/list` })
    return request({ 
    method: "get",
    url: `/userController/${departId}/list` 
  })
  }

// 能源类别设备查询
export function getCategroyEquipment(data,page){
  return request({ 
    method: "post",
    url: '/energyCategory/equipment',
    data,
    page
  })
}


// 获取系统标题
export function getSystemTitle() {
  return request({
      url: `/node/getTitle`,
      method: 'get'
  });
}
// logo管理新版
// 获取
export function getSystemLogoNew() {
  return request({
      url: `logo`,
      method: 'get'
  });
}
//获取区域下拉列表
export function getAreaList() {
  return request({
      url: `/dict/getDictItemsByCode?dictCode=energy_area`,
      method: 'get',
  });
}
//设备类型
export function getDeviceType(data = {}) {
  return request({
      url: `/dict/getDictItemsByCode?dictCode=equipment_type`,
      method: 'get',
      data,
  });
}
export function getDeviceTypeNew() {
  return request({
      url: `/equipment/getEquipmentType`,
      method: 'get',
  });
}

// 获取数字孪生url
export function getDigitalTwinUrl() {
  return request({
      url: `/digitalTwin`,
      method: 'get',
  });
}