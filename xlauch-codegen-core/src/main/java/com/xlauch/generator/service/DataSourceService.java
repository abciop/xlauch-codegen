package com.xlauch.generator.service;

import com.xlauch.generator.common.page.PageResult;
import com.xlauch.generator.common.query.Query;
import com.xlauch.generator.common.service.BaseService;
import com.xlauch.generator.config.GenDataSource;
import com.xlauch.generator.entity.DataSourceEntity;

import java.util.List;

/**
 * 数据源管理
 *
 * @author 阿沐 babamu@126.com
 */
public interface DataSourceService extends BaseService<DataSourceEntity> {

    PageResult<DataSourceEntity> page(Query query);

    List<DataSourceEntity> getList();

    /**
     * 获取数据库产品名，如：MySQL
     *
     * @param datasourceId 数据源ID
     * @return 返回产品名
     */
    String getDatabaseProductName(Long datasourceId);

    /**
     * 根据数据源ID，获取数据源
     *
     * @param datasourceId 数据源ID
     */
    GenDataSource get(Long datasourceId);
}
