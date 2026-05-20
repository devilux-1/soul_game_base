package com.soul.game.soulgamedb.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义业务异常类，用于处理业务逻辑中的异常情况
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    // HTTP状态码
    private final HttpStatus status;

    /**
     * 使用指定的错误消息和HTTP状态码构造业务异常
     *
     * @param message 错误消息
     * @param status HTTP状态码
     */
    public BusinessException(String message, HttpStatus status) {
        // 调用父类构造函数设置错误消息
        super(message);
        // 初始化HTTP状态码
        this.status = status;
    }

    /**
     * 使用指定的错误消息构造业务异常，默认HTTP状态码为400 BAD_REQUEST
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        // 调用自身构造函数，设置默认状态码为400
        this(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 获取HTTP状态码
     *
     * @return HTTP状态码对象
     */
    public HttpStatus getStatus() {
        // 返回HTTP状态码
        return status;
    }
}
