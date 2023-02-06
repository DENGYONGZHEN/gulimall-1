package com.deng.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.common.constant.ProductConstant;
import com.deng.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.deng.gulimall.product.dao.AttrGroupDao;
import com.deng.gulimall.product.dao.CategoryDao;
import com.deng.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.deng.gulimall.product.entity.AttrGroupEntity;
import com.deng.gulimall.product.entity.CategoryEntity;
import com.deng.gulimall.product.service.CategoryService;
import com.deng.gulimall.product.vo.AttrRespVo;
import com.deng.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deng.common.utils.PageUtils;
import com.deng.common.utils.Query;

import com.deng.gulimall.product.dao.AttrDao;
import com.deng.gulimall.product.entity.AttrEntity;
import com.deng.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AttrDao attrDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        //保存attrEntity实体类对应的表
        this.save(attrEntity);
        //保存关联关系
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId() != null){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            //AttrId 在数据库中是自增长的，先保存之后，就能到到他的AttrId
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }
    }

    /**
     * 查询属性
     *
     * @param params
     * @param catelogId
     * @param attrType
     * @return
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        //先获取key
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        //判断是基本属性还是销售属性，进行相应的查询
        wrapper.eq("attr_type","base".equalsIgnoreCase(attrType)?
                ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        //不为0时按三级分类节点id查
        if(catelogId != 0){
            wrapper.eq("catelog_id",catelogId);
        }
        //如果key不为空，拼接条件进行模糊查询
        if(!StringUtils.isEmpty(key)){
            //拼接条件
            wrapper.and((queryWrapper)->{
                queryWrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        //当catelogId 为0时 查询所有
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pageUtils = new PageUtils(page);
        //获取当前页中的记录
        List<AttrEntity> records = page.getRecords();

        //映射每一条记录
        List<AttrRespVo> voList = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            //因为返回的数据中有分类名和分组名，设置分类和分组的名字
            AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().
                    eq("attr_id", attrEntity.getAttrId()));
            if("base".equalsIgnoreCase(attrType)){
                if (attrId != null && attrId.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(voList);
        return pageUtils;

    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,attrRespVo);

        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //设置分组Id
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if(attrAttrgroupRelationEntity != null){
                attrRespVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
                //设置分组名
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                if(attrGroupEntity != null){
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }

        //设置所属分类的完整路径
        Long[] catelogPath = categoryService.findCatelogPath(attrEntity.getCatelogId());
        attrRespVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
        if(categoryEntity != null){
            attrRespVo.setCatelogName(categoryEntity.getName());
        }
        return attrRespVo;
    }

    /**
     * 更新 属性
     * @param attr
     */
    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        //更新 attrEntity对应的表中的数据
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //因为在创建时所属分组没选，相应的属性与分组关系表中是没有记录的，虽说是更新操作，但对于所属分组是在属性与分组关系表中增加一条记录
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attr.getAttrId()));
            //更新attrAttrgroupRelation对应的表中的数据
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            if (count > 0) {
                attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity,
                        new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            } else {
                attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
            }
        }

    }

    /**
     * 根据分组id查询所有关联的属性
     * @param attrgroupId 属性与属性分组关系表中 属性分组的id
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> entityList = attrAttrgroupRelationDao.
                selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        List<Long> attrIds = entityList.stream().map((attrAttrgroupRelationEntity) -> {
            return attrAttrgroupRelationEntity.getAttrId();
        }).collect(Collectors.toList());
        if(attrIds == null || attrIds.size() == 0){
            return null;
        }
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        return (List<AttrEntity>) attrEntities;
    }

    /**
     * 获取当前分组没有关联的属性
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1、当前分组只能关联自己所属分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();

        //2、当前分组只能关联别的分组没有引用的属性
        //2.1 获取当前分类下的其他分组
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));
        List<Long> groupIds = groupEntities.stream().map((group) -> {
            return group.getAttrGroupId();
        }).collect(Collectors.toList());
        //2.2 获取这些分组关联的属性
        List<AttrAttrgroupRelationEntity> attrgroupRelationEntityList = attrAttrgroupRelationDao.
                selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds));
        List<Long> attrIds = attrgroupRelationEntityList.stream().map((attrgroupRelation) -> {
            return attrgroupRelation.getAttrId();
        }).collect(Collectors.toList());
        //2.3获取当前分类下其他分组没有关联的属性
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().
                eq("catelog_id", catelogId).eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(attrIds != null && attrIds.size() > 0){
            wrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and(queryWrapper -> {
                queryWrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}