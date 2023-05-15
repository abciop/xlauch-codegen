package com.xlauch.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlauch.generator.entity.TemplateEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 类描述 : 模板组
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 15:03
 */
@Mapper
public interface TemplateDao extends BaseMapper<TemplateEntity> {
}
