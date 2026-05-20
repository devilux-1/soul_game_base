import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
import App from './App.vue'

/**
 * Vue应用入口文件
 *
 * @author Soul Game Team
 * @since 1.0.0
 */

// 创建Vue应用实例
const app = createApp(App)

// 创建Pinia状态管理实例
const pinia = createPinia()

// 使用Pinia状态管理
app.use(pinia)

// 使用Vue Router路由
app.use(router)

// 使用Element Plus UI组件库
app.use(ElementPlus)

// 将应用挂载到id为app的DOM元素上
app.mount('#app')
