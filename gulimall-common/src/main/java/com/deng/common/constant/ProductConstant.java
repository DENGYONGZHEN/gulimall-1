package com.deng.common.constant;

/**
 * @Classname ProductConstant
 * @Description 商品系统的常量
 * @Version 1.0.0
 * @Date 2023/2/5 22:06
 * @Created by helloDeng
 */
public class ProductConstant {
    public enum AttrEnum{
        ATTR_TYPE_BASE(1,"基本属性"),ATTR_TYPE_SALE(0,"销售属性");
        private int code;
        private String message;
        AttrEnum(int code,String message){
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
