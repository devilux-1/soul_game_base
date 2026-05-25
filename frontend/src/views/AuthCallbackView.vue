<template>
  <div class="callback-container">
    <div class="callback-box">
      <div v-if="loading" class="loading-section">
        <div class="spinner"></div>
        <h2 class="status-text">正在登录...</h2>
        <p class="description">请稍候，正在验证您的Steam账户</p>
      </div>

      <div v-else-if="success" class="success-section">
        <div class="success-icon">✓</div>
        <h2 class="status-text success">登录成功！</h2>
        <p class="description">正在跳转到首页...</p>
      </div>

      <div v-else class="error-section">
        <div class="error-icon">✗</div>
        <h2 class="status-text error">登录失败</h2>
        <p class="description">{{ errorMessage }}</p>
        <button class="back-btn" @click="goToLogin">返回登录</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/useAuthStore'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const loading = ref(true)
const success = ref(false)
const errorMessage = ref('登录验证失败')

onMounted(async () => {
  const params = route.query

  const result = await authStore.handleSteamCallback(params)
  loading.value = false

  if (result) {
    success.value = true
    setTimeout(() => {
      router.push('/')
    }, 1500)
  } else {
    errorMessage.value = 'Steam认证失败，请重试'
  }
})

const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.callback-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 20px;
}

.callback-box {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  padding: 60px 50px;
  max-width: 400px;
  width: 100%;
  text-align: center;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.spinner {
  width: 60px;
  height: 60px;
  border: 4px solid rgba(245, 197, 24, 0.2);
  border-top-color: #f5c518;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 30px;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.success-icon {
  width: 80px;
  height: 80px;
  background: rgba(46, 204, 113, 0.2);
  border: 3px solid #2ecc71;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2.5rem;
  color: #2ecc71;
  margin: 0 auto 30px;
}

.error-icon {
  width: 80px;
  height: 80px;
  background: rgba(231, 76, 60, 0.2);
  border: 3px solid #e74c3c;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2.5rem;
  color: #e74c3c;
  margin: 0 auto 30px;
}

.status-text {
  font-size: 1.5rem;
  font-weight: 600;
  color: #e0e0e0;
  margin-bottom: 15px;
}

.status-text.success {
  color: #2ecc71;
}

.status-text.error {
  color: #e74c3c;
}

.description {
  color: #a0a0a0;
  font-size: 1rem;
  margin-bottom: 30px;
  line-height: 1.6;
}

.back-btn {
  padding: 12px 30px;
  background: rgba(245, 197, 24, 0.1);
  color: #f5c518;
  border: 1px solid rgba(245, 197, 24, 0.3);
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.back-btn:hover {
  background: rgba(245, 197, 24, 0.2);
  border-color: #f5c518;
}
</style>