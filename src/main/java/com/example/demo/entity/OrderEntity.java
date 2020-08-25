package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version V1.0
 * @program: Order
 * @description: TODO
 * @author: Mr.Zhang
 * @create: 2020-08-25 09:50
 **/

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_order")
public class OrderEntity implements Serializable{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid2")
    private String uuid;

    private String name;

    private String memo;

    private BigDecimal price;

    private Integer amount;

    private Integer total;

    private String status;

    private String createUser;

    private String updateUser;

}
