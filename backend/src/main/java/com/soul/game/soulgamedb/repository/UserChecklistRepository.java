
package com.soul.game.soulgamedb.repository;

import com.soul.game.soulgamedb.domain.UserChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户Boss检查清单数据访问接口
 * 继承 JpaRepository 提供基础的CRUD操作，并扩展自定义查询方法
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@Repository
public interface UserChecklistRepository extends JpaRepository<UserChecklist, Long> {

    /**
     * 根据用户ID查询该用户的所有Boss检查清单
     *
     * @param userId 用户ID
     * @return 该用户的Boss检查清单列表
     */
    List<UserChecklist> findByUserId(Long userId);

    /**
     * 根据用户ID和BossID查询特定的检查记录
     *
     * @param userId 用户ID
     * @param bossId Boss ID
     * @return 包含检查记录的Optional对象，可能为空
     */
    Optional<UserChecklist> findByUserIdAndBossId(Long userId, Long bossId);

    /**
     * 统计指定用户已完成或未完成的Boss数量
     *
     * @param userId 用户ID
     * @param isCompleted 是否已完成
     * @return 符合条件的Boss数量
     */
    long countByUserIdAndIsCompleted(Long userId, Boolean isCompleted);
}

