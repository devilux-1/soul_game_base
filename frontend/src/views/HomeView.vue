<template>
  <div class="app-container">
    <!-- 左侧组件：攻略分类导航 -->
    <div class="left-sidebar">
      <!-- 用户信息区域 -->
      <div class="user-section">
        <div class="user-avatar">
          <img v-if="authStore.user?.avatar" :src="authStore.user.avatar" :alt="authStore.user.personaName" />
          <div v-else class="avatar-placeholder">{{ authStore.user?.personaName?.charAt(0) || 'U' }}</div>
        </div>
        <div class="user-info">
          <div class="user-name">{{ authStore.user?.personaName || '用户' }}</div>
          <div class="user-steam-id">Steam ID: {{ authStore.user?.steamId || '-' }}</div>
        </div>
        <button class="logout-btn" @click="handleLogout" title="退出登录">
          ↪
        </button>
      </div>

      <div class="sidebar-divider"></div>

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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useGameStore } from '@/stores/useGameStore'
import { useAuthStore } from '@/stores/useAuthStore'

const router = useRouter()

const gameStore = useGameStore()
const authStore = useAuthStore()

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
}

const currentTag = ref(null)
const searchKeyword = ref('')
const errorMessage = ref('')

const selectTag = (tag) => {
  currentTag.value = tag
  searchKeyword.value = ''
  errorMessage.value = ''
  gameStore.fetchGuides(tag, null)
}

const searchGuides = () => {
  currentTag.value = null
  errorMessage.value = ''
  gameStore.fetchGuides(null, searchKeyword.value)
}

const retryFetch = () => {
  errorMessage.value = ''
  gameStore.fetchGuides(currentTag.value, searchKeyword.value || null)
}

const toggleGuideDetail = (guide) => {
  console.log('[App] 点击攻略卡片:', guide)
  console.log('[App] 攻略ID:', guide.guideId)
  console.log('[App] 跳转路径:', `/guide/${guide.guideId}`)

  router.push(`/guide/${guide.guideId}`)
    .then(() => {
      console.log('[App] 路由跳转成功')
    })
    .catch((error) => {
      console.error('[App] 路由跳转失败:', error)
    })
}

const openInNewWindow = (guide) => {
  router.push(`/guide/${guide.guideId}`)
}

const toggleBossCheck = (bossId) => {
  gameStore.toggleBoss(bossId)
}

const getTagsArray = (tags) => {
  if (!tags || typeof tags !== 'string') return []
  return tags.split(',').map(t => t.trim()).filter(t => t !== '')
}

const getGuideCover = (guide) => {
  const defaultCovers = [
    'https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1511512578047-dfb367046420?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1493711662062-fa541f7f689b?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=200&h=150&fit=crop'
  ]

  const index = (guide.guideId % defaultCovers.length + defaultCovers.length) % defaultCovers.length
  return defaultCovers[index]
}

onMounted(() => {
  try {
    gameStore.fetchGuides(null, null)
    gameStore.fetchChecklist()
  } catch (error) {
    console.error('[App] 组件初始化失败:', error)
    errorMessage.value = '加载失败，请刷新页面重试'
  }
})
</script>

<style scoped>
.app-container {
  display: flex;
  height: 100vh;
  background-color: #121212;
  color: #e0e0e0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.left-sidebar {
  width: 260px;
  background: linear-gradient(180deg, #1a1a1a 0%, #151515 100%);
  border-right: 1px solid #2a2a2a;
  padding: 24px 16px;
  display: flex;
  flex-direction: column;
}

.user-section {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px 12px;
  background: #1e1e1e;
  border-radius: 12px;
  margin-bottom: 8px;
}

.user-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
  background: #333;
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  font-weight: bold;
  color: #ff6b35;
  background: linear-gradient(135deg, #ff6b35 0%, #f7931e 100%);
}

.user-info {
  flex: 1;
  min-width: 0;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.user-steam-id {
  font-size: 11px;
  color: #888;
}

.logout-btn {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  background: #2a2a2a;
  border: 1px solid #333;
  color: #888;
  font-size: 18px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.logout-btn:hover {
  background: #333;
  border-color: #ff6b35;
  color: #ff6b35;
}

.sidebar-divider {
  height: 1px;
  background: #2a2a2a;
  margin: 16px 0;
}

.sidebar-title {
  font-size: 22px;
  font-weight: bold;
  color: #ffffff;
  margin-bottom: 32px;
  text-align: center;
  letter-spacing: 2px;
}

.button-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.sidebar-btn {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px 20px;
  background: #242424;
  border: 1px solid #333;
  border-radius: 10px;
  color: #b0b0b0;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 15px;
}

.sidebar-btn:hover {
  background: #2a2a2a;
  border-color: #444;
  transform: translateX(4px);
}

.sidebar-btn.active {
  background: linear-gradient(135deg, #ff6b35 0%, #f7931e 100%);
  border-color: #ff6b35;
  color: #ffffff;
  box-shadow: 0 4px 20px rgba(255, 107, 53, 0.3);
}

.btn-icon {
  font-size: 20px;
}

.main-content {
  flex: 1;
  overflow-y: auto;
  padding: 32px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 32px;
}

.search-bar input {
  flex: 1;
  padding: 14px 20px;
  background: #1e1e1e;
  border: 1px solid #333;
  border-radius: 10px;
  color: #ffffff;
  font-size: 15px;
  outline: none;
  transition: border-color 0.3s;
}

.search-bar input:focus {
  border-color: #ff6b35;
}

.search-btn {
  padding: 14px 32px;
  background: linear-gradient(135deg, #ff6b35 0%, #f7931e 100%);
  border: none;
  border-radius: 10px;
  color: white;
  font-size: 15px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(255, 107, 53, 0.4);
}

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

.guide-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.guide-card {
  display: flex;
  background: #1e1e1e;
  border: 1px solid #2a2a2a;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
}

.guide-card:hover {
  border-color: #ff6b35;
  transform: translateY(-4px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.4);
}

.guide-cover {
  width: 200px;
  flex-shrink: 0;
  overflow: hidden;
}

.guide-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.guide-card:hover .guide-cover img {
  transform: scale(1.05);
}

.guide-info {
  flex: 1;
  padding: 20px 24px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.guide-title {
  font-size: 18px;
  color: #ffffff;
  margin: 0 0 10px 0;
}

.guide-summary {
  color: #999;
  margin: 0 0 12px 0;
  line-height: 1.6;
  font-size: 14px;
}

.guide-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.tag {
  padding: 4px 12px;
  background: #2a2a2a;
  border-radius: 14px;
  font-size: 12px;
  color: #ff6b35;
}

.external-link-btn {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 8px 16px;
  background: rgba(255, 107, 53, 0.9);
  border: none;
  border-radius: 6px;
  color: #ffffff;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  opacity: 0;
  transition: opacity 0.3s, background 0.3s, transform 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.guide-card:hover .external-link-btn {
  opacity: 1;
}

.external-link-btn:hover {
  background: #ff6b35;
  transform: scale(1.05);
  box-shadow: 0 4px 16px rgba(255, 107, 53, 0.4);
}

.right-sidebar {
  width: 300px;
  background: linear-gradient(180deg, #1a1a1a 0%, #151515 100%);
  border-left: 1px solid #2a2a2a;
  padding: 24px 16px;
  overflow-y: auto;
}

.checklist-title {
  font-size: 18px;
  font-weight: bold;
  color: #ffffff;
  margin-bottom: 24px;
}

.progress-section {
  margin-bottom: 24px;
}

.progress-bar-container {
  height: 12px;
  background: #2a2a2a;
  border-radius: 6px;
  overflow: hidden;
  margin-bottom: 12px;
}

.progress-bar {
  height: 100%;
  background: linear-gradient(90deg, #ff6b35 0%, #f7931e 100%);
  border-radius: 6px;
  transition: width 0.6s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 0 20px rgba(255, 107, 53, 0.5);
}

.progress-text {
  font-size: 36px;
  font-weight: bold;
  color: #ff6b35;
  text-align: center;
  margin-bottom: 8px;
}

.progress-stats {
  text-align: center;
  color: #888;
  font-size: 14px;
}

.boss-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.boss-item {
  display: flex;
  align-items: center;
}

.boss-item input[type="checkbox"] {
  width: 20px;
  height: 20px;
  margin-right: 12px;
  cursor: pointer;
  accent-color: #ff6b35;
}

.boss-item label {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
}

.boss-name {
  color: #e0e0e0;
  font-size: 14px;
  transition: color 0.2s;
}

.boss-name.completed {
  color: #666;
  text-decoration: line-through;
}

.boss-difficulty {
  font-size: 12px;
  font-weight: bold;
}

.diff-1 { color: #4caf50; }
.diff-2 { color: #8bc34a; }
.diff-3 { color: #ff9800; }
.diff-4 { color: #ff5722; }
.diff-5 { color: #f44336; }
</style>
