package com.soul.game.soulgamedb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserInfo user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private Long id;
        private String steamId;
        private String personaName;
        private String avatar;
        private String avatarMedium;
        private String avatarFull;
        private String profileUrl;
    }
}