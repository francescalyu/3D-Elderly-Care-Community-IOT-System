import { request } from "@/http"

//获取当日能源
export function getPower() {
  return request({
    method: "get",
    url: `/api/v1/dash/getPower`
  })
}
//获取日(小时)能源
export function getPowerbyHour(qdate) {
  return request({
    method: "get",
    url: `/api/v1/dash/getPowerbyHour?qdate=${qdate}`
  })
}
//获取月(日)能源
export function getPowerbyDay(qdate) {
  return request({
    method: "get",
    url: `/api/v1/dash/getPowerbyDay?qdate=${qdate}`
  })
}
//以孪生uuid查询设备当前点位值
export function gettwinpoint(uuid) {
  return request({
    method: "get",
    url: `/api/v1/device/gettwinpoint?uuid=${uuid}`
  })
}
//获取当前空气质量
export function getAir() {
  return request({
    method: "get",
    url: `/api/v1/dash/getAir`
  })
}
//获取门禁及户外数据
export function getOutDoor() {
  return request({
    method: "get",
    url: `/api/v1/dash/getOutDoor`
  })
}
//获取健康数据
export function getHealth() {
  return request({
    method: "get",
    url: `/api/v1/dash/getHealth`
  })
}
//获取1小时健康数据
export function getHealthbyHour(qdate) {
  return request({
    method: "get",
    url: `/api/v1/dash/getHealthbyHour?qdate=${qdate}`
  })
}
//列出所有设备
export function getall() {
  return request({
    method: "get",
    url: `/api/v1/device/getall`
  })
}
//列出指定设备当前点位值
export function getdevpoint(id) {
  return request({
    method: "get",
    url: `/api/v1/device/getdevpoint?devId=${id}`
  })
}
//列出产品物模型
export function getPoint(pid) {
  return request({
    method: "get",
    url: `/api/v1/product/getPoint?id=${pid}`
  })
}
//查询设备指定点位值
export function getdevonepoint(devId, pointId) {
  return request({
    method: "get",
    url: `/api/v1/device/getdevonepoint?devId=${devId}&pointId=${pointId}`
  })
}
//执行设备命令
export function setSndcmd(data) {
  return request({
    method: "post",
    url: `/api/v1/device/sndcmd`,
    headers: {
      'content-type': 'application/json; charset=utf-8',
    },
    data,
  })
}
//列出当前租户
export function getTenant() {
  return request({
    method: "get",
    url: `/api/v1/people/getTenant`
  })
}
//列出当日所有告警
export function getTodayWarn() {
  return request({
    method: "get",
    url: `/api/v1/warn/getTodayWarn`
  })
}
//子系统列表
export function getSubsysAll() {
  return request({
    method: "get",
    url: `/api/v1/subsys/getall`
  })
}
