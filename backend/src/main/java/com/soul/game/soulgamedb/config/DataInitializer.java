
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
        // 如果Boss表为空，初始化Boss数据
        if (bossListRepository.count() == 0) {
            initBossData();
        }
        // 如果攻略表为空，初始化攻略数据
        if (guideRepository.count() == 0) {
            initGuideData();
        }
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
        // 葛瑞克新手攻略
        Guide guide1 = new Guide();
        guide1.setTitle("葛瑞克新手攻略");
        guide1.setSummary("适合新手挑战葛瑞克的详细攻略，包括装备推荐和战术要点。");
        guide1.setContent("葛瑞克是玩家遇到的第一个半神级Boss。推荐使用猎犬长牙，利用战技的无敌帧来躲避葛瑞克的攻击。第二阶段葛瑞克会接上龙头，要注意龙头的喷吐攻击。");
        guide1.setTags("新手,葛瑞克,史东薇尔城");
        guide1.setBossId(1L);
        guideRepository.save(guide1);

        // 拉塔恩打法技巧
        Guide guide2 = new Guide();
        guide2.setTitle("拉塔恩打法技巧");
        guide2.setSummary("碎星将军拉塔恩的详细攻略，包括召唤NPC的时机和注意事项。");
        guide2.setContent("拉塔恩是艾尔登法环中最具史诗感的Boss战之一。一定要召唤所有可用的NPC来帮忙。在拉塔恩下马后，利用机动性绕后攻击。注意躲避流星雨。");
        guide2.setTags("拉塔恩,碎星,史诗战");
        guide2.setBossId(2L);
        guideRepository.save(guide2);

        // 玛莲妮亚究极攻略
        Guide guide3 = new Guide();
        guide3.setTitle("玛莲妮亚究极攻略");
        guide3.setSummary("女武神玛莲妮亚的全方位攻略，从装备选择到阶段分析。");
        guide3.setContent("玛莲妮亚是艾尔登法环最难的Boss之一。她会回复生命值，所以必须保持持续的压制。推荐使用高出血的武器。第二阶段她会开花，一定要尽快离开花粉范围。");
        guide3.setTags("玛莲妮亚,女武神,高难度");
        guide3.setBossId(4L);
        guideRepository.save(guide3);

        // 全Boss成就路线
        Guide guide4 = new Guide();
        guide4.setTitle("全Boss成就路线");
        guide4.setSummary("获取艾尔登法环全Boss成就的推荐路线图。");
        guide4.setContent("推荐先完成主线Boss，再去挑战可选Boss。主线顺序：葛瑞克→拉塔恩→蒙葛特→艾尔登之兽。可选Boss：拉卡德、玛莲妮亚、梅瑟莫、龙王普拉顿桑克斯等。");
        guide4.setTags("成就,路线,全Boss");
        guide4.setBossId(null);
        guideRepository.save(guide4);

        // 蒙葛特王城攻略
        Guide guide5 = new Guide();
        guide5.setTitle("蒙葛特王城攻略");
        guide5.setSummary("恶兆王蒙葛特的打法分析，王城探索要点。");
        guide5.setContent("蒙葛特是王城的最终Boss。他的攻击速度快，但前摇比较明显。保持距离，利用翻滚躲避，然后反击。可以召唤梅瑟莫的灵魂来帮忙。");
        guide5.setTags("蒙葛特,恶兆王,王城");
        guide5.setBossId(5L);
        guideRepository.save(guide5);
    }
}

