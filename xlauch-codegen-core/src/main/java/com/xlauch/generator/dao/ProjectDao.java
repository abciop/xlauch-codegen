package com.xlauch.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlauch.generator.entity.ProjectEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 类描述 : 项目管理dao
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/12 11:15
 */
@Mapper
public interface ProjectDao extends BaseMapper<ProjectEntity> {

}
