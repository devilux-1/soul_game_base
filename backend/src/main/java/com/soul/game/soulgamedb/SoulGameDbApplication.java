package com.soul.game.soulgamedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Soul Game Database项目主启动类
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@SpringBootApplication
public class SoulGameDbApplication {

    /**
     * 应用程序入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(SoulGameDbApplication.class, args);
    }
}
