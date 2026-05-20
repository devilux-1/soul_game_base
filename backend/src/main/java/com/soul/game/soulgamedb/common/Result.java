package com.soul.game.soulgamedb.common;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 统一响应结果封装类
 * 用于统一封装后端返回的数据，包括成功响应和失败响应
 *
 * @param <T> 返回数据的泛型类型
 * @author Soul Game Team
 * @since 1.0.0
 */
@Data
public class Result<T> {

    // 响应状态码
    private Integer code;

    // 响应消息
    private String message;

    // 响应数据
    private T data;

    // 响应时间戳
    private LocalDateTime timestamp;

    /**
     * 私有构造函数，防止外部直接实例化
     * 使用静态方法创建实例
     */
    private Result() {
        // 初始化时间戳为当前时间
        this.timestamp = LocalDateTime.now();
    }

    /**
     * 成功响应方法（无数据）
     *
     * @param <T> 泛型类型
     * @return 成功的Result对象
     */
    public static <T> Result<T> success() {
        // 创建Result对象
        Result<T> result = new Result<>();
        // 设置状态码为200
        result.setCode(200);
        // 设置成功消息
        result.setMessage("操作成功");
        // 返回Result对象
        return result;
    }

    /**
     * 成功响应方法（有数据）
     *
     * @param data 返回的数据
     * @param <T> 泛型类型
     * @return 成功的Result对象
     */
    public static <T> Result<T> success(T data) {
        // 创建Result对象
        Result<T> result = new Result<>();
        // 设置状态码为200
        result.setCode(200);
        // 设置成功消息
        result.setMessage("操作成功");
        // 设置返回数据
        result.setData(data);
        // 返回Result对象
        return result;
    }

    /**
     * 成功响应方法（有数据和自定义消息）
     *
     * @param data 返回的数据
     * @param message 自定义消息
     * @param <T> 泛型类型
     * @return 成功的Result对象
     */
    public static <T> Result<T> success(T data, String message) {
        // 创建Result对象
        Result<T> result = new Result<>();
        // 设置状态码为200
        result.setCode(200);
        // 设置自定义消息
        result.setMessage(message);
        // 设置返回数据
        result.setData(data);
        // 返回Result对象
        return result;
    }

    /**
     * 失败响应方法（默认状态码）
     *
     * @param message 错误消息
     * @param <T> 泛型类型
     * @return 失败的Result对象
     */
    public static <T> Result<T> error(String message) {
        // 创建Result对象
        Result<T> result = new Result<>();
        // 设置状态码为500
        result.setCode(500);
        // 设置错误消息
        result.setMessage(message);
        // 返回Result对象
        return result;
    }

    /**
     * 失败响应方法（自定义状态码）
     *
     * @param code 自定义状态码
     * @param message 错误消息
     * @param <T> 泛型类型
     * @return 失败的Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        // 创建Result对象
        Result<T> result = new Result<>();
        // 设置自定义状态码
        result.setCode(code);
        // 设置错误消息
        result.setMessage(message);
        // 返回Result对象
        return result;
    }
}
