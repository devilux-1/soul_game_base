
package com.soul.game.soulgamedb.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Boss列表实体类
 * 映射数据库中的 boss_list 表，用于存储游戏Boss的基本信息
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "boss_list")
public class BossList {

    /**
     * Boss ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Boss名称，不能为空，最大长度100字符
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Boss所在位置，最大长度200字符
     */
    @Column(length = 200)
    private String location;

    /**
     * 难度等级，1-5级，默认为1
     */
    @Column(name = "difficulty_level")
    private Integer difficultyLevel = 1;

    /**
     * 是否为可选Boss，false表示必打Boss，true表示可选Boss，默认为false
     */
    @Column(name = "is_optional")
    private Boolean isOptional = false;

    /**
     * Boss描述，使用TEXT类型存储长文本
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 创建时间，记录Boss信息首次创建的时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间，记录Boss信息最后修改的时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 实体持久化前的回调方法
     * 自动设置创建时间和更新时间为当前时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 实体更新前的回调方法
     * 自动更新更新时间为当前时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

