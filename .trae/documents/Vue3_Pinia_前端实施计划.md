# Vue 3 + Pinia 前端代码实施计划

## 项目概述
为一个 Steam 游戏 Hub 项目（艾尔登法环）开发暗黑风三栏布局前端，对接后端 `http://localhost:8080` 的 RESTful API。

## 技术栈
- **前端框架**: Vue 3 (Composition API)
- **状态管理**: Pinia
- **HTTP 客户端**: Axios
- **UI 风格**: 暗黑 Steam 风格

## 后端接口分析

### 1. 攻略接口 (GuideController)
- **路径**: `GET /api/guides`
- **参数**: 
  - `tag` (可选): 标签筛选，如 "开荒攻略"、"全收集攻略"
  - `keyword` (可选): 关键词模糊搜索
- **响应**: `{ code: 200, data: [...] }`

### 2. 检查清单接口 (ChecklistController)
- **获取清单**: `GET /api/checklist`
- **切换状态**: `POST /api/checklist/toggle` (body: `{ bossId: number }`)

## 实施步骤

### 步骤 1: 优化状态管理 (useGameStore.js)
**目标**: 确保状态管理逻辑完善，写死 userId = 1

**任务清单**:
- [ ] 确认 `userId = 1` 写死不变
- [ ] 优化 `fetchGuides(tag, keyword)` 函数
  - 添加 loading 状态管理
  - 添加错误处理和日志
  - 确保请求路径为 `/api/guides`
  - 响应数据直接存储到 `guides.value`
- [ ] 优化 `fetchChecklist()` 函数
  - 完善错误处理
  - 确保数据结构正确映射
- [ ] 优化 `toggleBoss(bossId)` 函数
  - 无刷新更新进度条
  - 乐观更新 UI
  - 错误回滚机制

**代码规范**:
```javascript
/**
 * 获取攻略列表
 * @param {string|null} tag - 标签筛选
 * @param {string|null} keyword - 关键词搜索
 */
const fetchGuides = async (tag, keyword) => {
  // 1. 设置加载状态
  // 2. 构建请求参数
  // 3. 发送 Axios 请求到 /api/guides
  // 4. 处理响应和数据存储
  // 5. 完善错误捕获
  // 6. 清除加载状态
}
```

### 步骤 2: 完善暗黑风三栏布局 (App.vue)
**目标**: 实现完整的 Steam 暗黑风格 UI

#### 2.1 整体视觉设计
- 背景色: `#121212` (深黑)
- 卡片背景: `#1e1e1e` (小黑盒)
- 边框色: `#2a2a2a` (微灰边框)
- 主色调: `#ff6b35` (橙色活力)
- 文字色: `#ffffff` (白色) / `#b0b0b0` (高级灰)

#### 2.2 左侧组件 - 攻略分类导航
**视觉要求**:
- 宽度: 260px
- 背景: 渐变 `#1a1a1a` → `#151515`
- 标题: "艾尔登法环" (居中，22px，粗体)
- 按钮: 质感按钮，带图标和激活状态

**功能实现**:
- "开荒攻略" 按钮 (⚔️ 图标)
- "全收集攻略" 按钮 (📜 图标)
- 点击触发 Pinia `fetchGuides(tag, null)`
- 当前选中状态高亮 (橙色渐变背景)

**交互细节**:
```css
/* 按钮悬停效果 */
.sidebar-btn:hover {
  transform: translateX(4px);
  background: #2a2a2a;
}

/* 激活状态 */
.sidebar-btn.active {
  background: linear-gradient(135deg, #ff6b35 0%, #f7931e 100%);
  box-shadow: 0 4px 20px rgba(255, 107, 53, 0.3);
}
```

#### 2.3 中间组件 - 攻略流式列表
**布局结构**:
```
┌─────────────────────────────────────┐
│ 搜索框 (输入 '名刀月隐' + 回车)      │
├─────────────────────────────────────┤
│ ┌─────────────────────────────────┐ │
│ │ [图片] 标题                      │ │
│ │         简介...                 │ │
│ │         [标签] [标签]           │ │
│ └─────────────────────────────────┘ │
│ ┌─────────────────────────────────┐ │
│ │ [图片] 标题                      │ │
│ │         简介...                 │ │
│ │         [标签] [标签]           │ │
│ └─────────────────────────────────┘ │
└─────────────────────────────────────┘
```

**搜索功能**:
- 输入框 placeholder: "搜索攻略，如：名刀月隐"
- 回车事件触发搜索
- 调用 `gameStore.fetchGuides(null, keyword)`

**卡片样式** (左图右文):
- 图片区域: 200px 宽度，object-fit: cover
- 卡片: 圆角 12px，悬停上浮 4px + 阴影
- 标题: 白色 18px
- 简介: 高级灰 14px，行高 1.6
- 标签: 橙色文字，灰色背景

**展开详情功能**:
- 点击卡片: 弹出模态框显示图文详情
- 模态框: 居中显示，90% 宽度，最大高度 80vh
- 支持关闭按钮 (右上角 ✕)
- 点击遮罩层也可关闭

#### 2.4 右侧组件 - Boss 检查清单
**视觉设计**:
- 宽度: 300px
- 浮动效果: 固定定位或 sticky

**进度条组件**:
```css
.progress-bar {
  /* 橙色渐变 */
  background: linear-gradient(90deg, #ff6b35 0%, #f7931e 100%);
  /* 平滑动画 */
  transition: width 0.6s cubic-bezier(0.4, 0, 0.2, 1);
  /* 发光效果 */
  box-shadow: 0 0 20px rgba(255, 107, 53, 0.5);
}
```

**百分比显示**:
- 36px 粗体橙色文字
- 居中显示
- 下方显示 "X / Y 已完成"

**Boss 列表**:
- 复选框样式: 20px，橙色强调色
- Boss 名称 + 难度星级
- 完成状态: 删除线 + 灰色
- 难度等级颜色:
  - ★1: 绿色 (#4caf50)
  - ★2: 浅绿 (#8bc34a)
  - ★3: 橙色 (#ff9800)
  - ★4: 深橙 (#ff5722)
  - ★5: 红色 (#f44336)

**交互功能**:
- 点击复选框: 触发 `gameStore.toggleBoss(bossId)`
- 无刷新更新进度条
- 进度条平滑涨动动画

### 步骤 3: 完善请求工具 (request.js)
**目标**: 确保 Axios 配置完善

**任务**:
- [ ] 确认 baseURL 为 `/api`
- [ ] 超时时间: 10000ms
- [ ] 请求拦截器: 添加 token
- [ ] 响应拦截器: 统一错误处理
- [ ] 使用 ElMessage 显示错误提示

### 步骤 4: 添加 Loading 和 Error 状态
**目标**: 完善的加载和错误反馈

**UI 需求**:
- 攻略列表加载中: 显示骨架屏或加载动画
- 加载失败: 显示错误提示和重试按钮
- 无数据: 显示空状态提示

**实现建议**:
```vue
<!-- 加载状态 -->
<div v-if="gameStore.loading" class="loading-spinner">
  正在加载攻略...
</div>

<!-- 错误状态 -->
<div v-else-if="error" class="error-message">
  {{ error }}
  <button @click="retry">重试</button>
</div>

<!-- 空状态 -->
<div v-else-if="guides.length === 0" class="empty-state">
  暂无攻略
</div>
```

## 关键代码片段

### Pinia Store (useGameStore.js)
```javascript
// 写死的用户ID
const userId = 1

// 获取攻略列表
const fetchGuides = async (tag, keyword) => {
  loading.value = true
  try {
    const params = {}
    if (tag) params.tag = tag
    if (keyword) params.keyword = keyword
    
    const res = await request({
      url: '/guides',  // 会被代理到 /api/guides
      method: 'get',
      params
    })
    
    if (res.code === 200) {
      guides.value = res.data
    }
  } catch (error) {
    console.error('获取攻略失败:', error)
  } finally {
    loading.value = false
  }
}
```

### 卡片展开逻辑
```javascript
const toggleGuideDetail = (guide) => {
  if (expandedGuide.value?.id === guide.id) {
    expandedGuide.value = null
  } else {
    expandedGuide.value = guide
  }
}
```

### Boss 切换逻辑
```javascript
const toggleBoss = async (bossId) => {
  // 乐观更新 - 先更新UI
  const bossStatus = checklist.value.bossStatusList.find(
    item => item.boss.id === bossId
  )
  if (bossStatus) {
    bossStatus.isCompleted = !bossStatus.isCompleted
  }
  
  try {
    const res = await request({
      url: '/checklist/toggle',
      method: 'post',
      data: { bossId }
    })
    
    if (res.code === 200) {
      // 更新进度数据
      checklist.value.progressPercent = res.data.progressPercent
      checklist.value.completedBosses = res.data.completedBosses
    }
  } catch (error) {
    // 错误回滚
    if (bossStatus) {
      bossStatus.isCompleted = !bossStatus.isCompleted
    }
  }
}
```

## 验证清单

### 功能验证
- [ ] 页面加载时自动获取攻略列表
- [ ] 点击"开荒攻略"按钮筛选攻略
- [ ] 点击"全收集攻略"按钮筛选攻略
- [ ] 搜索框输入关键词回车搜索
- [ ] 点击攻略卡片展开详情
- [ ] 点击关闭按钮或遮罩关闭详情
- [ ] Boss 检查清单显示进度条
- [ ] 点击 Boss 复选框切换状态
- [ ] 进度条平滑动画更新

### UI 验证
- [ ] 暗黑背景 (#121212)
- [ ] 三栏布局正常显示
- [ ] 按钮悬停效果正常
- [ ] 卡片悬停上浮效果
- [ ] 进度条渐变和发光效果
- [ ] 响应式布局（最小宽度）

### 错误处理验证
- [ ] 网络错误时显示错误提示
- [ ] 加载状态显示
- [ ] 空数据状态显示

## 文件清单

需要修改的文件:
1. `frontend/src/stores/useGameStore.js` - 优化状态管理
2. `frontend/src/App.vue` - 完善三栏布局和交互
3. `frontend/src/utils/request.js` - 确认请求配置

## 注意事项

1. **不破坏现有功能**: 只做最小修改
2. **防御式编程**: 所有请求必须有错误处理
3. **视觉一致性**: 保持橙色主色调风格
4. **性能优化**: 避免不必要的重复请求
5. **用户体验**: 加载状态、错误反馈必须完善
