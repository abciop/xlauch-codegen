package com.xlauch.generator.service;

import com.xlauch.generator.common.service.BaseService;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.entity.TableEntity;
import com.xlauch.generator.entity.TemplateGroupEntity;
import com.xlauch.generator.vo.TemplateGroupVO;

import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 类描述 : 分组服务类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 15:10
 */
public interface TemplateGroupService extends BaseService<TemplateGroupEntity> {

    /**
     * 获取模板分组列表
     *
     * @return
     */
    Result<List<TemplateGroupVO>> getGroupList();


    /**
     * 删除分组
     *
     * @param id
     * @return
     */
    Result<String> deleteGroup(Long id);

    /**
     * 新增或修改
     *
     * @param entity
     * @return
     */
    Result<String> insertOrUpdate(TemplateGroupEntity entity);

    /**
     * 导出模板
     *
     * @param id
     * @param zip
     */
    void downloadCode(Long id, ZipOutputStream zip);

    /**
     * 上传文件
     *
     * @param filePath
     * @param groupId
     * @return
     */
    Result<String> parseFile(String filePath, Long groupId);
}
