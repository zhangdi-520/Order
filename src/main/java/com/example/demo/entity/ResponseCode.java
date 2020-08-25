package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: Order
 * @description: 状态码
 * @author: Mr.Wang
 * @create: 2020-08-25 12:47
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum ResponseCode {

    OK_INSERT("10000","操作成功"),
    OK_UPDATE("10001","更新成功"),
    OK_DELETE("10002","删除成功"),
    OK_FIND("10003","查询成功"),
    ERROR("20000","操作失败");
    private String Code;

    private String message;

    public String getCode(){
        return Code;
    }

    public String getMessage(){
        return message;
    }
}
