import { createRouter, createWebHistory } from 'vue-router'

/**
 * Vue Router路由配置文件
 *
 * @author Soul Game Team
 * @since 1.0.0
 */

// 定义路由配置
const routes = [
  {
    // 根路径
    path: '/',
    // 路由名称
    name: 'Home',
    // 组件，使用懒加载方式
    component: () => import('@/views/HomeView.vue')
  }
]

// 创建路由实例
const router = createRouter({
  // 使用HTML5 History模式
  history: createWebHistory(import.meta.env.BASE_URL),
  // 路由配置
  routes
})

// 导出路由实例
export default router
