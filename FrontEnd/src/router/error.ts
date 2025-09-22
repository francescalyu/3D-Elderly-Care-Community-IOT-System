/**
 * 
 * @author emiila
 * 
**/

import type { RouteRecordRaw } from 'vue-router'
import  NotFound  from '@/views/error-page/404.vue';
import  Page401  from '@/views/error-page/401.vue';
//

const errorRoutes: RouteRecordRaw[] = [
  {
    path: '/:pathMatch(.*)*', 
    name: 'NotFound',
    component: NotFound
  },
  {
    path: '/401',
    name: '401',
    component: () => Page401
  }
];

export default errorRoutes;
