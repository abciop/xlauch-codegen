package com.xlauch.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlauch.generator.entity.DataSourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源管理
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface DataSourceDao extends BaseMapper<DataSourceEntity> {
	
}
