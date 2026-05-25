import { defineStore } from 'pinia'
import { ref } from 'vue'
import request from '@/utils/request'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
  const loading = ref(false)

  const isAuthenticated = () => {
    return token.value !== '' && user.value !== null
  }

  const getSteamAuthUrl = async () => {
    loading.value = true
    try {
      const res = await request({
        url: '/auth/steam/url',
        method: 'get'
      })
      if (res.code === 200) {
        return res.data.authUrl
      }
      return null
    } catch (error) {
      console.error('[Steam认证] 获取登录URL失败:', error)
      return null
    } finally {
      loading.value = false
    }
  }

  const handleSteamCallback = async (params) => {
    loading.value = true
    try {
      const res = await request({
        url: '/auth/steam/callback',
        method: 'get',
        params
      })
      if (res.code === 200) {
        token.value = res.data.token
        user.value = res.data.user
        localStorage.setItem('token', res.data.token)
        localStorage.setItem('user', JSON.stringify(res.data.user))
        return true
      }
      return false
    } catch (error) {
      console.error('[Steam认证] 登录回调处理失败:', error)
      return false
    } finally {
      loading.value = false
    }
  }

  const mockLogin = async (steamId) => {
    loading.value = true
    try {
      const res = await request({
        url: '/auth/steam/mock',
        method: 'post',
        params: { steamId }
      })
      if (res.code === 200) {
        token.value = res.data.token
        user.value = res.data.user
        localStorage.setItem('token', res.data.token)
        localStorage.setItem('user', JSON.stringify(res.data.user))
        return true
      }
      return false
    } catch (error) {
      console.error('[Steam认证] 模拟登录失败:', error)
      return false
    } finally {
      loading.value = false
    }
  }

  const logout = () => {
    token.value = ''
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  return {
    token,
    user,
    loading,
    isAuthenticated,
    getSteamAuthUrl,
    handleSteamCallback,
    mockLogin,
    logout
  }
})