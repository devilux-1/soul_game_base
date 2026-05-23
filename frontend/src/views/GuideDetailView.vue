<template>
  <div class="guide-detail-page">
    <!-- 加载状态 -->
    <div v-if="loading" class="loading-container">
      <div class="spinner"></div>
      <p>正在加载攻略详情...</p>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="errorMessage" class="error-container">
      <p>{{ errorMessage }}</p>
      <button class="retry-btn" @click="fetchGuideDetail">重试</button>
    </div>

    <!-- 攻略详情 -->
    <div v-else-if="guide" class="detail-content">
      <!-- 返回按钮 -->
      <button class="back-btn" @click="goBack">
        <span>←</span> 返回列表
      </button>

      <!-- 标题区 -->
      <div class="header">
        <h1 class="title">{{ guide.title }}</h1>
        <div class="meta-info">
          <span v-if="guide.createTime" class="date">
            {{ formatDate(guide.createTime) }}
          </span>
          <span v-if="guide.viewCount !== undefined" class="view-count">
            👁 {{ guide.viewCount }}
          </span>
          <div class="tags">
            <span v-for="tag in getTagsArray(guide.tags)" :key="tag" class="tag">
              {{ tag }}
            </span>
          </div>
        </div>
      </div>

      <!-- 封面图 -->
      <div v-if="getGuideCover(guide)" class="cover-image">
        <img :src="getGuideCover(guide)" :alt="guide.title" />
      </div>

      <!-- 正文内容 -->
      <div class="content" v-html="formatContent(guide.content)"></div>

      <!-- 底部操作 -->
      <div class="footer-actions">
        <button class="action-btn collect-btn">
          ⭐ 收藏攻略
        </button>
        <button class="action-btn share-btn">
          📤 分享攻略
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
/**
 * 攻略详情页面组件
 *
 * @author Soul Game Team
 * @since 1.0.0
 * @description 显示攻略的详细内容，支持返回列表
 */

import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const route = useRoute()
const router = useRouter()

// 攻略数据
const guide = ref(null)
const loading = ref(false)
const errorMessage = ref('')

/**
 * 获取攻略详情
 */
const fetchGuideDetail = async () => {
  loading.value = true
  errorMessage.value = ''

  try {
    const guideId = route.params.id
    const res = await request({
      url: `/guides/${guideId}`,
      method: 'get'
    })

    console.log('[GuideDetail] API响应:', res)
    if (res.code === 200 && res.data) {
      guide.value = res.data
      console.log('[GuideDetail] 攻略数据:', guide.value)
    } else {
      errorMessage.value = '该攻略不存在'
      ElMessage.warning('该攻略不存在')
    }
  } catch (error) {
    console.error('获取攻略详情失败:', error)
    errorMessage.value = '获取攻略详情失败，请重试'
    ElMessage.error(error.message || '获取攻略详情失败')
  } finally {
    loading.value = false
  }
}

/**
 * 返回上一页
 */
const goBack = () => {
  router.push('/')
}

/**
 * 将标签字符串转换为数组
 * @param {string} tags 标签字符串
 * @returns {Array}
 */
const getTagsArray = (tags) => {
  if (!tags || typeof tags !== 'string') return []
  return tags.split(',').map(t => t.trim()).filter(t => t !== '')
}

/**
 * 获取攻略封面
 * @param {Object} guide 攻略对象
 * @returns {string}
 */
const getGuideCover = (guide) => {
  const covers = [
    'https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=1200&h=600&fit=crop',
    'https://images.unsplash.com/photo-1511512578047-dfb367046420?w=1200&h=600&fit=crop'
  ]
  const index = (guide?.guideId % covers.length + covers.length) % covers.length
  return covers[index]
}

/**
 * 格式化日期
 * @param {string} dateStr 日期字符串
 * @returns {string}
 */
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

/**
 * 格式化内容
 * @param {string} content 原始内容
 * @returns {string}
 */
const formatContent = (content) => {
  if (!content) return ''
  // 替换换行符为<br>
  let formatted = content.replace(/\n/g, '<br>')
  return formatted
}

onMounted(() => {
  if (route.params.id) {
    fetchGuideDetail()
  } else {
    errorMessage.value = '缺少攻略 ID'
  }
})
</script>

<style scoped>
.guide-detail-page {
  min-height: 100vh;
  background-color: #121212;
  color: #e0e0e0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  overflow-y: auto;
  padding-bottom: 60px;
}

/* 加载和错误状态 */
.loading-container, .error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 50vh;
  padding: 60px 20px;
}

.spinner {
  width: 48px;
  height: 48px;
  border: 4px solid #333;
  border-top-color: #ff6b35;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.loading-container p, .error-container p {
  margin-top: 16px;
  color: #b0b0b0;
  font-size: 15px;
}

.retry-btn {
  margin-top: 20px;
  padding: 12px 32px;
  background: #242424;
  border: 1px solid #ff6b35;
  border-radius: 8px;
  color: #ff6b35;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.retry-btn:hover {
  background: #ff6b35;
  color: #ffffff;
}

/* 详情内容 */
.detail-content {
  max-width: 900px;
  margin: 0 auto;
  padding: 40px 20px;
}

/* 返回按钮 */
.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  color: #b0b0b0;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 40px;
}

.back-btn:hover {
  border-color: #ff6b35;
  color: #ff6b35;
}

/* 标题区 */
.header {
  margin-bottom: 40px;
}

.title {
  font-size: 32px;
  color: #ffffff;
  margin: 0 0 16px 0;
  line-height: 1.4;
}

.meta-info {
  display: flex;
  align-items: center;
  gap: 20px;
  flex-wrap: wrap;
}

.date {
  color: #888;
  font-size: 14px;
}

.view-count {
  color: #888;
  font-size: 14px;
}

.tags {
  display: flex;
  gap: 8px;
}

.tag {
  padding: 4px 12px;
  background: #2a2a2a;
  border-radius: 14px;
  font-size: 12px;
  color: #ff6b35;
}

/* 封面图 */
.cover-image {
  width: 100%;
  margin-bottom: 40px;
  border-radius: 12px;
  overflow: hidden;
}

.cover-image img {
  width: 100%;
  height: auto;
  object-fit: cover;
  display: block;
}

/* 正文内容 */
.content {
  font-size: 16px;
  line-height: 2;
  color: #ccc;
  word-wrap: break-word;
  white-space: pre-wrap;
}

.content p {
  margin: 20px 0;
}

.content strong {
  color: #ffffff;
  font-weight: 600;
}

.content h2, .content h3, .content h4 {
  color: #ffffff;
  margin-top: 40px;
  margin-bottom: 20px;
  font-weight: 600;
}

.content h2 {
  font-size: 24px;
}

.content h3 {
  font-size: 20px;
}

.content ul, .content ol {
  margin: 20px 0;
  padding-left: 24px;
}

.content li {
  margin: 10px 0;
}

.content code {
  background: #1e1e1e;
  padding: 2px 8px;
  border-radius: 4px;
  font-family: monospace;
}

.content blockquote {
  border-left: 4px solid #ff6b35;
  padding-left: 20px;
  margin: 24px 0;
  color: #aaa;
  font-style: italic;
}

/* 底部操作 */
.footer-actions {
  display: flex;
  gap: 16px;
  margin-top: 60px;
  padding-top: 40px;
  border-top: 1px solid #333;
}

.action-btn {
  flex: 1;
  padding: 14px 32px;
  background: #1e1e1e;
  border: 1px solid #333;
  border-radius: 10px;
  color: #b0b0b0;
  font-size: 15px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.action-btn:hover {
  border-color: #ff6b35;
  color: #ff6b35;
}

.collect-btn:hover {
  background: linear-gradient(135deg, #ff6b35 0%, #f7931e 100%);
  border-color: transparent;
  color: #ffffff;
}

/* 响应式布局 */
@media (max-width: 768px) {
  .title {
    font-size: 24px;
  }

  .detail-content {
    padding: 20px 16px;
  }

  .footer-actions {
    flex-direction: column;
  }
}
</style>
