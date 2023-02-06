package com.deng.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deng.common.utils.PageUtils;
import com.deng.gulimall.product.entity.AttrEntity;
import com.deng.gulimall.product.vo.AttrRespVo;
import com.deng.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author DENGYONGZHEN
 * @email YONGZHENDENG111@gmail.com
 * @date 2023-01-25 20:27:27
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

