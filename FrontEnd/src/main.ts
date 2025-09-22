/**
 * main
 * @author emiila
 * 
**/


import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './style.css'
//
import * as ElIcons from '@element-plus/icons'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import { setupRouter } from './router'
// import { setupStore } from './store'
import App from './App.vue'
//

//
const app = createApp(App)
for (const name in ElIcons) {
	app.component(name, (ElIcons as any)[name])
}
// 挂载状态管理
await setupRouter(app)
// await setupStore(app)
app.use(ElementPlus, { locale: zhCn })
app.use(createPinia())
app.mount('#app')
//
console.log('打印当前环境: ', process.env.NODE_ENV)