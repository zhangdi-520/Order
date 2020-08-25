package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @version V1.0
 * @program: Order
 * @description: TODO
 * @author: Mr.Zhang
 * @create: 2020-08-25 12:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> implements Serializable {

    private Boolean success;

    private String errorCode;

    private String message;

    private String args;

    private T data;

    // LH comment: 该方法无需加锁
    public synchronized static <T> ResponseResult<T> e(ResponseCode status,String args,T data){
        // LH comment: 下面这行声明为static更为合适
        List<String> strings = Arrays.asList("10000", "10001", "10002", "10003");
        ResponseResult<T> res = new ResponseResult<>();
        // LH comment: List做contains效率不如Set
        if(strings.contains(status.getCode())){
            res.setSuccess(true);
        }else{
            res.setSuccess(false);
        }
        res.setErrorCode(status.getCode());
        res.setMessage(status.getMessage());
        res.setArgs(args);
        res.setData(data);
        return res;
    }
}
