
/**
 * 游戏状态管理 Store
 * 负责管理攻略列表、Boss检查清单等数据状态
 * 
 * @author Soul Game Team
 * @since 1.0.0
 * @description 使用 Pinia 进行状态管理，写死 userId = 1
 */

import { defineStore } from 'pinia'
import { ref } from 'vue'
import request from '@/utils/request'

/**
 * 定义游戏状态管理 Store
 * 使用 Composition API 风格
 */
export const useGameStore = defineStore('game', () => {
  /**
   * 用户ID - 固定为1（演示用途）
   * 写死确保数据隔离和演示一致性
   */
  const userId = 1
  
  /**
   * 攻略列表数据
   * 存储从后端获取的所有攻略信息
   */
  const guides = ref([])
  
  /**
   * Boss检查清单数据
   * 包含Boss状态列表、总数、已完成数、进度百分比
   */
  const checklist = ref({
    bossStatusList: [],  // Boss状态列表
    totalBosses: 0,       // Boss总数
    completedBosses: 0,    // 已完成的Boss数量
    progressPercent: 0     // 进度百分比 (0-100)
  })
  
  /**
   * 加载状态标识
   * 用于控制 Loading 动画的显示与隐藏
   */
  const loading = ref(false)

  /**
   * 获取攻略列表函数
   * 通过 Axios 请求后端的 /api/guides 接口
   * 支持按标签筛选和关键词模糊搜索
   * 
   * @param {string|null} tag - 标签参数，用于按标签筛选攻略（如"开荒攻略"、"全收集攻略"）
   * @param {string|null} keyword - 关键词参数，用于在标题、简介和内容中模糊搜索
   * @returns {Promise<void>} 无返回值，数据直接存储到 guides 状态中
   * 
   * @example
   * // 获取所有攻略
   * await fetchGuides(null, null)
   * 
   * // 按标签筛选
   * await fetchGuides('开荒攻略', null)
   * 
   * // 关键词搜索
   * await fetchGuides(null, '名刀月隐')
   */
  const fetchGuides = async (tag, keyword) => {
    // 步骤1: 设置加载状态为 true，显示 Loading 动画
    loading.value = true
    
    try {
      // 步骤2: 构建查询参数对象
      // 只添加非空参数，避免发送无效请求参数
      const params = {}
      
      // 如果 tag 参数存在且不为空字符串，则添加到查询参数中
      if (tag && tag.trim() !== '') {
        params.tag = tag.trim()
        console.log(`[攻略查询] 按标签筛选: ${tag}`)
      }
      
      // 如果 keyword 参数存在且不为空字符串，则添加到查询参数中
      if (keyword && keyword.trim() !== '') {
        params.keyword = keyword.trim()
        console.log(`[攻略查询] 关键词搜索: ${keyword}`)
      }
      
      // 步骤3: 发送 Axios GET 请求到后端 /api/guides 接口
      // Vite 开发服务器会将 /api 开头的请求代理到 http://localhost:8080
      const res = await request({
        url: '/guides',  // 会被代理到 /api/guides
        method: 'get',   // 使用 GET 方法
        params          // 查询参数：{ tag?: string, keyword?: string }
      })
      
      // 步骤4: 处理响应数据
      // 后端返回格式: { code: 200, data: [...] }
      if (res.code === 200) {
        // 数据验证：确保返回的是数组
        if (Array.isArray(res.data)) {
          guides.value = res.data  // 直接将数据存储到状态中
          console.log(`[攻略查询] 成功获取 ${res.data.length} 条攻略`)
        } else {
          // 防御式编程：数据格式异常时打印警告
          console.warn('[攻略查询] 警告: 后端返回数据格式异常，不是数组')
          guides.value = []  // 确保 guides 始终是数组
        }
      } else {
        // 防御式编程：响应码非200时打印错误日志
        console.error(`[攻略查询] 错误: 后端返回异常码 ${res.code}`)
      }
    } catch (error) {
      // 步骤5: 统一错误处理
      // 捕获网络错误、请求超时、后端异常等情况
      console.error('[攻略查询] 获取攻略失败，详细错误:', error)
      
      // 根据错误类型提供更友好的日志信息
      if (error.code === 'ECONNABORTED') {
        console.error('[攻略查询] 网络超时，请检查后端服务是否正常运行')
      } else if (error.response) {
        // 服务器返回错误状态码（如404、500等）
        console.error(`[攻略查询] 服务器错误: ${error.response.status} - ${error.response.statusText}`)
      } else if (error.request) {
        // 请求已发出但没有收到响应
        console.error('[攻略查询] 网络连接失败，请检查网络或后端服务')
      }
    } finally {
      // 步骤6: 无论成功或失败，都需要在 finally 中清除加载状态
      // 确保 Loading 动画能够正确关闭
      loading.value = false
    }
  }

  /**
   * 获取 Boss 检查清单函数
   * 请求后端的 /api/checklist 接口
   * 获取当前用户（userId=1）的所有 Boss 完成状态和进度
   * 
   * @returns {Promise<void>} 无返回值，数据直接存储到 checklist 状态中
   * 
   * @description
   * 后端返回数据结构:
   * {
   *   bossStatusList: [{ boss: BossList, isCompleted: boolean }],
   *   totalBosses: number,
   *   completedBosses: number,
   *   progressPercent: number
   * }
   */
  const fetchChecklist = async () => {
    try {
      // 发送 Axios GET 请求到 /api/checklist
      console.log(`[清单查询] 获取用户 ${userId} 的 Boss 检查清单`)
      
      const res = await request({
        url: '/checklist',  // 会被代理到 /api/checklist
        method: 'get'       // 使用 GET 方法
      })
      
      // 处理响应数据
      if (res.code === 200) {
        // 防御式编程：验证数据结构完整性
        if (res.data && typeof res.data === 'object') {
          checklist.value = {
            bossStatusList: res.data.bossStatusList || [],  // Boss状态列表，确保是数组
            totalBosses: res.data.totalBosses || 0,         // Boss总数
            completedBosses: res.data.completedBosses || 0, // 已完成数
            progressPercent: res.data.progressPercent || 0  // 进度百分比
          }
          console.log(`[清单查询] 成功获取清单: ${checklist.value.completedBosses}/${checklist.value.totalBosses} 完成`)
        } else {
          console.warn('[清单查询] 警告: 后端返回数据格式异常')
        }
      } else {
        console.error(`[清单查询] 错误: 后端返回异常码 ${res.code}`)
      }
    } catch (error) {
      // 统一错误处理
      console.error('[清单查询] 获取清单失败，详细错误:', error)
      
      if (error.response) {
        console.error(`[清单查询] 服务器错误: ${error.response.status} - ${error.response.statusText}`)
      } else if (error.request) {
        console.error('[清单查询] 网络连接失败')
      }
    }
  }

  /**
   * 切换 Boss 完成状态函数
   * 请求后端的 /api/checklist/toggle 接口
   * 切换指定 Boss 的完成状态，并更新本地 UI（乐观更新）
   * 
   * @param {number} bossId - 要切换的 Boss ID
   * @returns {Promise<void>} 无返回值
   * 
   * @description
   * 实现乐观更新策略:
   * 1. 先立即更新本地 UI（提升用户体验）
   * 2. 发送请求到后端
   * 3. 如果请求失败，回滚本地 UI 状态
   * 4. 如果请求成功，使用后端返回的最新进度数据更新 UI
   */
  const toggleBoss = async (bossId) => {
    // 防御式编程：参数校验
    if (!bossId || typeof bossId !== 'number') {
      console.error('[Boss切换] 参数错误: bossId 必须是非空数字')
      return
    }
    
    try {
      // 步骤1: 乐观更新 - 先立即更新本地 UI
      // 在发送请求之前就更新界面，让用户感受到即时响应
      const bossStatus = checklist.value.bossStatusList.find(
        item => item.boss && item.boss.id === bossId
      )
      
      if (bossStatus) {
        // 记录当前状态，用于可能的回滚
        const previousStatus = bossStatus.isCompleted
        
        // 立即翻转状态
        bossStatus.isCompleted = !previousStatus
        console.log(`[Boss切换] 乐观更新: Boss ${bossId} 状态从 ${previousStatus} 变为 ${bossStatus.isCompleted}`)
        
        // 立即更新进度数据（乐观更新进度百分比）
        const delta = bossStatus.isCompleted ? 1 : -1
        checklist.value.completedBosses += delta
        checklist.value.progressPercent = checklist.value.totalBosses > 0
          ? Math.round((checklist.value.completedBosses / checklist.value.totalBosses) * 100)
          : 0
      }
      
      // 步骤2: 发送 POST 请求到 /api/checklist/toggle
      const res = await request({
        url: '/checklist/toggle',  // 会被代理到 /api/checklist/toggle
        method: 'post',           // 使用 POST 方法
        data: { bossId }          // 请求体：{ bossId: number }
      })
      
      // 步骤3: 请求成功后，使用后端返回的最新数据更新 UI
      // 确保本地数据与后端数据一致
      if (res.code === 200) {
        if (res.data) {
          checklist.value.progressPercent = res.data.progressPercent || 0
          checklist.value.completedBosses = res.data.completedBosses || 0
          checklist.value.totalBosses = res.data.totalBosses || checklist.value.totalBosses
          console.log(`[Boss切换] 成功: 进度更新为 ${checklist.value.progressPercent}%`)
        }
      } else {
        // 后端返回异常码，需要回滚乐观更新
        console.error(`[Boss切换] 错误: 后端返回异常码 ${res.code}`)
        
        // 回滚操作：恢复之前的状态
        if (bossStatus) {
          bossStatus.isCompleted = !bossStatus.isCompleted
          // 恢复进度数据
          const rollbackDelta = bossStatus.isCompleted ? 1 : -1
          checklist.value.completedBosses += rollbackDelta
          checklist.value.progressPercent = checklist.value.totalBosses > 0
            ? Math.round((checklist.value.completedBosses / checklist.value.totalBosses) * 100)
            : 0
          console.log('[Boss切换] 已回滚乐观更新')
        }
      }
    } catch (error) {
      // 步骤4: 请求失败，需要回滚乐观更新
      console.error('[Boss切换] 切换 Boss 状态失败，详细错误:', error)
      
      // 找到对应的 Boss 状态并回滚
      const bossStatus = checklist.value.bossStatusList.find(
        item => item.boss && item.boss.id === bossId
      )
      
      if (bossStatus) {
        // 回滚到之前的状态
        bossStatus.isCompleted = !bossStatus.isCompleted
        // 回滚进度数据
        const rollbackDelta = bossStatus.isCompleted ? 1 : -1
        checklist.value.completedBosses += rollbackDelta
        checklist.value.progressPercent = checklist.value.totalBosses > 0
          ? Math.round((checklist.value.completedBosses / checklist.value.totalBosses) * 100)
          : 0
        console.log('[Boss切换] 异常回滚完成')
      }
      
      if (error.response) {
        console.error(`[Boss切换] 服务器错误: ${error.response.status}`)
      }
    }
  }

  /**
   * 导出 Store 的状态和方法
   * 供组件使用
   */
  return {
    userId,           // 用户ID（写死为1）
    guides,           // 攻略列表
    checklist,        // Boss检查清单
    loading,          // 加载状态
    fetchGuides,      // 获取攻略列表
    fetchChecklist,   // 获取检查清单
    toggleBoss        // 切换Boss状态
  }
})

