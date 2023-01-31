package com.deng.gulimall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deng.common.utils.PageUtils;
import com.deng.common.utils.Query;

import com.deng.gulimall.product.dao.CategoryDao;
import com.deng.gulimall.product.entity.CategoryEntity;
import com.deng.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    @Autowired
//    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查出所有分类信息，并以父子树形结构返回
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        //2、组装成父子结构
        //2.1）找到所有的一级分类
        List<CategoryEntity> list = categoryEntities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid()==0;
        }).map(menu->{
            menu.setChildren(getChildren(menu,categoryEntities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        //2.2)再找到各个一级分类的子分类

        return list;
    }

    /**
     *  删除
     * @param catIds
     */
    @Override
    public void removeMenuByIds(List<Long> catIds) {
        //TODO 检查是否被引用，然后再删除

        //使用逻辑删除
        baseMapper.deleteBatchIds(catIds);
    }

    /**
     * 获取子分类
     * @param root 当前分类
     * @param all  查到的所有分类的list
     * @return
     */
   public List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){
       List<CategoryEntity> entityList = all.stream().filter(category -> {
           return category.getParentCid() == root.getCatId();
       }).map(menu -> {
           menu.setChildren(getChildren(menu, all));
           return menu;
       }).sorted((menu1, menu2) -> {
           return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
       }).collect(Collectors.toList());
       return entityList;
   }

}