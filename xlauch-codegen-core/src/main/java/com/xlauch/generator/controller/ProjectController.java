package com.xlauch.generator.controller;

import com.xlauch.generator.common.page.PageResult;
import com.xlauch.generator.common.query.Query;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.entity.ProjectEntity;
import com.xlauch.generator.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 类描述 : 项目管理
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/12 11:18
 */
@Slf4j
@RestController
@RequestMapping("xlauch-codegen/gen/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @GetMapping("page")
    public Result<PageResult<ProjectEntity>> page(Query query) {
        PageResult<ProjectEntity> page = projectService.page(query);
        return Result.ok(page);
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public Result<List<ProjectEntity>> list() {
        List<ProjectEntity> list = projectService.list();
        return Result.ok(list);
    }

    /**
     * 获取单个项目
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result<ProjectEntity> get(@PathVariable("id") Long id) {
        ProjectEntity data = projectService.getById(id);
        return Result.ok(data);
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody ProjectEntity entity) {
        return projectService.insertOrUpdate(entity);
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody ProjectEntity entity) {
        return projectService.insertOrUpdate(entity);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        projectService.removeBatchByIds(Arrays.asList(ids));
        return Result.ok();
    }
}
