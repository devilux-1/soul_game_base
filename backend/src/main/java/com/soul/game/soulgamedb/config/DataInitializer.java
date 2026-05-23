
package com.soul.game.soulgamedb.config;

import com.soul.game.soulgamedb.domain.BossList;
import com.soul.game.soulgamedb.domain.Guide;
import com.soul.game.soulgamedb.repository.BossListRepository;
import com.soul.game.soulgamedb.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化类
 * 实现 CommandLineRunner 接口，在应用启动时自动执行数据初始化
 * 当数据库中没有Boss和攻略数据时，自动插入预设的测试数据
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@Component
public class DataInitializer implements CommandLineRunner {

    /**
     * Boss列表数据访问对象
     */
    @Autowired
    private BossListRepository bossListRepository;

    /**
     * 攻略数据访问对象
     */
    @Autowired
    private GuideRepository guideRepository;

    /**
     * 应用启动时执行的初始化方法
     * 检查数据库中是否已有数据，如果没有则初始化预设数据
     *
     * @param args 命令行参数
     * @throws Exception 初始化过程中可能抛出的异常
     */
    @Override
    public void run(String... args) throws Exception {
        // 数据初始化已禁用
        // 如需初始化数据，请执行项目根目录下的 update_guides.sql 文件
        System.out.println("[DataInitializer] 数据初始化已禁用，请手动执行SQL文件导入数据");
    }

    /**
     * 初始化Boss数据
     * 向数据库中插入预设的Boss列表数据
     * 包含艾尔登法环游戏中的8个主要Boss
     */
    private void initBossData() {
        // 葛瑞克 - 史东薇尔城
        BossList boss1 = new BossList();
        boss1.setName("葛瑞克");
        boss1.setLocation("史东薇尔城");
        boss1.setDifficultyLevel(2);
        boss1.setIsOptional(false);
        boss1.setDescription("接肢葛瑞克，史东薇尔城的领主。");
        bossListRepository.save(boss1);

        // 拉塔恩 - 盖利德
        BossList boss2 = new BossList();
        boss2.setName("拉塔恩");
        boss2.setLocation("盖利德");
        boss2.setDifficultyLevel(4);
        boss2.setIsOptional(false);
        boss2.setDescription("碎星将军拉塔恩，半神之一。");
        bossListRepository.save(boss2);

        // 拉卡德 - 火山官邸
        BossList boss3 = new BossList();
        boss3.setName("拉卡德");
        boss3.setLocation("火山官邸");
        boss3.setDifficultyLevel(3);
        boss3.setIsOptional(true);
        boss3.setDescription("亵渎君王拉卡德，火山官邸的主人。");
        bossListRepository.save(boss3);

        // 玛莲妮亚 - 米凯拉的圣树
        BossList boss4 = new BossList();
        boss4.setName("玛莲妮亚");
        boss4.setLocation("米凯拉的圣树");
        boss4.setDifficultyLevel(5);
        boss4.setIsOptional(true);
        boss4.setDescription("女武神玛莲妮亚，米凯拉的孪生妹妹。");
        bossListRepository.save(boss4);

        // 蒙葛特 - 王城罗德尔
        BossList boss5 = new BossList();
        boss5.setName("蒙葛特");
        boss5.setLocation("王城罗德尔");
        boss5.setDifficultyLevel(4);
        boss5.setIsOptional(false);
        boss5.setDescription("恶兆王蒙葛特，艾尔登王座的守护者。");
        bossListRepository.save(boss5);

        // 艾尔登之兽 - 艾尔登王座
        BossList boss6 = new BossList();
        boss6.setName("艾尔登之兽");
        boss6.setLocation("艾尔登王座");
        boss6.setDifficultyLevel(5);
        boss6.setIsOptional(false);
        boss6.setDescription("艾尔登之兽，黄金树意志的化身。");
        bossListRepository.save(boss6);

        // 梅瑟莫 - 深根底层
        BossList boss7 = new BossList();
        boss7.setName("梅瑟莫");
        boss7.setLocation("深根底层");
        boss7.setDifficultyLevel(3);
        boss7.setIsOptional(true);
        boss7.setDescription("死龙梅瑟莫，曾是葛孚雷的战友。");
        bossListRepository.save(boss7);

        // 龙王普拉顿桑克斯 - 法姆·亚兹拉
        BossList boss8 = new BossList();
        boss8.setName("龙王普拉顿桑克斯");
        boss8.setLocation("法姆·亚兹拉");
        boss8.setDifficultyLevel(5);
        boss8.setIsOptional(true);
        boss8.setDescription("龙王普拉顿桑克斯，古龙时代的艾尔登之王。");
        bossListRepository.save(boss8);
    }

    /**
     * 初始化攻略数据
     * 向数据库中插入预设的攻略列表数据
     * 包含针对各个Boss的攻略和通用攻略
     */
    private void initGuideData() {
        // ========== 开荒攻略 ==========
        
        // 开荒攻略1：新手入门指南
        Guide guide1 = new Guide();
        guide1.setTitle("艾尔登法环新手入门完全指南");
        guide1.setSummary("为刚开始游戏的玩家提供全面的入门指南，包括角色创建、赐福选择和基础操作教学。");
        guide1.setContent("欢迎来到交界地！作为新手玩家，你需要了解以下内容：\n\n一、角色创建建议\n1. 职业选择：武士适合新手战士流派，血质隐子适合暗杀了流派\n2. 赐福选择：推荐选择\"野兽眼眸\"，提供额外的感知能力\n3. 背景选择：推荐\"流浪民族\"，获得更好的机动性\n\n二、基础操作\n1. 移动：WASD控制移动\n2. 攻击：鼠标左键轻攻击，右键重攻击\n3. 跳跃：空格键\n4. 防御：按住右键\n5. 使用道具：Q键\n6. 物品栏：Tab键\n\n三、战斗技巧\n1. 观察敌人攻击前摇，及时翻滚\n2. 善用锁定系统（Tab键切换目标）\n3. 保持耐心，不要贪刀\n4. 利用地形和高度优势\n5. 召唤骨灰帮助战斗\n\n四、升级建议\n1. 优先提升生命值（VIG）到40\n2. 提升耐力（END）到30\n3. 根据武器类型选择主属性\n\n祝你游戏愉快！");
        guide1.setTags("开荒攻略,新手,入门指南,基础教学");
        guideRepository.save(guide1);

        // 开荒攻略2：史东薇尔城探索指南
        Guide guide2 = new Guide();
        guide2.setTitle("史东薇尔城完整探索攻略");
        guide2.setSummary("详细讲解史东薇尔城的探索路线、隐藏物品位置以及葛瑞克Boss战攻略。");
        guide2.setContent("史东薇尔城是玩家遇到的第一个大型区域，这里提供完整探索指南：\n\n一、探索路线\n1. 从宁姆格福的圣人桥出发\n2. 进入城墙外侧，清理沿途敌人\n3. 找到大门后击败守门的恶兆之子\n4. 进入城墙内部\n5. 探索各个塔楼和房间\n6. 最终到达葛瑞克Boss房\n\n二、重要物品位置\n1. 圣杯泪滴×2：城墙外侧和内部各一个\n2. 锻造石×3：分布在各个房间\n3. 战灰：史东薇尔城的某处可以获得\n4. 骨灰：击杀特定敌人后获得\n\n三、隐藏区域\n1. 主建筑后方有隐藏通道\n2. 塔楼顶层可以俯瞰整个区域\n3. 地下室有珍贵的锻造石\n\n四、葛瑞克Boss战要点\n1. 第一阶段：保持距离，等待他攻击后的破绽\n2. 第二阶段：龙吐息伤害很高，看到龙头要立即跑开\n3. 推荐武器：猎犬长牙、熨斗卵石\n4. 可以召唤NPC和骨灰帮助战斗");
        guide2.setTags("开荒攻略,史东薇尔城,葛瑞克,探索指南");

        guideRepository.save(guide2);

        // 开荒攻略3：早期武器推荐
        Guide guide3 = new Guide();
        guide3.setTitle("早期强力武器获取与推荐");
        guide3.setSummary("推荐游戏前期的强力武器，包括获取位置和加点建议。");
        guide3.setContent("游戏前期的武器选择非常重要，以下是强力武器推荐：\n\n一、猎犬长牙\n1. 获取方式：在史东薇尔城的拾荒者的牢房找NPC购买\n2. 特点：出血效果优秀，战技有无敌帧\n3. 加点：主敏捷，副体力\n4. 适合流派：敏捷出血流\n\n二、熨斗卵石\n1. 获取方式：宁姆格福的遁世商人处购买\n2. 特点：战技伤害高，可蓄力\n3. 加点：主力量，副体力\n4. 适合流派：力量战技流\n\n三、打刀\n1. 获取方式：盖利德的商人处购买\n2. 特点：连续攻击积累出血值\n3. 加点：主敏捷\n4. 适合流派：武士流派\n\n四、十字镐\n1. 获取方式：史东薇尔城某处拾取\n2. 特点：战技可以破坏敌人护甲\n3. 加点：主力量\n4. 适合流派：力量粉碎流\n\n五、武器强化建议\n1. 优先强化一把主力武器\n2. 找到矿工工会提升锻造石获取\n3. 中期可以开始制作战灰");
        guide3.setTags("开荒攻略,武器推荐,获取位置,加点建议");

        guideRepository.save(guide3);

        // 开荒攻略4：骨灰系统详解
        Guide guide4 = new Guide();
        guide4.setTitle("骨灰系统完全指南");
        guide4.setSummary("详细介绍游戏中的骨灰系统，包括获取方法、最强骨灰推荐和升级指南。");
        guide4.setContent("骨灰是艾尔登法环的重要系统，以下是详细指南：\n\n一、骨灰获取方法\n1. 主线任务奖励\n2. 商人购买\n3. 击杀特定敌人掉落\n4. 探索隐藏区域\n\n二、强力骨灰推荐\n\n1. 离群野狼\n   - 特点：攻击速度快，数量多\n   - 适用：需要快速输出的Boss\n   - 获取：宁姆格福的隐藏洞穴\n\n2. 腐败野狗\n   - 特点：可以附加腐败状态\n   - 适用：长时间Boss战\n   - 获取：盖利德区域\n\n3. 壶战士亚历山大\n   - 特点：生命值高，可以吸引仇恨\n   - 适用：需要分散注意力的战斗\n   - 获取：完成支线任务\n\n三、骨灰升级\n1. 找到\"癫火村的魔法师\"升级骨灰\n2. 需要灵魂和铃兰\n3. 升级可以提升骨灰的伤害和生命值\n\n四、使用技巧\n1. Boss战开始时召唤骨灰\n2. 骨灰死亡后需要等待才能再次召唤\n3. 部分Boss对骨灰有特殊机制");
        guide4.setTags("开荒攻略,骨灰系统,召唤物,升级指南");

        guideRepository.save(guide4);

        // ========== 全收集攻略 ==========

        // 全收集攻略1：全传说武器收集指南
        Guide guide5 = new Guide();
        guide5.setTitle("全传说武器位置与收集指南");
        guide5.setSummary("包含艾尔登法环中所有传说武器的详细位置和获取方式。");
        guide5.setContent("以下是所有传说武器的完整收集指南：\n\n一、大卢恩武器\n1. 黄金律法拉达冈的雕像\n   - 位置：王城罗德尔\n   - 需求：基础力量39、灵巧17、信仰46\n   - 效果：释放黄金律法的神圣光芒\n\n2. 葛孚雷的肖像\n   - 位置：王城罗德尔\n   - 需求：力量46\n   - 效果：释放流星雨攻击\n\n二、传说护符\n1. 黄金树的根源\n   - 位置：桂奥尔龙墓\n   - 效果：大幅提升负重\n\n2. 玛莉卡的烙印\n   - 位置：永恒之城\n   - 效果：提升所有能力值\n\n三、传说骨灰\n1. 黑刀狄希\n   - 位置：王城罗德尔\n   - 效果：召唤多个黑刀刺客\n   - 伤害：暗属性\n\n2. 米莉森的绿花\n   - 位置：圣树\n   - 效果：召唤米莉森协助战斗\n\n四、收集建议\n1. 先完成所有NPC支线任务\n2. 探索每个区域的隐藏地点\n3. 注意Boss的专属掉落物品\n4. 部分物品需要特定结局才能获得");
        guide5.setTags("全收集攻略,传说武器,收集指南,全物品");

        guideRepository.save(guide5);

        // 全收集攻略2：全NPC支线任务攻略
        Guide guide6 = new Guide();
        guide6.setTitle("全NPC支线任务完整攻略");
        guide6.setSummary("详细讲解所有NPC的支线任务流程，包括隐藏结局触发条件。");
        guide6.setContent("以下是所有重要NPC支线任务的完整指南：\n\n一、菈妮支线\n1. 起始：魔法师塔的蕾娜\n2. 流程：\n   - 在菈妮魔法师塔与蕾娜对话\n   - 获得\"卡利亚颠倒像\"\n   - 调查艾吉奥雕像\n   - 进入卡利亚书斋\n   - 获取\"魔女帽子\"\n   - 在月光祭坛找到菈妮\n   - 完成灵魂战斗\n   - 返回魔法师塔获得奖励\n3. 奖励：菈妮的追随者、骨灰、传说魔法\n\n二、亚历山大支线（壶战士）\n1. 起始：宁姆格福的洞穴\n2. 流程：\n   - 第一次相遇：帮助他离开洞穴\n   - 第二次相遇：盖利德的巨人山顶\n   - 第三次相遇：火山官邸区域\n   - 最终：帮助他进入碎片王座\n3. 奖励：亚历山大的内容、强力战灰\n\n三、布莱泽支线\n1. 起始：雾林废墟\n2. 流程：\n   - 在雾林废墟找到布莱泽\n   - 获得\"布莱泽的信\"\n   - 交给蒂亚弗雷德\n   - 在卡兹利邸邸找到布莱泽\n   - 完成布莱泽的战斗\n3. 奖励：龙信仰寺院钥匙、布莱泽的套装\n\n四、狄亚罗斯支线\n1. 起始：壶村\n2. 流程：\n   - 在壶村找到狄亚罗斯\n   - 帮助他离开壶村\n   - 在火山官邸再次遇到他\n   - 帮助他完成使命\n3. 奖励：壶头的护身符\n\n五、注意事项\n1. 部分支线有互斥性\n2. 某些支线需要特定等级才能继续\n3. 错过关键步骤会导致支线失败\n4. 建议在推主线前先完成支线");
        guide6.setTags("全收集攻略,NPC支线,任务攻略,隐藏结局");

        guideRepository.save(guide6);

        // 全收集攻略3：全地图探索指南
        Guide guide7 = new Guide();
        guide7.setTitle("全地图探索与物品收集完整攻略");
        guide7.setSummary("包含宁姆格福、利耶尼亚、盖利德等所有区域的探索要点和物品位置。");
        guide7.setContent("以下是全地图探索完整指南：\n\n一、宁姆格福（初始区域）\n\n重要地点：\n1. 风暴根脚的地下墓地\n   - 奖励：战灰、死根\n   \n2. 蒙流岸的洞窟\n   - 奖励：大型陆地生物的沿无\n   \n3. 遁世商人的店\n   - 商品：熨斗卵石、旅者的护身符\n\n二、利耶尼亚（魔法区域）\n\n重要地点：\n1. 卡利亚书斋\n   - 奖励：卡利亚魔法\n   - 解谜：使用卡利亚颠倒像\n\n2. 魔法学院雷亚卢卡利亚\n   - Boss：满月女王雷娜菈\n   - 奖励：大量魔法和学院钥匙\n\n3. 驿站街的废墟\n   - 隐藏：地下墓地入口\n   - 奖励：高级魔法\n\n三、盖利德（红土区域）\n\n重要地点：\n1. 盖利德的奇列姆\n   - Boss：碎星将军拉塔恩\n   - 支线：帮助红军或蓝军\n\n2. 格密尔的火山官邸\n   - Boss：亵渎君王拉卡德\n   - 隐藏：寻找完美的王之复苏\n\n3. 崩溃者的墓地\n   - 特殊：强制Boss战\n   - 奖励：传说护符\n\n四、王城罗德尔\n\n重要地点：\n1. 黄金树大教堂\n   - 奖励：大量卢恩\n\n2. 王座边缘的幻影\n   - Boss：恶兆王蒙葛特\n\n五、收集建议\n1. 每个区域至少探索90%\n2. 不要错过任何地下室\n3. 地下室通常有珍贵物品\n4. 注意隐藏的NPC位置");
        guide7.setTags("全收集攻略,地图探索,物品收集,全区域");

        guideRepository.save(guide7);

        // 全收集攻略4：全套装护甲收集
        Guide guide8 = new Guide();
        guide8.setTitle("全套装护甲位置与属性详解");
        guide8.setSummary("详细介绍所有套装护甲的获取位置、属性加成和套装效果。");
        guide8.setContent("以下是所有套装护甲的完整收集指南：\n\n一、传说套装\n\n1. 布莱泽的套装\n   - 获得方式：完成布莱泽支线\n   - 套装效果：龙耐性大幅提升\n   - 适用：龙信仰战士\n\n2. 死之子的套装\n   - 获得方式：癫火村NPC处购买\n   - 套装效果：提高呢喃者伤害\n   - 适用：呢喃者流派\n\n3. 蒙葛特的套装\n   - 获得方式：王城罗德尔Boss掉落\n   - 套装效果：抗性提升\n   - 适用：综合防御型\n\n二、职业套装\n\n1. 武士套装\n   - 获得：初始装备\n   - 特点：轻便、敏捷加成\n\n2. 魔法师套装\n   - 获得：学院内部拾取\n   - 特点：魔力消耗减少\n\n3. 盗贼套装\n   - 获得：宁姆格福地下\n   - 特点：隐匿性能提升\n\n三、特殊套装\n\n1. 黑刀套装\n   - 获得：黑刀刺客掉落\n   - 特点：暗属性攻击提升\n\n2. 黄金树套装\n   - 获得：王城各区域\n   - 特点：信仰系魔法增强\n\n四、收集建议\n1. 优先收集主线相关套装\n2. 注意敌对NPC掉落\n3. 部分套装需要特定结局\n4. 护甲强化同样重要");
        guide8.setTags("全收集攻略,套装护甲,装备收集,属性详解");

        guideRepository.save(guide8);

        // 全收集攻略5：全魔法和战灰收集
        Guide guide9 = new Guide();
        guide9.setTitle("全魔法、战技和祈祷收集指南");
        guide9.setSummary("详细列出所有魔法书、战灰和祈祷的获取位置。");
        guide9.setContent("以下是所有魔法和战技的完整收集指南：\n\n一、魔法类\n\n1. 卡利亚魔法系\n   - 获取：卡利亚书斋、学院内部\n   - 代表魔法：卡利亚迅剑、卡利亚贯刺\n   - 特点：可与武器战技叠加\n\n2. 流星魔法系\n   - 获取：利耶尼亚各区域\n   - 代表魔法：帚星、毁灭流星\n   - 特点：高伤害魔法\n\n3. 冰霜魔法系\n   - 获取：永恒之城\n   - 代表魔法：冻结波动\n   - 特点：附加冰冻效果\n\n二、战技类\n\n1. 剑士战技\n   - 获取：各武器自带\n   - 代表：滑斩、居合\n   - 特点：适合快节奏战斗\n\n2. 重武器战技\n   - 获取：特定武器\n   - 代表：碎月击、强力重击\n   - 特点：高伤害但慢\n\n3. 特殊战技\n   - 获取：任务奖励或特殊武器\n   - 代表：市长老、狗道\n   - 特点：独特效果\n\n三、祈祷类\n\n1. 黄金树祈祷\n   - 获取：王城各教堂\n   - 代表：黄金树之力、恩典hp\n   - 特点：治疗和buff\n\n2. 龙祈祷\n   - 获取：龙飨教堂\n   - 代表：龙咬、龙喷吐\n   - 特点：龙信仰专属\n\n3. 死王子祈祷\n   - 获取：迪可达斯大升降机\n   - 代表：黑炎系列\n   - 特点：暗属性\n\n四、收集建议\n1. 优先收集通用强力魔法\n2. 记忆石同样重要\n3. 特定魔法需要特定信仰\n4. 部分需要完成支线");
        guide9.setTags("全收集攻略,魔法,战技,祈祷收集");

        guideRepository.save(guide9);

        // Boss攻略：拉塔恩
        Guide guide10 = new Guide();
        guide10.setTitle("碎星将军拉塔恩完全攻略");
        guide10.setSummary("详细介绍拉塔恩 Boss战的所有阶段、招式分析和应对策略。");
        guide10.setContent("碎星将军拉塔恩是艾尔登法环最具史诗感的Boss之一。\n\n一、Boss介绍\n- 位置：盖利德的艾吉奥草原\n- 难度：★★★★☆\n- 类型：大型人类Boss\n- 特点：可以召唤NPC协助\n\n二、第一阶段（马上）\n\n主要招式：\n1. 箭雨\n   - 前摇：拉弓瞄天空\n   - 应对：保持移动\n   - 伤害：中\n\n2. 冲锋\n   - 前摇：马加速\n   - 应对：向两侧跑\n   - 伤害：高\n\n3. 爆炸箭\n   - 前摇：红色光芒\n   - 应对：立即向内翻滚\n   - 伤害：高\n\n三、第二阶段（下马后）\n\n主要招式：\n1. 重力攻击\n   - 前摇：跳起\n   - 应对：向内或向外跑\n   - 伤害：极高\n\n2. 流星雨\n   - 前摇：手臂举起\n   - 应对：靠近Boss\n   - 伤害：高\n\n3. 近战连招\n   - 前摇：武器发光\n   - 应对：准确翻滚\n   - 伤害：高\n\n四、推荐打法\n\n1. 召唤所有NPC\n2. 使用远程魔法攻击\n3. 保持距离观察\n4. 第二阶段贴近输出\n5. 携带解毒药\n\n五、推荐装备\n- 武器：任意+20以上\n- 护符：拉塔恩恩惠\n- 骨灰：高伤害骨灰\n\n六、奖励\n- 记忆代：拉塔恩的核心\n- 套装：拉塔恩套装\n- 卢恩：大量卢恩");
        guide10.setTags("全收集攻略,拉塔恩,碎星,Boss攻略");

        guideRepository.save(guide10);

        // Boss攻略：玛莲妮亚
        Guide guide11 = new Guide();
        guide11.setTitle("女武神玛莲妮亚究极攻略");
        guide11.setSummary("详细分析女武神玛莲妮亚的所有招式和最佳应对策略。");
        guide11.setContent("女武神玛莲妮亚是艾尔登法环最具挑战性的Boss。\n\n一、Boss介绍\n- 位置：米凯拉的圣树\n- 难度：★★★★★\n- 类型：大型人类Boss\n- 特点：可以回血\n\n二、第一阶段\n\n主要招式：\n1. 水乌月\n   - 前摇：举起武器\n   - 应对：向内翻滚\n   - 伤害：中\n   - 备注：可以回血，注意\n\n2. 突刺连击\n   - 前摇：后仰\n   - 应对：向内或向外\n   - 伤害：中\n\n3. 跳跃攻击\n   - 前摇：跳起\n   - 应对：向内或向外跑\n   - 伤害：高\n\n4. 花瓣风暴\n   - 前摇：旋转\n   - 应对：远离\n   - 伤害：极高\n\n三、第二阶段（开花后）\n\n主要招式：\n1. 开花\n   - 前摇：中心开花\n   - 应对：立即向外跑\n   - 效果：全屏花瓣攻击\n   - 备注：最重要的一招\n\n2. 幻影攻击\n   - 前摇：旋转武器\n   - 应对：看准时机翻滚\n   - 伤害：极高\n\n3. 快速连击\n   - 前摇：蹲下\n   - 应对：精准翻滚\n   - 伤害：高\n\n四、推荐打法\n\n1. 使用出血武器\n2. 保持持续压制\n3. 看到她开花立即跑开\n4. 不要贪刀\n5. 召唤NPC分担仇恨\n\n五、推荐装备\n- 武器：蒙加特的爪子（出血）\n- 护符：反常诱惑\n- 骨灰：离群野狼\n- 道具：解毒药、腐败体力药\n\n六、奖励\n- 记忆代：米凯拉的核心\n- 套装：玛莲妮亚套装\n- 结局道具：米凯拉的针");
        guide11.setTags("全收集攻略,玛莲妮亚,女武神,Boss攻略");

        guideRepository.save(guide11);
    }
}

