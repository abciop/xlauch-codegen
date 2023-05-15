package com.xlauch.generator.service;

import com.xlauch.generator.common.page.PageResult;
import com.xlauch.generator.common.query.Query;
import com.xlauch.generator.common.service.BaseService;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.entity.ProjectEntity;

/**
 * <p>
 * 类描述 : 项目管理service
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/12 11:15
 */
public interface ProjectService extends BaseService<ProjectEntity> {

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResult<ProjectEntity> page(Query query);

    /**
     * 新增或修改
     *
     * @param entity
     * @return
     */
    Result<String> insertOrUpdate(ProjectEntity entity);
}
