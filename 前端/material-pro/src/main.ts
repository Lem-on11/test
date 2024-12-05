import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
//引入element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//打印
import print from 'vue3-print-nb'
//echarts
import * as echarts from 'echarts'
//确定弹框封装
import myconfirm from './utils/myconfirm'
import objCoppy from './utils/objCoppy'
//引入Pinia构造函数
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persist'
import hasPerm from './permission/hasPerm'
// 实例化 Pinia
const pinia = createPinia()
pinia.use(piniaPersist)
const app = createApp(App);
app.use(router).use(ElementPlus, {
    locale: zhCn,
}).use(pinia).use(print).mount('#app')
//全局注册图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
//全局挂载
app.config.globalProperties.$myconfirm = myconfirm
app.config.globalProperties.$objCoppy = objCoppy
app.config.globalProperties.$echarts = echarts;
app.config.globalProperties.$hasPerm = hasPerm
import './permission'
