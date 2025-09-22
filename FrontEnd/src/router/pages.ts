/**
 * new router record
 * @author emiila
 * 
**/



import type { RouteRecordRaw } from 'vue-router'

//注册路由

/* 工作台 */
/**目前工作台不做 */
// import workbenches1 from '../views/workbenches/workbenches1.vue'
// import workbenches2 from '../views/workbenches/workbenches2.vue'
// import workbenches21 from '../views/workbenches/workbenches2-1.vue'
export const workbenches: RouteRecordRaw[] = [
  // {
  //   path: '/workbenches1',
  //   component: () => import('../views/workbenches/workbenches1.vue'),
  //   name: 'workbenches1',
  //   meta: { title: '工作台菜单1' }
  // },
  // {
  //   path: '/workbenches2',
  //   component: () => import('../views/workbenches/workbenches2.vue'),
  //   name: 'workbenches2',
  //   meta: { title: '工作台菜单2' },
  // },
  // {
  //   path: '/workbenches2-1',
  //   component: () => import('../views/workbenches/workbenches2-1.vue'),
  //   name: 'workbenches2-1',
  //   meta: { title: '工作台菜单2-1' }
  // },
]

/** 仪表盘 */
// import parkIntelligentControl1 from '../views/parkIntelligentControl/parkIntelligentControl1.vue'
// import parkIntelligentControl2 from '../views/parkIntelligentControl/parkIntelligentControl2.vue'
// import parkIntelligentControl21 from '../views/parkIntelligentControl/parkIntelligentControl2-1.vue'
export const Dashboards: RouteRecordRaw[] = [
  {
    path: '/energy',
    component: () => import('../views/Dashboards/energy/index.vue'),
    name: 'energy',
    meta: { title: '能源' }
  },
  {
    path: '/environment',
    component: () => import('../views/Dashboards/environment/index.vue'),
    name: 'environment',
    meta: { title: '环境' }
  },
  {
    path: '/health',
    component: () => import('../views/Dashboards/Health/index.vue'),
    name: 'health',
    meta: { title: '健康' }
  },
  {
    path: '/device',
    component: () => import('../views/Dashboards/Device/index.vue'),
    name: 'device',
    meta: { title: '设备' }
  },
  {
    path: '/product',
    component: () => import('../views/Dashboards/Product/index.vue'),
    name: 'product',
    meta: { title: '产品' }
  },
]

/** 告警 */
// import smartEnergyCarbon1 from '../views/smartEnergyCarbon/smartEnergyCarbon1.vue'
// import smartEnergyCarbon2 from '../views/smartEnergyCarbon/smartEnergyCarbon2.vue'
// import smartEnergyCarbon21 from '../views/smartEnergyCarbon/smartEnergyCarbon2-1.vue'
export const smartEnergyCarbon: RouteRecordRaw[] = [
  {
    path: '/alarmPage',
    component: () => import('../views/Alarm/alarmPage/index.vue'),
    name: 'alarmPage',
    meta: { title: '告警' }
  },
  {
    path: '/energyConservation',
    component: () => import('../views/Alarm/energyConservation/index.vue'),
    name: 'energyConservation',
    meta: { title: '节能' },
  },
  {
    path: '/workOrder',
    component: () => import('../views/Alarm/workOrder/index.vue'),
    name: 'workOrder',
    meta: { title: '工单' },
  },
]

/** 策略 */
// import maintenanceOverview from '../views/smartMaintenance/maintenanceOverview.vue'
// import smartMaintenance2 from '../views/smartMaintenance/smartMaintenance2.vue'
// import smartMaintenance21 from '../views/smartMaintenance/smartMaintenance2-1.vue'
export const smartMaintenance: RouteRecordRaw[] = [
  {
    path: '/alarmStrategy',
    component: () => import('../views/Strategy/alarmStrategy/index.vue'),
    name: 'alarmStrategy',
    meta: { title: '告警' }
  },

  {
    path: '/energyConservationStrategy',
    component: () => import('../views/Strategy/energyConservationStrategy/index.vue'),
    name: 'ECStrategy',
    meta: { title: '节能' }
  },
  // {
  //   path: '/workStrategy',
  //   component: () => import('../views/Strategy/workStrategy/index.vue'),
  //   name: 'workStrategy',
  //   meta: { title: '工单' }
  // },
]

/** 智慧安防 */
// import smartSecurity1 from '../views/smartSecurity/smartSecurity1.vue'
// import smartSecurity2 from '../views/smartSecurity/smartSecurity2.vue'
// import smartSecurity21 from '../views/smartSecurity/smartSecurity2-1.vue'
export const smartSecurity: RouteRecordRaw[] = [
  {
    path: '/Author',
    component: () => import('../views/About/Author/index.vue'),
    name: 'Author',
    meta: { title: '作者' }
  },
  {
    path: '/Architecture',
    component: () => import('../views/About/Architecture/index.vue'),
    name: 'Architecture',
    meta: { title: '架构' },
  },
  {
    path: '/DataModel',
    component: () => import('../views/About/DataModel/index.vue'),
    name: 'DataModel',
    meta: { title: '模型' }
  },
]

/** 园区运营 */
// import parkOperation1 from '../views/parkOperation/parkOperation1.vue'
// import parkOperation2 from '../views/parkOperation/parkOperation2.vue'
// import parkOperation21 from '../views/parkOperation/parkOperation2-1.vue'
export const parkOperation: RouteRecordRaw[] = [
  // {
  //   path: '/parkOperation1',
  //   component: () => import('../views/parkOperation/parkOperation1.vue'),
  //   name: 'parkOperation1',
  //   meta: { title: '园区运营1' }
  // },
  // {
  //   path: '/parkOperation2',
  //   component: () => import('../views/parkOperation/parkOperation2.vue'),
  //   name: 'parkOperation2',
  //   meta: { title: '园区运营2' },
  // },
  // {
  //   path: '/parkOperation2-1',
  //   component: () => import('../views/parkOperation/parkOperation2-1.vue'),
  //   name: 'parkOperation2-1',
  //   meta: { title: '园区运营2-1' }
  // },
]


/** 系统设置 */
export const systemRoutes: RouteRecordRaw[] = [
  // 角色管理

  // 用户管理

]

// 输出必要router数组
export const pageRoutes: RouteRecordRaw[] = [
  // ...workbenches,//工作台
  ...Dashboards,//看板
  ...smartEnergyCarbon,//告警
  ...smartMaintenance,//智慧运维
  ...smartSecurity,//智慧安防
  ...parkOperation,//园区运营
  ...systemRoutes//系统设置
]
