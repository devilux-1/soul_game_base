
package com.soul.game.soulgamedb.controller;

import com.soul.game.soulgamedb.common.Result;
import com.soul.game.soulgamedb.domain.BossList;
import com.soul.game.soulgamedb.domain.UserChecklist;
import com.soul.game.soulgamedb.repository.BossListRepository;
import com.soul.game.soulgamedb.repository.UserChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;

/**
 * Boss检查清单控制器
 * 提供Boss完成状态查询和切换功能
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@RestController
@RequestMapping("/checklist")
@CrossOrigin(origins = "*")
public class ChecklistController {

    /**
     * Boss列表数据访问对象
     */
    @Autowired
    private BossListRepository bossListRepository;

    /**
     * 用户检查清单数据访问对象
     */
    @Autowired
    private UserChecklistRepository userChecklistRepository;

    /**
     * 获取用户的Boss检查清单
     * 返回所有Boss的列表及用户的完成状态、进度百分比等信息
     * 用户ID固定为1（演示用途）
     *
     * @return 包含Boss状态列表和进度信息的Result响应对象
     */
    @GetMapping
    public Result<Map<String, Object>> getChecklist() {
        // 固定用户ID为1
        Long userId = 1L;
        
        // 获取所有Boss列表 - 按难度等级升序排序（先简单后难），然后按ID排序
        List<BossList> allBosses = bossListRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        
        // 获取该用户的所有检查记录
        List<UserChecklist> userChecklists = userChecklistRepository.findByUserId(userId);

        // 构建Boss完成状态的映射，方便快速查找
        Map<Long, Boolean> completedMap = new HashMap<>();
        for (UserChecklist checklist : userChecklists) {
            completedMap.put(checklist.getBossId(), checklist.getIsCompleted());
        }

        // 组装Boss状态列表，包含Boss信息和完成状态
        List<Map<String, Object>> bossStatusList = new ArrayList<>();
        for (BossList boss : allBosses) {
            Map<String, Object> bossStatus = new HashMap<>();
            bossStatus.put("boss", boss);
            // 如果没有记录，默认为未完成
            bossStatus.put("isCompleted", completedMap.getOrDefault(boss.getId(), false));
            bossStatusList.add(bossStatus);
        }

        // 计算进度百分比
        long totalBosses = allBosses.size();
        long completedBosses = userChecklistRepository.countByUserIdAndIsCompleted(userId, true);
        int progressPercent = totalBosses > 0 ? (int) ((completedBosses * 100) / totalBosses) : 0;

        // 组装返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("bossStatusList", bossStatusList);
        result.put("totalBosses", totalBosses);
        result.put("completedBosses", completedBosses);
        result.put("progressPercent", progressPercent);

        return Result.success(result);
    }

    /**
     * 切换Boss的完成状态
     * 如果用户之前有该Boss的记录，则翻转状态；如果没有，则创建一个新记录并标记为完成
     * 用户ID固定为1（演示用途）
     *
     * @param request 包含bossId的请求体
     * @return 包含新进度百分比的Result响应对象
     */
    @PostMapping("/toggle")
    public Result<Map<String, Object>> toggleBoss(@RequestBody Map<String, Long> request) {
        // 固定用户ID为1
        Long userId = 1L;
        Long bossId = request.get("bossId");

        // 查找该用户对该Boss的现有记录
        Optional<UserChecklist> optionalChecklist = userChecklistRepository.findByUserIdAndBossId(userId, bossId);
        UserChecklist checklist;

        if (optionalChecklist.isPresent()) {
            // 有记录时，翻转完成状态
            checklist = optionalChecklist.get();
            boolean newStatus = !checklist.getIsCompleted();
            checklist.setIsCompleted(newStatus);
            // 如果标记为完成，记录完成时间；否则清空
            checklist.setCompletedAt(newStatus ? LocalDateTime.now() : null);
        } else {
            // 没有记录时，创建新记录并标记为完成
            checklist = new UserChecklist();
            checklist.setUserId(userId);
            checklist.setBossId(bossId);
            checklist.setIsCompleted(true);
            checklist.setCompletedAt(LocalDateTime.now());
        }

        // 保存记录到数据库
        userChecklistRepository.save(checklist);

        // 重新计算进度百分比
        List<BossList> allBosses = bossListRepository.findAll(Sort.by(Sort.Direction.ASC, "difficultyLevel").and(Sort.by(Sort.Direction.ASC, "id")));
        long totalBosses = allBosses.size();
        long completedBosses = userChecklistRepository.countByUserIdAndIsCompleted(userId, true);
        int progressPercent = totalBosses > 0 ? (int) ((completedBosses * 100) / totalBosses) : 0;

        // 组装返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("progressPercent", progressPercent);
        result.put("completedBosses", completedBosses);
        result.put("totalBosses", totalBosses);

        return Result.success(result);
    }
}

