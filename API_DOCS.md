# Steam 游戏 Hub API 文档

## 接口总览

| 模块 | 接口 | 方法 | 路径 | 描述 |
|------|------|------|------|------|
| 攻略模块 | 获取攻略列表 | GET | /api/guides | 获取所有攻略，支持标签和关键词搜索 |
| 攻略模块 | 获取攻略详情 | GET | /api/guides/{guideId} | 根据ID获取攻略详情，自动增加浏览次数 |
| 检查清单模块 | 获取检查清单 | GET | /api/checklist | 获取当前用户的Boss检查清单 |
| 检查清单模块 | 切换Boss状态 | POST | /api/checklist/toggle | 切换指定Boss的完成状态 |

---

## 攻略模块

### 1. 获取攻略列表

#### 接口信息
- **方法**：GET
- **路径**：`/api/guides`
- **描述**：获取所有攻略列表，支持按标签筛选和关键词搜索

#### 请求参数
| 参数名 | 类型 | 必填 | 参数类型 | 说明 |
|--------|------|------|----------|------|
| tag | string | 否 | Query | 标签筛选，如"新手"、"高难度"等 |
| keyword | string | 否 | Query | 关键词搜索，在标题、简介、内容中模糊匹配 |

#### 请求示例
```bash
# 获取所有攻略
curl http://localhost:8080/api/guides

# 按标签筛选
curl "http://localhost:8080/api/guides?tag=新手"

# 关键词搜索
curl "http://localhost:8080/api/guides?keyword=名刀月隐"

# 组合查询
curl "http://localhost:8080/api/guides?tag=新手&keyword=葛瑞克"
```

#### 响应参数
| 字段 | 类型 | 说明 |
|------|------|------|
| code | number | 状态码，200表示成功 |
| data | array | 攻略数据数组 |

#### 攻略数据结构
| 字段 | 类型 | 说明 |
|------|------|------|
| guideId | Long | 攻略ID |
| title | string | 攻略标题 |
| summary | string | 攻略简介 |
| content | string | 攻略详细内容 |
| tags | string | 标签，多个用逗号分隔 |
| bossId | Long | 关联的BossID，可为null |
| viewCount | Integer | 浏览次数 |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

#### 响应示例
```json
{
  "code": 200,
  "data": [
    {
      "guideId": 1,
      "title": "葛瑞克新手攻略",
      "summary": "适合新手挑战葛瑞克的详细攻略，包括装备推荐和战术要点。",
      "content": "葛瑞克是玩家遇到的第一个半神级Boss...",
      "tags": "新手,葛瑞克,史东薇尔城",
      "bossId": 1,
      "viewCount": 15,
      "createTime": "2024-05-21T10:30:00",
      "updateTime": "2024-05-21T10:30:00"
    }
  ]
}
```

---

### 2. 获取攻略详情

#### 接口信息
- **方法**：GET
- **路径**：`/api/guides/{guideId}`
- **描述**：根据攻略ID获取详情，访问时自动增加该攻略的浏览次数

#### 路径参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| guideId | Long | 是 | 攻略ID |

#### 请求示例
```bash
# 获取ID为1的攻略详情
curl http://localhost:8080/api/guides/1
```

#### 响应参数
| 字段 | 类型 | 说明 |
|------|------|------|
| code | number | 状态码，200表示成功 |
| data | object | 攻略详情数据，无数据时为null |

#### 响应示例
```json
{
  "code": 200,
  "data": {
    "guideId": 1,
    "title": "葛瑞克新手攻略",
    "summary": "适合新手挑战葛瑞克的详细攻略，包括装备推荐和战术要点。",
    "content": "葛瑞克是玩家遇到的第一个半神级Boss...",
    "tags": "新手,葛瑞克,史东薇尔城",
    "bossId": 1,
    "viewCount": 16,
    "createTime": "2024-05-21T10:30:00",
    "updateTime": "2024-05-21T12:45:00"
  }
}
```

---

## 检查清单模块

### 1. 获取检查清单

#### 接口信息
- **方法**：GET
- **路径**：`/api/checklist`
- **描述**：获取当前用户的Boss检查清单，包含所有Boss及其完成状态

#### 请求参数
无参数

#### 请求示例
```bash
curl http://localhost:8080/api/checklist
```

#### 响应参数
| 字段 | 类型 | 说明 |
|------|------|------|
| code | number | 状态码，200表示成功 |
| data | object | 检查清单数据 |

#### 检查清单数据结构
| 字段 | 类型 | 说明 |
|------|------|------|
| bossStatusList | array | Boss状态列表 |
| totalBosses | Long | Boss总数 |
| completedBosses | Long | 已完成的Boss数 |
| progressPercent | Integer | 进度百分比（0-100） |

#### Boss状态数据结构
| 字段 | 类型 | 说明 |
|------|------|------|
| boss | object | Boss详情 |
| isCompleted | boolean | 是否已完成 |

#### Boss详情数据结构
| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | BossID |
| name | string | Boss名称 |
| location | string | 所在位置 |
| difficultyLevel | Integer | 难度等级（1-5） |
| isOptional | boolean | 是否为可选Boss |
| description | string | 描述 |

#### 响应示例
```json
{
  "code": 200,
  "data": {
    "bossStatusList": [
      {
        "boss": {
          "id": 1,
          "name": "葛瑞克",
          "location": "史东薇尔城",
          "difficultyLevel": 2,
          "isOptional": false,
          "description": "接肢葛瑞克，史东薇尔城的领主。"
        },
        "isCompleted": true
      }
    ],
    "totalBosses": 8,
    "completedBosses": 3,
    "progressPercent": 38
  }
}
```

---

### 2. 切换Boss状态

#### 接口信息
- **方法**：POST
- **路径**：`/api/checklist/toggle`
- **描述**：切换指定Boss的完成状态，如果已完成则标记为未完成，反之亦然

#### 请求头
| 字段 | 值 | 说明 |
|------|-----|------|
| Content-Type | application/json | 请求体格式 |

#### 请求参数（Body）
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| bossId | Long | 是 | 要切换的BossID |

#### 请求示例
```bash
curl -X POST http://localhost:8080/api/checklist/toggle \
  -H "Content-Type: application/json" \
  -d "{\"bossId\": 1}"
```

#### 响应参数
| 字段 | 类型 | 说明 |
|------|------|------|
| code | number | 状态码，200表示成功 |
| data | object | 更新后的进度信息 |

#### 响应示例
```json
{
  "code": 200,
  "data": {
    "progressPercent": 50,
    "completedBosses": 4,
    "totalBosses": 8
  }
}
```

---

## 错误说明

| HTTP状态码 | 说明 |
|-----------|------|
| 200 | 请求成功 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

所有错误响应都会包含错误信息，并在前端通过 Element Plus 的 ElMessage 提示给用户。

---

## 前端对接示例

### Vue3 + Axios 调用示例

```javascript
import request from '@/utils/request'

// 获取攻略列表
export const fetchGuides = async (tag, keyword) => {
  const params = {}
  if (tag) params.tag = tag
  if (keyword) params.keyword = keyword

  const res = await request({
    url: '/guides',
    method: 'get',
    params
  })
  return res.data
}

// 获取攻略详情
export const fetchGuideDetail = async (guideId) => {
  const res = await request({
    url: `/guides/${guideId}`,
    method: 'get'
  })
  return res.data
}

// 获取检查清单
export const fetchChecklist = async () => {
  const res = await request({
    url: '/checklist',
    method: 'get'
  })
  return res.data
}

// 切换Boss状态
export const toggleBoss = async (bossId) => {
  const res = await request({
    url: '/checklist/toggle',
    method: 'post',
    data: { bossId }
  })
  return res.data
}
```

---

## 数据初始化

系统启动时会自动检查数据库是否有数据，如果没有则自动初始化以下测试数据：

### Boss列表（8个）
1. 葛瑞克（史东薇尔城，难度2）
2. 拉塔恩（盖利德，难度4）
3. 拉卡德（火山官邸，难度3，可选）
4. 玛莲妮亚（米凯拉的圣树，难度5，可选）
5. 蒙葛特（王城罗德尔，难度4）
6. 艾尔登之兽（艾尔登王座，难度5）
7. 梅瑟莫（深根底层，难度3，可选）
8. 龙王普拉顿桑克斯（法姆·亚兹拉，难度5，可选）

### 攻略列表（5个）
1. 葛瑞克新手攻略
2. 拉塔恩打法技巧
3. 玛莲妮亚究极攻略
4. 全Boss成就路线
5. 蒙葛特王城攻略
