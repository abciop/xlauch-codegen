package com.xlauch.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xlauch.generator.common.service.impl.BaseServiceImpl;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.dao.TemplateDao;
import com.xlauch.generator.entity.TemplateEntity;
import com.xlauch.generator.entity.TemplateGroupEntity;
import com.xlauch.generator.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 类描述 : 模板管理服务类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 16:29
 */
@Service
@AllArgsConstructor
public class TemplateServiceImpl extends BaseServiceImpl<TemplateDao, TemplateEntity> implements TemplateService {


    @Override
    public Result<String> insertOrUpdate(TemplateEntity entity) {
        if (checkExists(entity)) {
            return Result.error("模板名称已存在，请更换");
        }

        saveOrUpdate(entity);
        return Result.ok();
    }

    /**
     * 验证重复
     *
     * @param entity
     * @return
     */
    private boolean checkExists(TemplateEntity entity) {
        QueryWrapper<TemplateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", entity.getGroupId());
        queryWrapper.eq("template_name", entity.getGroupId());

        if (entity.getId() != null) {
            queryWrapper.ne("id", entity.getId());
        }

        List<TemplateEntity> list = list(queryWrapper);
        return CollUtil.isNotEmpty(list);
    }


}
