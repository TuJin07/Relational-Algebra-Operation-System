package com.example.operation_system.response;

import lombok.Data;

/**
 * @program: operation_system
 * @description: 用于返回前端的响应类
 * @author: Xuan
 * @create: 2022-10-18 20:19
 **/

@SuppressWarnings("rawtypes")
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    public static final int SUCCESS_CODE = 200;

    public static final int FAIL_CODE = 400;

    private static final String SUCCESS_NO_DATA = "成功，无返回数据";

    private static final String SUCCESS_WITH_DATA = "成功，含返回数据";

    public static Result fail(String msg) {
        Result result = new Result();
        result.setCode(Result.FAIL_CODE);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(Result.SUCCESS_CODE);
        result.setMsg(SUCCESS_WITH_DATA);
        result.setData(data);
        return result;
    }

    public static Result success() {
        Result result = new Result();
        result.setCode(Result.SUCCESS_CODE);
        result.setMsg(SUCCESS_NO_DATA);
        return result;
    }
}
