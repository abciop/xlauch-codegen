package com.xlauch.generator.controller;

import cn.hutool.core.io.IoUtil;
import lombok.AllArgsConstructor;
import com.xlauch.generator.common.page.PageResult;
import com.xlauch.generator.common.query.Query;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.entity.ProjectModifyEntity;
import com.xlauch.generator.service.ProjectModifyService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 项目名变更
 *
 * @author 阿沐 babamu@126.com
 */
@RestController
@RequestMapping("xlauch-codegen/gen/projectModify")
@AllArgsConstructor
public class ProjectModifyController {
    private final ProjectModifyService projectModifyService;

    @GetMapping("page")
    public Result<PageResult<ProjectModifyEntity>> page(@Valid Query query) {
        PageResult<ProjectModifyEntity> page = projectModifyService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    public Result<ProjectModifyEntity> get(@PathVariable("id") Long id) {
        ProjectModifyEntity entity = projectModifyService.getById(id);

        return Result.ok(entity);
    }

    @PostMapping
    public Result<String> save(@RequestBody ProjectModifyEntity entity) {
        projectModifyService.save(entity);

        return Result.ok();
    }

    @PutMapping
    public Result<String> update(@RequestBody @Valid ProjectModifyEntity entity) {
        projectModifyService.updateById(entity);

        return Result.ok();
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody List<Long> idList) {
        projectModifyService.removeByIds(idList);

        return Result.ok();
    }

    /**
     * 源码下载
     */
    @GetMapping("download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        // 项目信息
        ProjectModifyEntity project = projectModifyService.getById(id);

        byte[] data = projectModifyService.download(project);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + project.getModifyProjectName() + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), false, data);
    }
}
