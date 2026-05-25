<template>
  <div class="login-container">
    <div class="login-box">
      <div class="logo-section">
        <h1 class="title">艾尔登法环</h1>
        <h2 class="subtitle">游戏指南</h2>
        <p class="description">登录以访问完整的游戏攻略和Boss检查清单</p>
      </div>

      <div class="button-section">
        <button
          class="steam-login-btn"
          @click="loginWithSteam"
          :disabled="authStore.loading"
        >
          <span v-if="!authStore.loading">
            <svg class="steam-icon" viewBox="0 0 24 24" fill="currentColor">
              <path d="M12 2C6.48 2 2 6.48 2 12c0 4.84 3.44 8.87 8 9.8V15H8v-3h2V9.5C10 7.57 11.57 6 13.5 6H16v3h-2c-.55 0-1 .45-1 1v2h3v3h-3v6.95C18.05 21.45 22 17.19 22 12c0-5.52-4.48-10-10-10z"/>
            </svg>
            使用Steam登录
          </span>
          <span v-else>登录中...</span>
        </button>

        <div class="divider">
          <span>或者</span>
        </div>

        <div class="mock-login-section">
          <p class="mock-title">开发模式 - 模拟登录</p>
          <input
            v-model="mockSteamId"
            type="text"
            placeholder="输入Steam ID"
            class="mock-input"
          />
          <button class="mock-login-btn" @click="handleMockLogin" :disabled="authStore.loading">
            模拟登录
          </button>
        </div>
      </div>

      <div class="footer">
        <p>需要Steam API Key才能在生产环境使用真实登录</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/useAuthStore'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()
const mockSteamId = ref('76561198000000000')

const loginWithSteam = async () => {
  const authUrl = await authStore.getSteamAuthUrl()
  if (authUrl) {
    window.location.href = authUrl
  } else {
    ElMessage.error('获取Steam登录URL失败')
  }
}

const handleMockLogin = async () => {
  if (!mockSteamId.value) {
    ElMessage.warning('请输入Steam ID')
    return
  }

  const success = await authStore.mockLogin(mockSteamId.value)
  if (success) {
    ElMessage.success('登录成功')
    router.push('/')
  } else {
    ElMessage.error('登录失败')
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 20px;
}

.login-box {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  padding: 60px 50px;
  max-width: 450px;
  width: 100%;
  border: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
}

.logo-section {
  text-align: center;
  margin-bottom: 50px;
}

.title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #f5c518;
  margin-bottom: 10px;
  text-shadow: 0 0 20px rgba(245, 197, 24, 0.3);
}

.subtitle {
  font-size: 1.5rem;
  color: #e0e0e0;
  margin-bottom: 15px;
}

.description {
  color: #a0a0a0;
  font-size: 1rem;
  line-height: 1.6;
}

.button-section {
  margin-bottom: 30px;
}

.steam-login-btn {
  width: 100%;
  padding: 16px 30px;
  background: linear-gradient(135deg, #171a21 0%, #1b2838 100%);
  color: #c5c3c0;
  border: 2px solid #4c6a92;
  border-radius: 10px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  transition: all 0.3s ease;
}

.steam-login-btn:hover:not(:disabled) {
  background: linear-gradient(135deg, #1b2838 0%, #2a475e 100%);
  border-color: #66c0f4;
  transform: translateY(-2px);
  box-shadow: 0 10px 30px -10px rgba(102, 192, 244, 0.3);
}

.steam-login-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.steam-icon {
  width: 24px;
  height: 24px;
}

.divider {
  text-align: center;
  margin: 30px 0;
  position: relative;
}

.divider::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background: rgba(255, 255, 255, 0.1);
}

.divider span {
  background: rgba(26, 26, 46, 0.95);
  padding: 0 20px;
  color: #808080;
  font-size: 0.9rem;
  position: relative;
  z-index: 1;
}

.mock-login-section {
  text-align: center;
}

.mock-title {
  color: #808080;
  margin-bottom: 15px;
  font-size: 0.9rem;
}

.mock-input {
  width: 100%;
  padding: 12px 16px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  color: #fff;
  font-size: 1rem;
  margin-bottom: 15px;
  outline: none;
  transition: all 0.3s ease;
}

.mock-input:focus {
  border-color: #f5c518;
  box-shadow: 0 0 0 3px rgba(245, 197, 24, 0.1);
}

.mock-input::placeholder {
  color: #606060;
}

.mock-login-btn {
  width: 100%;
  padding: 12px 24px;
  background: rgba(245, 197, 24, 0.1);
  color: #f5c518;
  border: 1px solid rgba(245, 197, 24, 0.3);
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.mock-login-btn:hover:not(:disabled) {
  background: rgba(245, 197, 24, 0.2);
  border-color: #f5c518;
}

.footer {
  text-align: center;
  color: #606060;
  font-size: 0.8rem;
}

.footer p {
  margin: 0;
}
</style>