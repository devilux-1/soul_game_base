<template>
  <!-- Vue应用根组件 -->
  <div id="app">
    <!-- 路由视图，用于显示匹配到的路由组件 -->
    <router-view />
  </div>
</template>

<script>
/**
 * Vue应用根组件
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
export default {
  name: 'App'
}
</script>

<style>
/* 全局样式 */
* {
  /* 消除默认外边距 */
  margin: 0;
  /* 消除默认内边距 */
  padding: 0;
  /* 盒模型设置为border-box */
  box-sizing: border-box;
}

html, body, #app {
  /* 设置高度为100% */
  height: 100%;
  /* 设置宽度为100% */
  width: 100%;
}
</style>
