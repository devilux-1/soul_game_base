package com.soul.game.soulgamedb.controller;

import com.soul.game.soulgamedb.common.Result;
import com.soul.game.soulgamedb.dto.LoginResponse;
import com.soul.game.soulgamedb.service.SteamAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "认证管理", description = "Steam登录相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final SteamAuthService steamAuthService;

    @Value("${frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Operation(summary = "获取Steam登录URL")
    @GetMapping("/steam/url")
    public Result<Map<String, String>> getSteamAuthUrl() {
        String returnUrl = frontendUrl + "/auth/callback";
        String authUrl = steamAuthService.getSteamAuthUrl(returnUrl);
        Map<String, String> result = new HashMap<>();
        result.put("authUrl", authUrl);
        return Result.success(result);
    }

    @Operation(summary = "Steam登录回调处理")
    @GetMapping("/steam/callback")
    public Result<LoginResponse> steamCallback(@RequestParam Map<String, String> params) {
        String claimedId = params.get("openid.claimed_id");
        String steamId = steamAuthService.extractSteamIdFromOpenIdResponse(claimedId);

        if (steamId == null) {
            return Result.error("Steam认证失败");
        }

        LoginResponse loginResponse = steamAuthService.handleSteamLogin(steamId);
        return Result.success(loginResponse);
    }

    @Operation(summary = "模拟Steam登录（开发用）")
    @PostMapping("/steam/mock")
    public Result<LoginResponse> mockSteamLogin(@RequestParam String steamId) {
        LoginResponse loginResponse = steamAuthService.handleSteamLogin(steamId);
        return Result.success(loginResponse);
    }
}