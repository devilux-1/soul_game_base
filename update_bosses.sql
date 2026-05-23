-- ============================================
-- 艾尔登法环 Boss 列表完整数据脚本
-- 包含：本体 + DLC《黄金树幽影》所有 Boss
-- 按难度等级排序：先简单后难
-- ============================================

-- 清空现有数据
TRUNCATE TABLE boss_list;

-- ============================================
-- 本体 Boss（按难度等级升序）
-- ============================================

-- 难度 2：新手区域
INSERT INTO boss_list (name, location, difficulty_level, is_optional, description) VALUES
('大树守卫', '宁姆格福-黄金树脚下', 2, 1, '黄金树的忠诚守卫者，身穿金色铠甲'),
('飞龙亚基尔', '宁姆格福-亚基尔湖', 2, 1, '喷吐火焰的古老巨龙'),
('葛瑞克', '史东薇尔城', 2, 0, '接肢贵族后裔，守护史东薇尔城的半神，持有葛瑞克大卢恩');

-- 难度 3：前期进阶
INSERT INTO boss_list (name, location, difficulty_level, is_optional, description) VALUES
('熔炉骑士', '史东薇尔城-城墙', 3, 1, '古老的骑士，掌握多种战斗技艺'),
('猎犬骑士达瑞威尔', '宁姆格福-封林废墟', 3, 1, '迅捷的猎犬骑士，使用弯刀'),
('满月女王蕾娜菈', '利耶尼亚湖-雷亚卢卡利亚学院', 3, 0, '卡利亚王室女王，月之魔法大师，持有蕾娜菈大卢恩');

-- 难度 4：中期挑战
INSERT INTO boss_list (name, location, difficulty_level, is_optional, description) VALUES
('艾丝缇', '利耶尼亚-永恒之城诺克隆恩', 4, 1, '来自群星的生物，释放星辉魔法'),
('仿身泪滴', '永恒之城-黑夜神域', 4, 1, '模仿玩家角色的灵体 Boss'),
('恶兆妖鬼玛尔基特', '史东薇尔城入口/王城外围', 4, 0, '蒙格特的幻影形态，多次阻挡褪色者'),
('拉塔恩将军', '盖利德-红狮子城', 4, 0, '碎星将军，重力魔法使用者，持有拉塔恩大卢恩'),
('拉卡德', '火山官邸-格密尔火山', 4, 1, '亵渎君王，蛇身怪物，持有拉卡德大卢恩');

-- 难度 5：后期BOSS
INSERT INTO boss_list (name, location, difficulty_level, is_optional, description) VALUES
('神皮双人组', '渐近王城-龙殿', 5, 0, '神皮使徒与神皮贵族的组合，守护黑剑封印'),
('蒙格特', '王城罗德尔-埃尔登王座', 5, 0, '恶兆之王，守护黄金树的君王，持有蒙格特大卢恩'),
('火焰巨人', '巨人山顶', 5, 0, '巨人之战最后的幸存者，守护火焰大锅'),
('黑剑玛利喀斯', '法姆·亚兹拉-时空夹缝', 5, 0, '命定之死的管理者，玛莉卡的影子'),
('龙王普拉顿桑克斯', '法姆·亚兹拉-中心', 5, 1, '远古时代的龙王，掌控雷电'),
('艾尔登之兽', '艾尔登王座-最终战场', 5, 0, '艾尔登之兽，游戏的最终 Boss'),
('玛莲妮亚', '米凯拉的圣树-最底层', 5, 1, '女武神，从未败北的剑士，持有玛莲妮亚大卢恩');

-- ============================================
-- DLC《黄金树幽影》Boss（按难度等级升序）
-- ============================================

-- DLC 难度 2
INSERT INTO boss_list (name, location, difficulty_level, is_optional, description) VALUES
('蝎子那伽', '幽影城入口', 2, 1, 'DLC 初始区域的守门 Boss');

-- DLC 难度 3
INSERT INTO boss_list (name, location, difficulty_level, is_optional, description) VALUES
('舞蹈家', '幽影城-舞厅', 3, 1, '优雅的舞者，使用双刀'),
('暗影士兵', '幽影城-城墙', 2, 1, '梅瑟莫的士兵军团'),
('石像鬼', '古遗迹深处', 3, 1, '守护古代秘密的石像守卫'),
('黄金河马', '青蓝海岸', 3, 1, '奇特的水生生物 Boss');

-- DLC 难度 4
INSERT INTO boss_list (name, location, difficulty_level, is_optional, description) VALUES
('野兽人但丁', '幽影城-监狱', 4, 1, '被囚禁的野兽人战士'),
('狂火贝尔', '燎原荒野-火山区域', 4, 1, '操控狂火的战士'),
('蜘蛛手', '深渊洞穴', 4, 1, '多手的恐怖怪物'),
('杜尔加', '焦土荒野', 4, 1, '流浪的战士，使用巨型武器'),
('腐化龙族', '腐败沼泽', 4, 1, '被腐败侵蚀的巨龙');

-- DLC 难度 5
INSERT INTO boss_list (name, location, difficulty_level, is_optional, description) VALUES
('梅瑟莫', '幽影城-王座', 5, 0, '穿刺之王，幽影地的统治者，持有梅瑟莫大卢恩'),
('拉塔恩（DLC）', '焦土荒野-战场遗迹', 5, 1, '年轻时期的拉塔恩将军，DLC 中的回忆之战'),
('芙莉德', '幽影城-尖塔', 5, 1, '血焰骑士，梅瑟莫的忠实部下'),
('艾隆美尔', '青蓝海岸-海底神殿', 5, 1, '海中的古老存在，触手怪物');

-- ============================================
-- 验证插入结果
-- ============================================

SELECT
    COUNT(*) AS total_bosses,
    SUM(CASE WHEN is_optional = 0 THEN 1 ELSE 0 END) AS main_story_bosses,
    SUM(CASE WHEN is_optional = 1 THEN 1 ELSE 0 END) AS optional_bosses,
    SUM(CASE WHEN difficulty_level = 2 THEN 1 ELSE 0 END) AS level_2,
    SUM(CASE WHEN difficulty_level = 3 THEN 1 ELSE 0 END) AS level_3,
    SUM(CASE WHEN difficulty_level = 4 THEN 1 ELSE 0 END) AS level_4,
    SUM(CASE WHEN difficulty_level = 5 THEN 1 ELSE 0 END) AS level_5
FROM boss_list;

-- 查看所有 Boss 列表（按难度升序）
SELECT id, name, location, difficulty_level,
       CASE WHEN is_optional = 0 THEN '主线' ELSE '可选' END AS boss_type
FROM boss_list
ORDER BY difficulty_level ASC, id ASC;
