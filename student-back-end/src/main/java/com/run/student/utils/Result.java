package com.run.student.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;   //返回码，0成功
    private String message; //返回描述
    private T data;     //返回数据
    private Integer count;     //分页查询的总记录数

    public static Result<Object> success() {
        return new Result<>(0, "success", null, null);
    }

    public static Result<Object> fail() {
        return new Result<>(-1, "fail", null, null);
    }

    public static Result<Object> fail(String message) {
        return new Result<>(-1, message, null, null);
    }
}