package com.xlauch.generator.controller;

import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.entity.TemplateEntity;
import com.xlauch.generator.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 类描述 : 模板管理
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 14:55
 */
@Controller
@RequestMapping("xlauch-codegen/gen/template")
@AllArgsConstructor
public class TeamplateController {

    private final TemplateService templateService;

    /**
     * 新增模板
     *
     * @param entity
     * @return
     */
    @PostMapping
    @ResponseBody
    public Result<String> save(@RequestBody TemplateEntity entity) {
        return templateService.insertOrUpdate(entity);
    }

    /**
     * 修改模板
     *
     * @param entity
     * @return
     */
    @PutMapping
    @ResponseBody
    public Result<String> update(@RequestBody @Valid TemplateEntity entity) {
        return templateService.insertOrUpdate(entity);
    }

    /**
     * 删除模板
     *
     * @param ids
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public Result<String> delete(@RequestBody Long[] ids) {
        templateService.removeBatchByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
