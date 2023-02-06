package com.deng.gulimall.product.service.impl;

import com.deng.gulimall.product.entity.CategoryBrandRelationEntity;
import com.deng.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deng.common.utils.PageUtils;
import com.deng.common.utils.Query;

import com.deng.gulimall.product.dao.BrandDao;
import com.deng.gulimall.product.entity.BrandEntity;
import com.deng.gulimall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    /**
     *    增加模糊查询功能
     * @param params  请求参数
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //获取key
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.eq("brand_id",key).or().like("name",key);
        }
            IPage<BrandEntity> page = this.page(
                    new Query<BrandEntity>().getPage(params),
                    wrapper);

            return new PageUtils(page);
    }

    /**
     * 涉及品牌表中的字段被别的表引用，所以在修改brand表的时候，引用表中的字段也要进行修改
     * @param brand
     */
    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        //保证冗余字段的数据一致
        this.updateById(brand);
       if(!StringUtils.isEmpty(brand.getName())){
           //同步更新其他关联表中的数据
           categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
           //TODO 更新其他关联表
       }
    }
}