
package com.soul.game.soulgamedb.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户Boss检查清单实体类
 * 映射数据库中的 user_checklist 表，用于记录用户对各个Boss的完成状态
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "user_checklist")
public class UserChecklist {

    /**
     * 记录ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID，不能为空，关联用户表
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Boss ID，不能为空，关联 boss_list 表
     */
    @Column(name = "boss_id", nullable = false)
    private Long bossId;

    /**
     * 是否已完成，false表示未完成，true表示已完成，默认为false
     */
    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    /**
     * 完成时间，记录Boss被标记为完成的时间
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * 备注信息，用户可以添加自定义备注，最大长度500字符
     */
    @Column(length = 500)
    private String notes;

    /**
     * 创建时间，记录清单首次创建的时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间，记录清单最后修改的时间
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

