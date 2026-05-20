<template>
  <!-- 首页组件 -->
  <div class="home-container">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <!-- 项目标题 -->
      <h1 class="title">Soul Game Database</h1>
      <!-- 项目描述 -->
      <p class="description">Steam游戏Hub - 探索精彩的游戏世界</p>
    </div>
  </div>
</template>

<script>
/**
 * 首页组件
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
export default {
  name: 'HomeView'
}
</script>

<style scoped>
/* 首页容器样式 */
.home-container {
  /* 设置宽度为100% */
  width: 100%;
  /* 设置高度为100% */
  height: 100%;
  /* 使用flex布局 */
  display: flex;
  /* 垂直居中 */
  align-items: center;
  /* 水平居中 */
  justify-content: center;
  /* 设置背景颜色 */
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

/* 欢迎区域样式 */
.welcome-section {
  /* 文字居中 */
  text-align: center;
  /* 文字颜色为白色 */
  color: white;
}

/* 标题样式 */
.title {
  /* 字体大小 */
  font-size: 3rem;
  /* 底部外边距 */
  margin-bottom: 1rem;
  /* 字体粗细 */
  font-weight: bold;
}

/* 描述样式 */
.description {
  /* 字体大小 */
  font-size: 1.5rem;
  /* 透明度 */
  opacity: 0.9;
}
</style>
