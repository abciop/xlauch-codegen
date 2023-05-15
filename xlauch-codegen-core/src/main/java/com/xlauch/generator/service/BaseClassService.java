package com.xlauch.generator.service;

import com.xlauch.generator.common.page.PageResult;
import com.xlauch.generator.common.query.Query;
import com.xlauch.generator.common.service.BaseService;
import com.xlauch.generator.entity.BaseClassEntity;

import java.util.List;

/**
 * 基类管理
 *
 * @author 阿沐 babamu@126.com
 */
public interface BaseClassService extends BaseService<BaseClassEntity> {

    PageResult<BaseClassEntity> page(Query query);

    List<BaseClassEntity> getList();
}
