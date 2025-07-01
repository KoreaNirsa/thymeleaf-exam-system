package com.tes.global.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ResultVO<T> {
    private boolean success;
    private String message;
    private T result;

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
