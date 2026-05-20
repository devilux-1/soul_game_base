import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

/**
 * Vite配置文件
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
export default defineConfig({
  // 插件配置
  plugins: [
    // Vue支持插件
    vue()
  ],
  // 路径别名配置
  resolve: {
    alias: {
      // 使用@作为src目录的别名
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  // 开发服务器配置
  server: {
    // 端口号
    port: 5173,
    // 自动打开浏览器
    open: true,
    // 代理配置，用于解决跨域问题
    proxy: {
      // 将/api开头的请求代理到后端服务器
      '/api': {
        // 后端服务器地址
        target: 'http://localhost:8080',
        // 允许跨域
        changeOrigin: true
      }
    }
  }
})
