package com.xlauch.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xlauch.generator.common.page.PageResult;
import com.xlauch.generator.common.query.Query;
import com.xlauch.generator.common.service.impl.BaseServiceImpl;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.dao.ProjectDao;
import com.xlauch.generator.entity.DataSourceEntity;
import com.xlauch.generator.entity.ProjectEntity;
import com.xlauch.generator.entity.TemplateEntity;
import com.xlauch.generator.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 类描述 : 项目管理service实现类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/12 11:15
 */
@Service
@AllArgsConstructor
public class ProjectServiceImpl extends BaseServiceImpl<ProjectDao, ProjectEntity> implements ProjectService {


    @Override
    public PageResult<ProjectEntity> page(Query query) {
        IPage<ProjectEntity> page = baseMapper.selectPage(
                getPage(query),
                getWrapper(query)
        );
        return new PageResult<>(page.getRecords(), page.getTotal());
    }


    @Override
    public Result<String> insertOrUpdate(ProjectEntity entity) {
        if (checkExists(entity)) {
            return Result.error("项目名称已存在，请更换");
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
    private boolean checkExists(ProjectEntity entity) {
        QueryWrapper<ProjectEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_name", entity.getProjectName());

        if (entity.getId() != null) {
            queryWrapper.ne("id", entity.getId());
        }

        List<ProjectEntity> list = list(queryWrapper);
        return CollUtil.isNotEmpty(list);
    }


}
