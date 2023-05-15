package com.xlauch.generator.service;

import com.xlauch.generator.common.service.BaseService;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.entity.TemplateEntity;

/**
 * <p>
 * 类描述 : 分组服务类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 15:10
 */
public interface TemplateService extends BaseService<TemplateEntity> {

    /**
     * 新增或修改
     *
     * @param entity
     * @return
     */
    Result<String> insertOrUpdate(TemplateEntity entity);
}
