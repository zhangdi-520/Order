package com.example.demo.controller;

import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ResponseCode;
import com.example.demo.entity.ResponseResult;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @version V1.0
 * @program: Order
 * @description: 对外暴露的接口
 * @author: Mr.Zhang
 * @create: 2020-08-25 11:18
 **/
@RestController
@RequestMapping("/sys/sample")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/insert")
    public ResponseResult insertOrder(@RequestBody OrderEntity orderEntity){
        Boolean aBoolean = orderService.insertOrder(orderEntity);
        if (aBoolean){
            return ResponseResult.e(ResponseCode.OK_INSERT,orderEntity.toString(),null);
        }
        return ResponseResult.e(ResponseCode.ERROR,orderEntity.toString(),null);
    }


    @GetMapping("/findAll")
    public ResponseResult findAllOrder(){
        List<OrderEntity> orderEntityList = orderService.findAllOrder();
        return ResponseResult.e(ResponseCode.OK_FIND,null,orderEntityList);
    }

    @GetMapping("/getById")
    public ResponseResult getById(@RequestParam("id")String id){
        Object orderEntity = orderService.getById(id);
        return ResponseResult.e(ResponseCode.OK_FIND,null,orderEntity);
    }

    @GetMapping("/delete")
    public ResponseResult deleteById(@RequestParam("id")String id){
        orderService.deleteById(id);
        return ResponseResult.e(ResponseCode.OK_DELETE,id,null);
    }

    @PostMapping("/update")
    public ResponseResult updateById(@RequestBody OrderEntity orderEntity){
        Boolean aBoolean = orderService.updateById(orderEntity);
        if(aBoolean){
            return ResponseResult.e(ResponseCode.OK_UPDATE,orderEntity.toString(),null);
        }else {
            return ResponseResult.e(ResponseCode.ERROR,orderEntity.toString(),null);
        }

    }
}
