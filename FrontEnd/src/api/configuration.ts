/**
 * api config 
 * @author emiila
 * 
**/

import http from '@/utils/http/api'

enum Api {
  GET_CONFIG_THEME = '/configTheme',
  GET_CONFIGTHEME_BY_MENU_AND_ORG = '/configThemeController/getConfigThemeByMenuAndOrg',
  GET_POINT_TREND_DATA = '/configPartPointTrendController/getPointTrendData',
  WRITE_TASK_DATA = '/taskController/write',
  BATCH_CTRL = '/taskController/batchControl',
  SETTING_SYSTEM_CONFIG = '/customSystem',
  GET_CONFIG_THEME_LIST = '/configTheme/page',
  GET_EQUIPMENT_BY_THEMEID = '/configTheme/getEquipmentByTheme',
  GET_ALL_THINGSMODEL = '/thingModel/listModel',
  GET_ALL_THINGSMODEL_ATTRIBUTE = '/thingModel/getProperties',
  GET_ALL_THINGSMODEL_BY_THEMEID = '/configTheme/getAllEquipmentTypeByTheme',
  DELETE_LAYER_MENU = '/customSystem',
  SETTING_GROUP_PARAMS = '/customSystem/setValue',
  GET_THINGS_MODEL_PROPS = '/thingModel/getProperties',
  GET_THINGS_MODEL_PROPS_BY_LAYERID = '/customSystem/getPropertiesByLayer',
  GET_POINT_BY_EQUIPMENTID = '/point/getPointByEquipment',
  SAVE_TIME_CTRL_DATA = '/configTheme/configTimeControl',
  GET_STRATEGY_LIST = '/strategy/page',
  EXECUTE_STRATEGY = '/customSystem/executeOnOff',
  GET_EQUIPMENT_STATUS_BY_LAYERID = '/customSystem/getEquipmentInfos'
}

export function getConfigThemePage(data, page) {
  const param = { ...data, ...page }
  return http.get(`${Api.GET_CONFIG_THEME_LIST}`, param)
}
/**
 * @description 根据 主题id 获取对应主题的数据
 * @param {String} id 主题id
 */
export function getConfigTheme(id) {
  return http.get(`${Api.GET_CONFIG_THEME}/${id}`)
}
/**
 * 
 */
// 根据modelId查询物模型详情
export function getTemplateDetailByID(modelId, equipmentId) {
  return http.get(`configTheme/template/configTheme?modelId=${modelId}&equipmentId=${equipmentId}`);
}

/**
 * @description 根据 资源id 获取对应资源的主题数据
 * @param {String} orgId 资源
 * @param {String} dicCode 资源代码
 */
export function getConfigThemeByMenuAndOrg(orgId, dicCode) {
  return http.get(`${Api.GET_CONFIGTHEME_BY_MENU_AND_ORG}/${orgId}/${dicCode}`)
}

/**
 * @description 点位趋势接口
 * @param { Object } data 趋势图点位和开始时间 { partPointTrendId, startTime }
 */
export function getConfigPointTrendData(data) {
  return http.get(`${Api.GET_POINT_TREND_DATA}/${data.partPointTrendId}/${data.startTime}`)
}

export function doWriteTask(data) {
  return http.post(`${Api.WRITE_TASK_DATA}/${data.pointId}?value=${data.value}`)
}

//群控开关操作
export function handleBatchCtrl(data, value) {
  return http.post(`${Api.BATCH_CTRL}?value=${value}`, data)
}

//------------------------------工程师配置接口-------------------------------
//配置
export function saveSettingConfig(data) {
  return http.post(Api.SETTING_SYSTEM_CONFIG, data)
}
//获取组态下所有设备
export function getEquipmentByTheme(themeId, params) {
  return http.get(`${Api.GET_EQUIPMENT_BY_THEMEID}/${themeId}`, params)
}

//获取所有物模型
export function getAllThingsModel() {
  return http.get(Api.GET_ALL_THINGSMODEL)
}

//根据themeId获取物模型
export function getAllThingsModelByThemeId(themeId) {
  return http.get(`${Api.GET_ALL_THINGSMODEL_BY_THEMEID}/${themeId}`)
}
//获取物模型属性
export function getThingsModelAttribute(modelId) {
  return http.get(Api.GET_ALL_THINGSMODEL_ATTRIBUTE + '/' + modelId)
}
//删除区域菜单
export function delLayerPageMenu(id) {
  return http.delete(`${Api.DELETE_LAYER_MENU}/${id}`)
}
//分组参数设定
export function saveGroupParams(data) {
  return http.post(Api.SETTING_GROUP_PARAMS, data)
}
//根据物模型ids获取物模型属性
export function getThingsModelProps(data) {
  return http.post(Api.GET_THINGS_MODEL_PROPS, data)
}
//查询当前楼层物模型属性
export function getThingsModelPropsByLyaerId(layerId) {
  return http.get(Api.GET_THINGS_MODEL_PROPS_BY_LAYERID + '/' + layerId)
}

//获取模式控制下拉列表
export function getStrategyList(data, page) {
  return http.post(
    `${Api.GET_STRATEGY_LIST}?pageNum=${page.pageNum}&pageSize=${page.pageSize}`,
    data
  )
}

//执行策略
export function executeStrategy(data) {
  return http.get(`${Api.EXECUTE_STRATEGY}/${data.layerId}?state=${data.value}`)
}

// 顺序启停列表
export function getDataByPage(data) {
  return http.get(`/sequenceControl/page?pageNo=${data.pageNo}&pageSize=${data.pageSize}&configName=${data.configName}`)
}

// 顺序启停控制
export function handleSwitch(params) {
  return http.get('/sequenceControl/control', params)
}

//------------------------------单设备监控页面-------------------------------
//查询物模型下的属性
export function getThingsModelPropsByModelId(modelId) {
  return http.get(`${Api.GET_THINGS_MODEL_PROPS}/${modelId}`)
}
//查询设备下的属性点
export function getPointByEquipment(equipmentId) {
  return http.get(`${Api.GET_POINT_BY_EQUIPMENTID}/${equipmentId}`)
}
//时间控制表编辑
export function updateTimeCtrl(data) {
  return http.post(Api.SAVE_TIME_CTRL_DATA, data)
}
//通过区域查询设备状态
export function getEquipmentStatus(layerId) {
  return http.get(Api.GET_EQUIPMENT_STATUS_BY_LAYERID, { layerId })
}
