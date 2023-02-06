package com.deng.gulimall.product.exception;

import com.deng.common.exception.BizCodeEnume;
import com.deng.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname GulimallExceptionControllerAdvice
 * @Description  集中处理所有异常
 * @Version 1.0.0
 * @Date 2023/2/2 18:22
 * @Created by helloDeng
 */
@Slf4j
//@ResponseBody
//@ControllerAdvice(basePackages = "com.deng.gulimall.product.controller")
@RestControllerAdvice(basePackages = "com.deng.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException exception){
        log.error("数据校验出现问题{},异常类型:{}",exception.getMessage(),exception.getClass());
        BindingResult result = exception.getBindingResult();
        Map<String,String> map = new HashMap<>();
            //获取校验的错误结果
            result.getFieldErrors().forEach(item->{
                //获取到错误提示defaultMessage
                String defaultMessage = item.getDefaultMessage();
                //获取错误的属性名字
                String field = item.getField();
                map.put(field,defaultMessage);
            });
           return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getErrorMessage()).put("data",map);
    }
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){
        log.error("错误：",throwable);
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(),BizCodeEnume.UNKNOW_EXCEPTION.getErrorMessage());
    }
}
