package com.deng.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.deng.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.deng.gulimall.product.entity.AttrEntity;
import com.deng.gulimall.product.service.AttrAttrgroupRelationService;
import com.deng.gulimall.product.service.AttrService;
import com.deng.gulimall.product.service.CategoryService;
import com.deng.gulimall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deng.gulimall.product.entity.AttrGroupEntity;
import com.deng.gulimall.product.service.AttrGroupService;
import com.deng.common.utils.PageUtils;
import com.deng.common.utils.R;



/**
 * 属性分组
 *
 * @author DENGYONGZHEN
 * @email YONGZHENDENG111@gmail.com
 * @date 2023-01-25 20:27:27
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 批量新建分组和属性的关联关系
     * @param relationVos
     * @return
     */

    //product/attrgroup/attr/relation
    @PostMapping("/attr/relation")
    public R attrRelation(@RequestBody List<AttrGroupRelationVo> relationVos){
        attrAttrgroupRelationService.saveRelation(relationVos);
        return R.ok();
    }

    /**
     * 查询分组没有关联的属性
     * @return
     */
    //product/attrgroup/1/noattr/relation
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R AttrNoRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrgroupId") Long attrgroupId){
        PageUtils page = attrService.getNoRelationAttr(params,attrgroupId);
        return R.ok().put("page",page);
    }


    /**
     * 批量删除分组所关联的属性
     * @param relationVos
     * @return
     */
    //product/attrgroup/attr/relation/delete
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo [] relationVos){
        attrAttrgroupRelationService.deleteRelation(relationVos);
        return R.ok();
    }

    /**
     * 查询分组所关联的属性
     * @param attrgroupId
     * @return
     */
    //product/attrgroup/1/attr/relation
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> entities =  attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data",entities);
    }


    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        //获取完整路径
        Long catelogId = attrGroup.getCatelogId();
        Long [] path = categoryService.findCatelogPath(catelogId);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
