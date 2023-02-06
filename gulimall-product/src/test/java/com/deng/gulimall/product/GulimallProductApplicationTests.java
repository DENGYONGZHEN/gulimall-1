package com.deng.gulimall.product;



import com.deng.gulimall.product.entity.BrandEntity;
import com.deng.gulimall.product.service.BrandService;
import com.deng.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {

	@Autowired
	BrandService brandService;
	@Autowired
	CategoryService categoryService;
	@Test
	public void TestGetPath(){
		Long[] catelogPath = categoryService.findCatelogPath(225L);
		log.info("完整路径：{}",Arrays.asList(catelogPath));
	}


	@Test
	public void contextLoads() {
//		BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("华为");
//        boolean save = brandService.save(brandEntity);
//        System.out.println("保存成功。。。。");
//        brandEntity.setBrandId(1L);
//        brandEntity.setDescript("华为");
//        brandService.updateById(brandEntity);
		List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id",1));
		for (BrandEntity entity : list) {
			System.out.println(entity);
		}
	}

}
