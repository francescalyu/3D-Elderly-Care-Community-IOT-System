// 告警板块接口
import request from '@/utils/http/axios';

// 获取人员列表
export function getBaseUserList() {
    return request({
        url: '/userController/base/list',
        method: 'get',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
    })
}
// 告警配置
// 新增
export function alarmStrategyAdd(data) {
    return request({
        url: '/alarmStrategy',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data,
    })
}
// 分页
export function getAlarmStrategyPage(data, params) {
    return request({
        url: '/alarmStrategy/page',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        params: params,
        data
    })
}
// 修改
export function alarmStrategyUpdate(data) {
    return request({
        url: '/alarmStrategy',
        method: 'put',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data
    })
}
// 启用、禁用
export function getAlarmStrategyEnabled(strategyId, enabled) {
    return request({
        url: `alarmStrategy/${strategyId}/${enabled}`,
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
    })
}
// 删除
export function alarmStrategyDelete(strategyId) {
    return request({
        url: `/alarmStrategy/${strategyId}`,
        method: 'delete',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
    })
}
// 批量删除
export function alarmStrategyDeleteBatch(ids) {
    return request({
        url: `/alarmStrategy/batchDelete`,
        method: 'delete',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        params: { ids: ids.join(',') },    })
}
// 单个查询
export function getAlarmStrategyById(id) {
    return request({
        url: `/alarmStrategy/${id}`,
        method: 'get',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
    })
}
// 告警级别
// 分页
export function getAlarmLevelPage(data, params) {
    return request({
        url: '/alarmLevel/page',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        params: params,
        data
    })
}
// 列表
export function getAlarmLevelList(data) {
    return request({
        url: '/alarmLevel/list',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data
    })
}
// 新增
export function alarmLevelAdd(data) {
    return request({
        url: '/alarmLevel/add',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data,
    })
}
// 编辑
export function alarmLevelUpdate(data) {
    return request({
        url: '/alarmLevel/update',
        method: 'put',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data
    })
}
// 删除
export function alarmLevelDelete(data) {
    return request({
        url: '/alarmLevel/delete',
        method: 'delete',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data
    })
}
// 告警设备
// 物模型列表
export function getAlarmEquipmentThingModels(data) {
    return request({
        url: '/alarmEquipment/thingModels',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data,
    })
}
// 设备列表
export function getAlarmEquipmentEquipments(data, params) {
    return request({
        url: '/alarmEquipment/equipments',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data,
        params: params
    })
}
// 通讯设备
export function getAlarmCommunicationEquipments(data, params) {
    return request({
        url: '/alarmEquipment/equipments/communication',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data,
        params: params
    })
}
// 告警记录
// 分页
export function getAlarmStrategyRecordPage(data, params) {
    return request({
        url: '/alarmStrategyRecord/page',
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data,
        params: params
    })
}
// 处理
export function handleAlarmStrategyRecord(data, recordId) {
    return request({
        url: `/alarmStrategyRecord/handle/${recordId}`,
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data
    })
}

// 系统处理（通讯设备告警、采集数据异常）
export function handleAlarmStrategyRecordOfSystem(data, recordId) {
    return request({
        url: `/alarmStrategyRecord/handle/communication/${recordId}`,
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data
    })
}
// 批量处理
export function handleAlarmStrategyRecordAll(data) {
    return request({
        url: `/alarmStrategyRecord/handle/communication`,
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data
    })
}
// 删除
export function delAlarmStrategyRecord(recordId) {
    return request({
        url: `/alarmStrategyRecord/${recordId}`,
        method: 'delete',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
    })
}
// 批量删除
export function delAlarmStrategyRecordAll(data) {
    return request({
        url: `/alarmStrategyRecord`,
        method: 'delete',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data
    })
}
// 消息记录
// 分页
export function getMessagePage(data, params) {
    return request({
        url: `/message/page`,
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data,
        params: params
    })
}
// 批量删除
export function delMessageBranch(params) {
    return request({
        url: `/message`,
        method: 'delete',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        params: params
    })
}
// 标记已读
export function getMessageRead(params) {
    return request({
        url: `/message/read`,
        method: 'get',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        params: params
    })
}

//
// 关闭全屏告警/alarmStrategy/new/record/close
export const closeTopScreen = (ids) => {
    return request({
        url: `/alarmStrategyRecord/close/topScreen`,
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        params: { ids: ids.join(',') },
    })
}

// 批量转工单
export const toWorkOrderBatch = (data, ids) => {
    return request({
        url: `/alarmStrategyRecord/batchHandle`,
        method: 'post',
        headers: {
            'content-type': 'application/json; charset=utf-8',
        },
        data,
        params: { ids: ids.join(',') },
    })
}
