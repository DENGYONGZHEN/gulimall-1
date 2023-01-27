package com.deng.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deng.common.utils.PageUtils;
import com.deng.gulimall.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author DENGYONGZHEN
 * @email YONGZHENDENG111@gmail.com
 * @date 2023-01-27 21:25:20
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

