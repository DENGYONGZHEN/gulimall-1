package com.deng.gulimall.member.feign;

import com.deng.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname CouponFeignService
 * @Description
 * @Version 1.0.0
 * @Date 2023/1/28 10:13
 * @Created by helloDeng
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/member/list")
    public R CouponMember();
}
