package com.xlauch.generator.controller;

import lombok.AllArgsConstructor;
import com.xlauch.generator.common.page.PageResult;
import com.xlauch.generator.common.query.Query;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.entity.TableEntity;
import com.xlauch.generator.entity.TableFieldEntity;
import com.xlauch.generator.service.TableFieldService;
import com.xlauch.generator.service.TableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据表管理
 *
 * @author 阿沐 babamu@126.com
 */
@RestController
@RequestMapping("xlauch-codegen/gen/table")
@AllArgsConstructor
public class TableController {
    private final TableService tableService;
    private final TableFieldService tableFieldService;

    /**
     * 分页
     *
     * @param query 查询参数
     */
    @GetMapping("page")
    public Result<PageResult<TableEntity>> page(Query query) {
        PageResult<TableEntity> page = tableService.page(query);

        return Result.ok(page);
    }

    /**
     * 获取表信息
     *
     * @param id 表ID
     */
    @GetMapping("{id}")
    public Result<TableEntity> get(@PathVariable("id") Long id) {
        TableEntity table = tableService.getById(id);

        // 获取表的字段
        List<TableFieldEntity> fieldList = tableFieldService.getByTableId(table.getId());
        table.setFieldList(fieldList);

        return Result.ok(table);
    }

    /**
     * 修改
     *
     * @param table 表信息
     */
    @PutMapping
    public Result<String> update(@RequestBody TableEntity table) {
        tableService.updateTable(table);

        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids 表id数组
     */
    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        tableService.deleteBatchIds(ids);

        return Result.ok();
    }

    /**
     * 同步表结构
     *
     * @param id 表ID
     */
    @PostMapping("sync/{id}")
    public Result<String> sync(@PathVariable("id") Long id) {
        tableService.sync(id);

        return Result.ok();
    }

    /**
     * 导入数据源中的表
     *
     * @param datasourceId  数据源ID
     * @param tableNameList 表名列表
     */
    @PostMapping("import/{projectId}/{datasourceId}")
    public Result<String> tableImport(@PathVariable("projectId") Long projectId, @PathVariable("datasourceId") Long datasourceId, @RequestBody List<String> tableNameList) {
        for (String tableName : tableNameList) {
            tableService.tableImport(projectId, datasourceId, tableName);
        }
        return Result.ok();
    }

    /**
     * 修改表字段数据
     *
     * @param tableId        表ID
     * @param tableFieldList 字段列表
     */
    @PutMapping("field/{tableId}")
    public Result<String> updateTableField(@PathVariable("tableId") Long tableId, @RequestBody List<TableFieldEntity> tableFieldList) {
        tableFieldService.updateTableField(tableId, tableFieldList);

        return Result.ok();
    }

}
