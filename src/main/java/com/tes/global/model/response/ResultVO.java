package com.tes.global.model.response;

import lombok.Getter;

@Getter
public class ResultVO<T> {
    private boolean success;
    private String message;
    private T result;
    
    private ResultVO(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }
    
    public static <T> ResultVO<T> of(boolean success, String message, T result) {
        return new ResultVO<>(success, message, result);
    }

    public static <T> ResultVO<T> success(T result) {
        return ResultVO.of(true, "OK", result);
    }

    public static <T> ResultVO<T> success(String msg) {
        return ResultVO.of(true, msg, null);
    }

    public static <T> ResultVO<T> error(String msg) {
        return ResultVO.of(false, msg, null);
    }

    public static <T> ResultVO<T> error(String msg, T result) {
        return ResultVO.of(false, msg, result);
    }
}
