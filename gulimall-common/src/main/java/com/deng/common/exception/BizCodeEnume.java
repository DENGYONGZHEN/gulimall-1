package com.deng.common.exception;

/**
 * @Classname BizCodeEnume
 * @Description  异常状态码
 * @Version 1.0.0
 * @Date 2023/2/2 18:52
 * @Created by helloDeng
 *  错误码和错误信息定义类
 *  1. 错误码定义规则为 5 为数字
 *  2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 *  3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 *  错误码列表：
 *  10: 通用
 *  001：参数格式校验
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14:
 *
 */
public enum BizCodeEnume {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败");

    private int code;
    private String errorMessage;

    BizCodeEnume(int code,String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    };

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
