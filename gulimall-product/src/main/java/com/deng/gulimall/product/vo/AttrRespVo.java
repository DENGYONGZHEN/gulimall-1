package com.deng.gulimall.product.vo;

import lombok.Data;

/**
 * @Classname AttrRespVo
 * @Description 属性响应数据
 * @Version 1.0.0
 * @Date 2023/2/5 19:14
 * @Created by helloDeng
 */
@Data
public class AttrRespVo extends AttrVo{
  //属性所属三级分类名
  private String catelogName;
  //属性所属分组名
  private String groupName;
  //所属分类路径
  private Long[] catelogPath;
}
