package com.shimh.util;

import com.macro.mall.common.api.ResultCode;
import com.shimh.common.result.Result;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {

    /**
     * 成功时返回
     * @param data 返回的data对象
     * @return {@link Result}
     */
    public static Result success(Object data) {
        Result Result = new Result();
        Result.setCode(0);
        Result.setMsg("success");
        Result.setData(data);
        return Result;
    }

    /**
     * 成功时返回
     * @return {@link Result}
     */
    public static Result success() {
        Map data = new HashMap();
        return success(data);
    }

    /**
     * 错误时返回
     * @param code 错误码
     * @param message 错误信息
     * @return {@link Result}
     */
    public static Result error(int code, String message) {
        Result Result = new Result();
        Result.setCode(code);
        Result.setMsg(message);
        Map data = new HashMap();
        Result.setData(data);
        return Result;
    }

    /**
     * 错误时返回
     * @param resultCode 错误枚举类
     * @return {@link Result}
     */
    public static Result error(ResultCode resultCode) {
        return error(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * 错误时返回
     * @param resultEnum 错误枚举类
     * @param message 错误的信息
     * @return {@link Result}
     */
    public static Result error(ResultCode resultEnum, String message) {
        return error(resultEnum.getCode(), message);
    }

    /**
     * 默认的错误
     * @return {@link Result}
     */
    public static Result error() {
        return error(ResultCode.NOT_NETWORK);
    }

}
