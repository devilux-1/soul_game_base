package com.soul.game.soulgamedb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soul.game.soulgamedb.config.JwtConfig;
import com.soul.game.soulgamedb.domain.User;
import com.soul.game.soulgamedb.dto.LoginResponse;
import com.soul.game.soulgamedb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SteamAuthService {

    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${steam.api.key}")
    private String steamApiKey;

    @Value("${steam.api.base-url:https://api.steampowered.com}")
    private String steamApiBaseUrl;

    public String getSteamAuthUrl(String returnUrl) {
        String steamOpenIdUrl = "https://steamcommunity.com/openid/login";
        Map<String, String> params = new HashMap<>();
        params.put("openid.ns", "http://specs.openid.net/auth/2.0");
        params.put("openid.mode", "checkid_setup");
        params.put("openid.return_to", returnUrl);
        params.put("openid.realm", returnUrl);
        params.put("openid.identity", "http://specs.openid.net/auth/2.0/identifier_select");
        params.put("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select");

        StringBuilder urlBuilder = new StringBuilder(steamOpenIdUrl);
        urlBuilder.append("?");
        params.forEach((key, value) -> {
            urlBuilder.append(key).append("=").append(value).append("&");
        });
        return urlBuilder.toString();
    }

    public String extractSteamIdFromOpenIdResponse(String openIdClaimedId) {
        if (openIdClaimedId == null) {
            return null;
        }
        String prefix = "https://steamcommunity.com/openid/id/";
        if (openIdClaimedId.startsWith(prefix)) {
            return openIdClaimedId.substring(prefix.length());
        }
        return null;
    }

    public LoginResponse handleSteamLogin(String steamId) {
        User user = userRepository.findBySteamId(steamId).orElseGet(() -> {
            User newUser = new User();
            newUser.setSteamId(steamId);
            newUser.setPersonaName("Steam User");
            newUser.setAvatar("");
            newUser.setAvatarMedium("");
            newUser.setAvatarFull("");
            newUser.setProfileUrl("https://steamcommunity.com/profiles/" + steamId);
            return userRepository.save(newUser);
        });

        try {
            updateUserFromSteamApi(user);
        } catch (Exception e) {
            log.warn("Failed to update user info from Steam API: {}", e.getMessage());
        }

        String token = jwtConfig.generateToken(user.getSteamId(), user.getId());

        return new LoginResponse(
                token,
                new LoginResponse.UserInfo(
                        user.getId(),
                        user.getSteamId(),
                        user.getPersonaName(),
                        user.getAvatar(),
                        user.getAvatarMedium(),
                        user.getAvatarFull(),
                        user.getProfileUrl()
                )
        );
    }

    private void updateUserFromSteamApi(User user) {
        String url = steamApiBaseUrl + "/ISteamUser/GetPlayerSummaries/v0002/?key=" + steamApiKey + "&steamids=" + user.getSteamId();

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode players = root.path("response").path("players").get(0);

            if (players != null) {
                user.setPersonaName(players.path("personaname").asText(user.getPersonaName()));
                user.setAvatar(players.path("avatar").asText(user.getAvatar()));
                user.setAvatarMedium(players.path("avatarmedium").asText(user.getAvatarMedium()));
                user.setAvatarFull(players.path("avatarfull").asText(user.getAvatarFull()));
                user.setProfileUrl(players.path("profileurl").asText(user.getProfileUrl()));
                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user info from Steam API", e);
        }
    }
}