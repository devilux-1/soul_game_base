package com.soul.game.soulgamedb.exception;

import com.soul.game.soulgamedb.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，统一处理应用中抛出的异常
 * 使用Result类统一封装返回数据
 *
 * @author Soul Game Team
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param ex 业务异常对象
     * @return 包含错误信息的Result响应实体
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessException(BusinessException ex) {
        // 使用Result.error()方法创建错误响应，状态码使用异常中定义的HTTP状态码值
        Result<?> result = Result.error(ex.getStatus().value(), ex.getMessage());

        // 返回带有错误状态的响应实体
        return new ResponseEntity<>(result, ex.getStatus());
    }

    /**
     * 处理运行时异常
     *
     * @param ex 运行时异常对象
     * @return 包含错误信息的Result响应实体
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result<?>> handleRuntimeException(RuntimeException ex) {
        // 打印异常堆栈信息，便于调试
        ex.printStackTrace();

        // 使用Result.error()方法创建错误响应，使用500状态码和友好提示信息
        Result<?> result = Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误，请稍后重试");

        // 返回带有500状态的响应实体
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理其他所有未捕获的异常
     *
     * @param ex 异常对象
     * @return 包含错误信息的Result响应实体
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(Exception ex) {
        // 打印异常堆栈信息，便于调试
        ex.printStackTrace();

        // 使用Result.error()方法创建错误响应，使用500状态码和友好提示信息
        Result<?> result = Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求处理失败，请稍后重试");

        // 返回带有500状态的响应实体
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
