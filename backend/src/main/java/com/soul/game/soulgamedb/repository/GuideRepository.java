
package com.soul.game.soulgamedb.repository;

import com.soul.game.soulgamedb.domain.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 攻略数据访问接口
 * 继承 JpaRepository 提供基础的CRUD操作，并扩展自定义查询方法
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {

    /**
     * 根据关键词和标签查询攻略
     * 支持同时按关键词和标签进行筛选，参数为null时不进行该条件的筛选
     *
     * @param keyword 搜索关键词，匹配标题、简介和内容
     * @param tag 标签关键词，匹配标签字段
     * @return 符合条件的攻略列表
     */
    @Query("SELECT g FROM Guide g WHERE " +
            "(:keyword IS NULL OR g.title LIKE %:keyword% OR g.summary LIKE %:keyword% OR g.content LIKE %:keyword%) AND " +
            "(:tag IS NULL OR g.tags LIKE %:tag%)")
    List<Guide> findByKeywordAndTag(@Param("keyword") String keyword, @Param("tag") String tag);

    /**
     * 根据标签查询攻略
     * 只按标签进行筛选
     *
     * @param tag 标签关键词
     * @return 符合条件的攻略列表
     */
    @Query("SELECT g FROM Guide g WHERE g.tags LIKE %:tag%")
    List<Guide> findByTag(@Param("tag") String tag);

    /**
     * 根据关键词查询攻略
     * 在标题、简介和内容中进行模糊搜索
     *
     * @param keyword 搜索关键词
     * @return 符合条件的攻略列表
     */
    @Query("SELECT g FROM Guide g WHERE g.title LIKE %:keyword% OR g.summary LIKE %:keyword% OR g.content LIKE %:keyword%")
    List<Guide> findByKeyword(@Param("keyword") String keyword);
}

