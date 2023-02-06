package com.deng.gulimall.product.service.impl;

import com.deng.gulimall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deng.common.utils.PageUtils;
import com.deng.common.utils.Query;

import com.deng.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.deng.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.deng.gulimall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] relationVos) {

        List<AttrGroupRelationVo> list = Arrays.asList(relationVos);
            List<AttrAttrgroupRelationEntity> entities = list.stream().map((relationVo) -> {
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
                BeanUtils.copyProperties(relationVo, attrAttrgroupRelationEntity);
                return attrAttrgroupRelationEntity;
            }).collect(Collectors.toList());
        attrAttrgroupRelationDao.deleteBatchRelation(entities);
    }

    @Override
    public void saveRelation(List<AttrGroupRelationVo> relationVos) {
        List<AttrAttrgroupRelationEntity> relationEntities = relationVos.stream().map((vo) -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(vo, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        this.saveBatch(relationEntities);
    }

}