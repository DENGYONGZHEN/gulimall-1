package com.deng.gulimall.order.dao;

import com.deng.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author DENGYONGZHEN
 * @email YONGZHENDENG111@gmail.com
 * @date 2023-01-27 21:12:15
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
