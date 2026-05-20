import axios from 'axios'
import { ElMessage } from 'element-plus'

/**
 * Axios请求工具类，用于统一处理HTTP请求
 *
 * @author Soul Game Team
 * @since 1.0.0
 */

// 创建axios实例
const request = axios.create({
  // 基础URL，通过Vite的代理转发到后端
  baseURL: '/api',
  // 请求超时时间设置为10秒
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么，比如添加token
    // 可以从localStorage中获取token
    const token = localStorage.getItem('token')
    // 如果token存在，则添加到请求头中
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    // 返回配置对象
    return config
  },
  error => {
    // 对请求错误做些什么
    console.error('请求错误:', error)
    // 返回一个被拒绝的Promise
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    // 对响应数据做些什么
    const res = response.data
    // 这里可以根据后端返回的状态码进行统一处理
    return res
  },
  error => {
    // 对响应错误做些什么
    console.error('响应错误:', error)
    // 显示错误提示消息
    ElMessage.error(error.message || '请求失败，请稍后重试')
    // 返回一个被拒绝的Promise
    return Promise.reject(error)
  }
)

// 导出axios实例
export default request
