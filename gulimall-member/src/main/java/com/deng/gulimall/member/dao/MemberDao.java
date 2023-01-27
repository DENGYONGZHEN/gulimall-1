package com.deng.gulimall.member.dao;

import com.deng.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author DENGYONGZHEN
 * @email YONGZHENDENG111@gmail.com
 * @date 2023-01-27 21:22:05
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
