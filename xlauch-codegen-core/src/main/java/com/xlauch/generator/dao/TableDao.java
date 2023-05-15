package com.xlauch.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlauch.generator.entity.TableEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据表
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface TableDao extends BaseMapper<TableEntity> {

}
