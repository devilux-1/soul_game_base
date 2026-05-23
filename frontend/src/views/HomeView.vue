<template>
  <div class="app-container">
    <!-- 左侧组件：攻略分类导航 -->
    <div class="left-sidebar">
      <div class="sidebar-title">艾尔登法环</div>
      <div class="button-group">
        <button 
          class="sidebar-btn" 
          :class="{ active: currentTag === '开荒攻略' }"
          @click="selectTag('开荒攻略')"
        >
          <span class="btn-icon">⚔️</span>
          <span>开荒攻略</span>
        </button>
        <button 
          class="sidebar-btn" 
          :class="{ active: currentTag === '全收集攻略' }"
          @click="selectTag('全收集攻略')"
        >
          <span class="btn-icon">📜</span>
          <span>全收集攻略</span>
        </button>
        <button 
          class="sidebar-btn" 
          :class="{ active: currentTag === null }"
          @click="selectTag(null)"
        >
          <span class="btn-icon">🏠</span>
          <span>全部</span>
        </button>
      </div>
    </div>

    <!-- 中间组件：攻略流式列表 -->
    <div class="main-content">
      <div class="search-bar">
        <input 
          v-model="searchKeyword"
          type="text" 
          placeholder="搜索攻略，如：名刀月隐"
          @keyup.enter="searchGuides"
        />
        <button class="search-btn" @click="searchGuides">搜索</button>
      </div>
      
      <!-- 加载状态显示 -->
      <div v-if="gameStore.loading" class="loading-spinner">
        <div class="spinner"></div>
        <p>正在加载攻略...</p>
      </div>
      
      <!-- 错误状态显示 -->
      <div v-else-if="errorMessage" class="error-message">
        <p>{{ errorMessage }}</p>
        <button class="retry-btn" @click="retryFetch">重试</button>
      </div>
      
      <!-- 空状态显示 -->
      <div v-else-if="gameStore.guides.length === 0" class="empty-state">
        <p>暂无攻略</p>
      </div>
      
      <!-- 攻略列表 -->
      <div v-else class="guide-list">
        <div
          v-for="guide in gameStore.guides"
          :key="guide.guideId"
          class="guide-card"
          @click="toggleGuideDetail(guide)"
        >
          <div class="guide-cover">
            <img :src="getGuideCover(guide)" :alt="guide.title" />
          </div>
          <div class="guide-info">
            <h3 class="guide-title">{{ guide.title }}</h3>
            <p class="guide-summary">{{ guide.summary }}</p>
            <div class="guide-tags">
              <span v-for="tag in getTagsArray(guide.tags)" :key="tag" class="tag">
                {{ tag }}
              </span>
            </div>
          </div>
          <!-- 打开详情按钮 -->
          <button
            class="external-link-btn"
            @click.stop="openInNewWindow(guide)"
            title="查看详情"
          >
            查看详情 →
          </button>
        </div>
      </div>
    </div>

    <!-- 右侧组件：Boss 检查清单 -->
    <div class="right-sidebar">
      <div class="checklist-title">Boss 检查清单</div>
      
      <div class="progress-section">
        <div class="progress-bar-container">
          <div 
            class="progress-bar" 
            :style="{ width: gameStore.checklist.progressPercent + '%' }"
          ></div>
        </div>
        <div class="progress-text">
          {{ gameStore.checklist.progressPercent }}%
        </div>
        <div class="progress-stats">
          {{ gameStore.checklist.completedBosses }} / {{ gameStore.checklist.totalBosses }} 已完成
        </div>
      </div>

      <div class="boss-list">
        <div 
          v-for="item in gameStore.checklist.bossStatusList" 
          :key="item.boss.id"
          class="boss-item"
        >
          <input 
            type="checkbox" 
            :checked="item.isCompleted"
            @change="toggleBossCheck(item.boss.id)"
            :id="'boss-' + item.boss.id"
          />
          <label :for="'boss-' + item.boss.id">
            <span class="boss-name" :class="{ completed: item.isCompleted }">
              {{ item.boss.name }}
            </span>
            <span class="boss-difficulty" :class="'diff-' + item.boss.difficultyLevel">
              ★{{ item.boss.difficultyLevel }}
            </span>
          </label>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
/**
 * App 主组件
 * 实现艾尔登法环游戏攻略的三栏布局页面
 *
 * @author Soul Game Team
 * @since 1.0.0
 * @description
 * - 左侧: 攻略分类导航
 * - 中间: 攻略搜索和列表展示
 * - 右侧: Boss 检查清单和进度追踪
 */

import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useGameStore } from '@/stores/useGameStore'

const router = useRouter()

// 使用 Pinia Store
const gameStore = useGameStore()

// 当前选中的标签（用于高亮显示）
const currentTag = ref(null)

// 搜索关键词
const searchKeyword = ref('')

// 错误消息（用于显示错误状态）
const errorMessage = ref('')

/**
 * 选择标签按钮点击事件
 * 触发 Pinia 的 fetchGuides 方法进行标签筛选
 *
 * @param {string|null} tag - 选中的标签，null 表示显示全部
 */
const selectTag = (tag) => {
  currentTag.value = tag  // 更新当前选中标签
  searchKeyword.value = ''  // 清空搜索关键词
  errorMessage.value = ''  // 清空错误消息
  gameStore.fetchGuides(tag, null)  // 调用 Store 方法获取攻略列表
}

/**
 * 搜索按钮点击事件
 * 触发 Pinia 的 fetchGuides 方法进行关键词搜索
 */
const searchGuides = () => {
  currentTag.value = null  // 清空标签选中状态
  errorMessage.value = ''  // 清空错误消息
  gameStore.fetchGuides(null, searchKeyword.value)  // 调用 Store 方法进行搜索
}

/**
 * 重试获取攻略列表
 * 当加载失败时点击重试按钮触发
 */
const retryFetch = () => {
  errorMessage.value = ''  // 清空错误消息
  gameStore.fetchGuides(currentTag.value, searchKeyword.value || null)  // 重新获取数据
}

/**
 * 切换攻略详情显示
 * 点击攻略卡片时跳转到详情页面
 *
 * @param {Object} guide - 要查看的攻略对象
 */
const toggleGuideDetail = (guide) => {
  console.log('[App] 点击攻略卡片:', guide)
  console.log('[App] 攻略ID:', guide.guideId)
  console.log('[App] 跳转路径:', `/guide/${guide.guideId}`)
  
  // 跳转到详情页面
  router.push(`/guide/${guide.guideId}`)
    .then(() => {
      console.log('[App] 路由跳转成功')
    })
    .catch((error) => {
      console.error('[App] 路由跳转失败:', error)
    })
}

/**
 * 打开攻略详情页面
 * 阻止事件冒泡
 *
 * @param {Object} guide - 要打开的攻略对象
 */
const openInNewWindow = (guide) => {
  // 跳转到详情页面（当前窗口）
  router.push(`/guide/${guide.guideId}`)
}

/**
 * 切换 Boss 检查状态
 * 点击复选框时触发 Store 的 toggleBoss 方法
 * 
 * @param {number} bossId - 要切换的 Boss ID
 */
const toggleBossCheck = (bossId) => {
  gameStore.toggleBoss(bossId)
}

/**
 * 将标签字符串转换为数组
 * 后端返回的 tags 可能是逗号分隔的字符串
 *
 * @param {string} tags - 逗号分隔的标签字符串
 * @returns {Array} 标签数组
 */
const getTagsArray = (tags) => {
  // 防御式编程：确保 tags 存在且是字符串
  if (!tags || typeof tags !== 'string') return []
  // 按逗号分隔并去除首尾空格
  return tags.split(',').map(t => t.trim()).filter(t => t !== '')
}

/**
 * 获取攻略封面图片
 * 如果攻略有封面 URL 则使用，否则使用默认图片
 *
 * @param {Object} guide - 攻略对象
 * @returns {string} 图片 URL
 */
const getGuideCover = (guide) => {
  // 使用默认图片数组，根据攻略 ID 轮换选择
  const defaultCovers = [
    'https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1511512578047-dfb367046420?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1493711662062-fa541f7f689b?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=200&h=150&fit=crop'
  ]

  // 使用攻略 ID 取余确保每个攻略有固定的默认封面
  const index = (guide.guideId % defaultCovers.length + defaultCovers.length) % defaultCovers.length
  return defaultCovers[index]
}

// 组件挂载时自动获取数据
onMounted(() => {
  // 防御式编程：添加错误处理
  try {
    gameStore.fetchGuides(null, null)  // 获取所有攻略
    gameStore.fetchChecklist()          // 获取 Boss 检查清单
  } catch (error) {
    console.error('[App] 组件初始化失败:', error)
    errorMessage.value = '加载失败，请刷新页面重试'
  }
})
</script>

<style scoped>
/**
 * 全局样式
 * 设置暗黑 Steam 风格的背景和字体
 */
.app-container {
  display: flex;
  height: 100vh;
  background-color: #121212;  /* 深黑色背景 */
  color: #e0e0e0;              /* 高级灰文字 */
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

/* ==================== 左侧边栏样式 ==================== */
.left-sidebar {
  width: 260px;
  background: linear-gradient(180deg, #1a1a1a 0%, #151515 100%);  /* 渐变背景 */
  border-right: 1px solid #2a2a2a;  /* 微灰边框 */
  padding: 24px 16px;
  display: flex;
  flex-direction: column;
}

/* 侧边栏标题样式 */
.sidebar-title {
  font-size: 22px;
  font-weight: bold;
  color: #ffffff;  /* 白色文字 */
  margin-bottom: 32px;
  text-align: center;
  letter-spacing: 2px;  /* 字间距 */
}

/* 按钮组样式 */
.button-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* 质感按钮样式 */
.sidebar-btn {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px 20px;
  background: #242424;     /* 小黑盒背景 */
  border: 1px solid #333;
  border-radius: 10px;      /* 圆角 */
  color: #b0b0b0;           /* 高级灰文字 */
  cursor: pointer;
  transition: all 0.3s ease;  /* 平滑过渡 */
  font-size: 15px;
}

/* 按钮悬停效果 */
.sidebar-btn:hover {
  background: #2a2a2a;
  border-color: #444;
  transform: translateX(4px);  /* 向右微移 */
}

/* 按钮激活状态（选中状态） */
.sidebar-btn.active {
  background: linear-gradient(135deg, #ff6b35 0%, #f7931e 100%);  /* 橙色渐变 */
  border-color: #ff6b35;
  color: #ffffff;
  box-shadow: 0 4px 20px rgba(255, 107, 53, 0.3);  /* 阴影发光效果 */
}

.btn-icon {
  font-size: 20px;
}

/* ==================== 主内容区样式 ==================== */
.main-content {
  flex: 1;
  overflow-y: auto;  /* 内容区可滚动 */
  padding: 32px;
}

/* 搜索栏样式 */
.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 32px;
}

.search-bar input {
  flex: 1;
  padding: 14px 20px;
  background: #1e1e1e;      /* 小黑盒背景 */
  border: 1px solid #333;
  border-radius: 10px;
  color: #ffffff;
  font-size: 15px;
  outline: none;             /* 去除默认聚焦框 */
  transition: border-color 0.3s;  /* 聚焦时边框颜色过渡 */
}

/* 输入框聚焦效果 */
.search-bar input:focus {
  border-color: #ff6b35;   /* 聚焦时边框变橙色 */
}

/* 搜索按钮样式 */
.search-btn {
  padding: 14px 32px;
  background: linear-gradient(135deg, #ff6b35 0%, #f7931e 100%);
  border: none;
  border-radius: 10px;
  color: white;
  font-size: 15px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;  /* 按钮悬停动画 */
}

.search-btn:hover {
  transform: translateY(-2px);  /* 向上微移 */
  box-shadow: 0 6px 20px rgba(255, 107, 53, 0.4);  /* 发光阴影 */
}

/* ==================== 加载状态样式 ==================== */
/* 加载动画容器 */
.loading-spinner {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #b0b0b0;
}

.loading-spinner p {
  margin-top: 16px;
  font-size: 14px;
}

/* 旋转动画 */
.spinner {
  width: 48px;
  height: 48px;
  border: 4px solid #333;      /* 灰色边框 */
  border-top-color: #ff6b35;  /* 顶部橙色边框 */
  border-radius: 50%;          /* 圆形 */
  animation: spin 1s linear infinite;  /* 1秒旋转一圈，无限循环 */
}

@keyframes spin {
  0% { transform: rotate(0deg); }    /* 起始角度 */
  100% { transform: rotate(360deg); } /* 结束角度（360度 = 一圈） */
}

/* ==================== 错误状态样式 ==================== */
/* 错误提示容器 */
.error-message {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #ff6b35;
  background: #1e1e1e;
  border-radius: 12px;
  border: 1px solid #333;
}

.error-message p {
  margin-bottom: 20px;
  font-size: 15px;
}

/* 重试按钮 */
.retry-btn {
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

/* ==================== 空状态样式 ==================== */
/* 空状态提示 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #666;
}

.empty-state p {
  font-size: 15px;
}

/* ==================== 攻略卡片列表样式 ==================== */
.guide-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 攻略卡片样式（左图右文） */
.guide-card {
  display: flex;
  background: #1e1e1e;      /* 小黑盒背景 */
  border: 1px solid #2a2a2a;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;  /* 悬停动画 */
  position: relative;         /* 用于定位外部链接按钮 */
}

/* 卡片悬停效果 */
.guide-card:hover {
  border-color: #ff6b35;     /* 悬停时边框变橙色 */
  transform: translateY(-4px);  /* 向上浮起 */
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.4);  /* 阴影 */
}

/* 卡片封面图片 */
.guide-cover {
  width: 200px;
  flex-shrink: 0;             /* 不允许收缩 */
  overflow: hidden;
}

.guide-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;          /* 保持比例填充 */
  transition: transform 0.3s; /* 图片缩放动画 */
}

.guide-card:hover .guide-cover img {
  transform: scale(1.05);      /* 悬停时图片微放大 */
}

/* 卡片信息区域 */
.guide-info {
  flex: 1;
  padding: 20px 24px;
  display: flex;
  flex-direction: column;
  justify-content: center;    /* 垂直居中 */
}

/* 攻略标题 */
.guide-title {
  font-size: 18px;
  color: #ffffff;              /* 白色标题 */
  margin: 0 0 10px 0;
}

/* 攻略简介 */
.guide-summary {
  color: #999;                 /* 高级灰简介 */
  margin: 0 0 12px 0;
  line-height: 1.6;            /* 行高 */
  font-size: 14px;
}

/* 标签容器 */
.guide-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;            /* 允许换行 */
}

/* 标签样式 */
.tag {
  padding: 4px 12px;
  background: #2a2a2a;
  border-radius: 14px;
  font-size: 12px;
  color: #ff6b35;             /* 橙色标签文字 */
}

/* 打开详情按钮 */
.external-link-btn {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 8px 16px;
  background: rgba(255, 107, 53, 0.9);  /* 橙色半透明背景 */
  border: none;
  border-radius: 6px;
  color: #ffffff;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  opacity: 0;                 /* 默认隐藏 */
  transition: opacity 0.3s, background 0.3s, transform 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.guide-card:hover .external-link-btn {
  opacity: 1;                 /* 悬停时显示 */
}

.external-link-btn:hover {
  background: #ff6b35;        /* 悬停时更亮的橙色 */
  transform: scale(1.05);
  box-shadow: 0 4px 16px rgba(255, 107, 53, 0.4);
}

/* ==================== 右侧边栏样式 ==================== */
.right-sidebar {
  width: 300px;
  background: linear-gradient(180deg, #1a1a1a 0%, #151515 100%);
  border-left: 1px solid #2a2a2a;
  padding: 24px 16px;
  overflow-y: auto;               /* 内容区可滚动 */
}

/* 检查清单标题 */
.checklist-title {
  font-size: 18px;
  font-weight: bold;
  color: #ffffff;
  margin-bottom: 24px;
}

/* 进度区域 */
.progress-section {
  margin-bottom: 24px;
}

/* 进度条容器 */
.progress-bar-container {
  height: 12px;
  background: #2a2a2a;
  border-radius: 6px;
  overflow: hidden;                /* 隐藏进度条超出的部分 */
  margin-bottom: 12px;
}

/* 进度条 */
.progress-bar {
  height: 100%;
  background: linear-gradient(90deg, #ff6b35 0%, #f7931e 100%);  /* 橙色渐变 */
  border-radius: 6px;
  transition: width 0.6s cubic-bezier(0.4, 0, 0.2, 1);          /* 平滑涨动动画 */
  box-shadow: 0 0 20px rgba(255, 107, 53, 0.5);                  /* 发光效果 */
}

/* 进度百分比文字 */
.progress-text {
  font-size: 36px;
  font-weight: bold;
  color: #ff6b35;                  /* 橙色百分比 */
  text-align: center;
  margin-bottom: 8px;
}

/* 进度统计文字 */
.progress-stats {
  text-align: center;
  color: #888;
  font-size: 14px;
}

/* Boss 列表 */
.boss-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* Boss 项样式 */
.boss-item {
  display: flex;
  align-items: center;
}

/* 复选框样式 */
.boss-item input[type="checkbox"] {
  width: 20px;
  height: 20px;
  margin-right: 12px;
  cursor: pointer;
  accent-color: #ff6b35;           /* 选中时橙色 */
}

/* Boss 标签样式 */
.boss-item label {
  flex: 1;
  display: flex;
  justify-content: space-between;  /* 两端对齐 */
  align-items: center;
  cursor: pointer;
}

/* Boss 名称 */
.boss-name {
  color: #e0e0e0;
  font-size: 14px;
  transition: color 0.2s;          /* 状态变化过渡 */
}

/* 已完成的 Boss 名称 */
.boss-name.completed {
  color: #666;                     /* 灰色 */
  text-decoration: line-through;    /* 删除线 */
}

/* Boss 难度星级 */
.boss-difficulty {
  font-size: 12px;
  font-weight: bold;
}

/* 不同难度等级的颜色 */
.diff-1 { color: #4caf50; }   /* 绿色 */
.diff-2 { color: #8bc34a; }   /* 浅绿 */
.diff-3 { color: #ff9800; }   /* 橙色 */
.diff-4 { color: #ff5722; }   /* 深橙 */
.diff-5 { color: #f44336; }   /* 红色 */
</style>
