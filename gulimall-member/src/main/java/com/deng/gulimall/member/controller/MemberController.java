package com.deng.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;


import com.deng.gulimall.member.feign.CouponFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deng.gulimall.member.entity.MemberEntity;
import com.deng.gulimall.member.service.MemberService;
import com.deng.common.utils.PageUtils;
import com.deng.common.utils.R;



/**
 * 会员
 *
 * @author DENGYONGZHEN
 * @email YONGZHENDENG111@gmail.com
 * @date 2023-01-27 21:22:05
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    CouponFeignService couponFeignService;

    /**
     * 测试使用feign
     * @return
     */
    @RequestMapping("/coupons")
    public R coupons(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("zhangsan");
        R r = couponFeignService.CouponMember();
        Object coupon = r.get("coupon");
        return R.ok().put("member",memberEntity).put("coupon",coupon);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
