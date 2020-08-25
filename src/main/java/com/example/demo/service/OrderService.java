package com.example.demo.service;

import com.example.demo.entity.OrderEntity;
import com.example.demo.service.impl.OrderServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @program: Order
 * @description: 商品服务接口类
 * @author: Mr.Zhang
 * @create: 2020-08-25 11:24
 **/
public interface OrderService  {
    /*
    * 插入商品
    * */
    Boolean insertOrder(OrderEntity orderEntity);

    /*
    * 查找全部商品
    * */
    List<OrderEntity> findAllOrder();

    /*
    * 删除商品
    * */
    void deleteById(String id);

    /*
    * 更新商品
    * */
    Boolean updateById(OrderEntity orderEntity);

    Object getById(String id);

}
