package com.deng.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deng.common.utils.PageUtils;
import com.deng.gulimall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author DENGYONGZHEN
 * @email YONGZHENDENG111@gmail.com
 * @date 2023-01-27 21:18:29
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

