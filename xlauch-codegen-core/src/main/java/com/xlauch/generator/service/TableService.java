package com.xlauch.generator.service;

import com.xlauch.generator.common.page.PageResult;
import com.xlauch.generator.common.query.Query;
import com.xlauch.generator.common.service.BaseService;
import com.xlauch.generator.entity.TableEntity;

/**
 * 数据表
 *
 * @author 阿沐 babamu@126.com
 */
public interface TableService extends BaseService<TableEntity> {

    PageResult<TableEntity> page(Query query);

    TableEntity getByTableName(String tableName);

    void deleteBatchIds(Long[] ids);

    /**
     * 导入表
     *
     * @param projectId
     * @param datasourceId
     * @param tableName
     */
    void tableImport(Long projectId, Long datasourceId, String tableName);

    /**
     * 同步数据库表
     *
     * @param id 表ID
     */
    void sync(Long id);
}
