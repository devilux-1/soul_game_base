
package com.soul.game.soulgamedb.controller;

import com.soul.game.soulgamedb.common.Result;
import com.soul.game.soulgamedb.domain.Guide;
import com.soul.game.soulgamedb.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 攻略控制器
 * 提供攻略相关的API接口，支持查询、筛选等功能
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@RestController
@RequestMapping("/guides")
@CrossOrigin(origins = "*")
public class GuideController {

    /**
     * 攻略数据访问对象
     */
    @Autowired
    private GuideRepository guideRepository;

    /**
     * 获取攻略列表
     * 支持按标签筛选和关键词模糊搜索
     *
     * @param tag 标签参数，可选，用于按标签筛选攻略
     * @param keyword 关键词参数，可选，用于在标题、简介和内容中模糊搜索
     * @return 包含攻略列表的Result响应对象
     */
    @GetMapping
    public Result<List<Guide>> getGuides(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String keyword) {
        List<Guide> guides;

        // 根据传入的参数选择不同的查询策略
        if (keyword != null && !keyword.isEmpty()) {
            // 有关键词时
            if (tag != null && !tag.isEmpty()) {
                // 同时有标签和关键词，使用组合查询
                guides = guideRepository.findByKeywordAndTag(keyword, tag);
            } else {
                // 只有关键词，只按关键词查询
                guides = guideRepository.findByKeyword(keyword);
            }
        } else if (tag != null && !tag.isEmpty()) {
            // 只有标签，只按标签查询
            guides = guideRepository.findByTag(tag);
        } else {
            // 没有参数，查询所有攻略
            guides = guideRepository.findAll();
        }

        // 返回成功响应
        return Result.success(guides);
    }

    /**
     * 根据ID获取攻略详情
     * 同时增加该攻略的浏览次数
     *
     * @param guideId 攻略ID，路径参数
     * @return 包含攻略详情的Result响应对象
     */
    @GetMapping("/{guideId}")
    public Result<Guide> getGuideById(@PathVariable Long guideId) {
        Guide guide = guideRepository.findById(guideId).orElse(null);
        if (guide != null) {
            // 增加浏览次数
            guide.setViewCount(guide.getViewCount() + 1);
            guideRepository.save(guide);
        }
        return Result.success(guide);
    }
}

