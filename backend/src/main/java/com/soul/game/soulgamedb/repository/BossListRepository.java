
package com.soul.game.soulgamedb.repository;

import com.soul.game.soulgamedb.domain.BossList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Boss列表数据访问接口
 * 继承 JpaRepository 提供基础的CRUD操作
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@Repository
public interface BossListRepository extends JpaRepository<BossList, Long> {
}

