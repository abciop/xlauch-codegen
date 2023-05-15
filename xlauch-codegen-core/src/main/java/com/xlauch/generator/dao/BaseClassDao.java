package com.xlauch.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlauch.generator.entity.BaseClassEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 基类管理
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface BaseClassDao extends BaseMapper<BaseClassEntity> {
	
}
