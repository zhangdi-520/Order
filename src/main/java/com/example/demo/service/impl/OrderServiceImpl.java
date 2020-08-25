package com.example.demo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.annotation.Cacheable;
import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @version V1.0
 * @program: Order
 * @description: TODO
 * @author: Mr.Zhang
 * @create: 2020-08-25 11:24
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    @Transactional
//    @CacheEvict(value="order",allEntries=true)
    // LH comment: 插入为什么要删除缓存呢？
    @com.example.demo.annotation.CacheEvict(group = "getById")
    public Boolean insertOrder(OrderEntity orderEntity) {
        OrderEntity order = orderRepository.save(orderEntity);
        if(order!=null){
            return true;
        }
        return false;
    }

    @Override
    public List<OrderEntity> findAllOrder() {
        List<OrderEntity> all = orderRepository.findAll();
        return all;
    }

    @Override
    @Transactional
//    @CacheEvict(value="order",allEntries=true)
    // LH comment: 删除缓存的时候会清除所有，合适吗？
    @com.example.demo.annotation.CacheEvict(group = "getById")
    public void deleteById(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
//    @CacheEvict(value="order",allEntries=true)
    // LH comment: 更新数据为什么要删除缓存，为什么不更新缓存呢
    @com.example.demo.annotation.CacheEvict(group = "getById")
    public Boolean updateById(OrderEntity orderEntity) {
        Optional<OrderEntity> order = orderRepository.findById(orderEntity.getUuid());
        if (order.isPresent()){
            OrderEntity orderEntity1 = order.get();
            if (!StringUtils.equals(orderEntity.getName(),orderEntity1.getName())) {
                orderEntity1.setName(orderEntity.getName());
            }
            if (!StringUtils.equals(orderEntity.getMemo(),orderEntity1.getMemo())) {
                orderEntity1.setMemo(orderEntity.getMemo());
            }
            if (!StringUtils.equals(orderEntity.getStatus(),orderEntity1.getStatus())) {
                orderEntity1.setStatus(orderEntity.getStatus());
            }
            if (!StringUtils.equals(orderEntity.getCreateUser(),orderEntity1.getCreateUser())) {
                orderEntity1.setCreateUser(orderEntity.getCreateUser());
            }
            if (!StringUtils.equals(orderEntity.getUpdateUser(),orderEntity1.getUpdateUser())) {
                orderEntity1.setUpdateUser(orderEntity.getUpdateUser());
            }

            // LH comment: 下面这几行用 == 、compareTo是不合适的
            if(!(orderEntity.getAmount()==orderEntity1.getAmount())){
                orderEntity1.setAmount(orderEntity.getAmount());
            }
            if(!(orderEntity.getTotal()==orderEntity1.getTotal())){
                orderEntity1.setTotal(orderEntity.getTotal());
            }
            if(orderEntity.getPrice()!=null&&!(orderEntity.getPrice().compareTo(orderEntity1.getPrice())==0)){
                orderEntity1.setPrice(orderEntity.getPrice());
            }
            Boolean aBoolean = insertOrder(orderEntity1);
            return aBoolean;
        }else{
            Boolean aBoolean = insertOrder(orderEntity);
            return aBoolean;
        }
    }

    @Override
    @Cacheable(group = "getById",name="methodName")
    public Object getById(String id) {
        Optional<OrderEntity> byId = orderRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }else{
            // LH comment: 这里可能返回null，缓存的处理对空的处理是有问题的
            return null;
        }
    }
}
