
package com.soul.game.soulgamedb.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 攻略实体类
 * 映射数据库中的 guide 表，用于存储游戏攻略相关信息
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "guide")
public class Guide {

    /**
     * 攻略ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guide_id")
    private Long guideId;

    /**
     * 攻略标题，不能为空，最大长度200字符
     */
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * 攻略简介，使用TEXT类型存储长文本
     */
    @Column(columnDefinition = "TEXT")
    private String summary;

    /**
     * 攻略详细内容，使用TEXT类型存储长文本
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 攻略标签，多个标签用逗号分隔，最大长度500字符
     */
    @Column(length = 500)
    private String tags;

    /**
     * 关联的Boss ID，对应 boss_list 表的主键
     */
    @Column(name = "boss_id")
    private Long bossId;

    /**
     * 浏览次数，默认为0
     */
    @Column(name = "view_count")
    private Integer viewCount = 0;

    /**
     * 创建时间，记录攻略首次创建的时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间，记录攻略最后修改的时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 实体持久化前的回调方法
     * 自动设置创建时间和更新时间为当前时间
     */
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    /**
     * 实体更新前的回调方法
     * 自动更新更新时间为当前时间
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}

