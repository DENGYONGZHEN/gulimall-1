package com.deng.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deng.gulimall.product.entity.CategoryEntity;
import com.deng.gulimall.product.service.CategoryService;
import com.deng.common.utils.PageUtils;
import com.deng.common.utils.R;



/**
 * 商品三级分类
 *
 * @author DENGYONGZHEN
 * @email YONGZHENDENG111@gmail.com
 * @date 2023-01-25 20:27:27
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类信息并以父子类树形结构进行展示
     */
    @RequestMapping("/list/tree")
    public R listWithTree(){
        List<CategoryEntity> entityList= categoryService.listWithTree();

        return R.ok().put("data", entityList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     * @RequestBody 获取请求体，只有post请求才有请求体,springMVC自动将请求体的数据json，转为对应的对象
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds){
		//categoryService.removeByIds(Arrays.asList(catIds));
        //1、检查当前删除的id是否被别的地方引用
        categoryService.removeMenuByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
