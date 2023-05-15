package com.xlauch.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xlauch.generator.common.exception.ServerException;
import com.xlauch.generator.common.service.impl.BaseServiceImpl;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.dao.TemplateGroupDao;
import com.xlauch.generator.entity.TemplateEntity;
import com.xlauch.generator.entity.TemplateGroupEntity;
import com.xlauch.generator.service.TemplateGroupService;
import com.xlauch.generator.service.TemplateService;
import com.xlauch.generator.utils.TemplateUtils;
import com.xlauch.generator.vo.TemplateGroupVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 类描述 : 模板分组管理服务类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 15:11
 */
@Service
@AllArgsConstructor
public class TemplateGroupServiceImpl extends BaseServiceImpl<TemplateGroupDao, TemplateGroupEntity> implements TemplateGroupService {


    private final TemplateService templateService;

    private final static String XLAUCH_JSON = "_xlauch.json";

    @Override
    public Result<List<TemplateGroupVO>> getGroupList() {
        List<TemplateGroupVO> groupList = new ArrayList<>();

        // 获取所有模板分组
        List<TemplateGroupEntity> list = list();
        if (CollUtil.isNotEmpty(list)) {
            // 获取所有模板
            List<TemplateEntity> templateList = templateService.list();
            Map<Long, List<TemplateEntity>> templateMap = templateList.stream().collect(Collectors.groupingBy(TemplateEntity::getGroupId));

            for (TemplateGroupEntity entity : list) {
                TemplateGroupVO group = new TemplateGroupVO();
                BeanUtil.copyProperties(entity, group);
                group.setTemplateList(templateMap.get(entity.getId()));
                groupList.add(group);
            }
        }
        return Result.ok(groupList);
    }

    @Override
    public Result<String> deleteGroup(Long id) {
        List<TemplateEntity> list = getTemplateList(id);
        if (CollUtil.isNotEmpty(list)) {
            return Result.error("该分组下存在模板，无法删除");
        }

        removeById(id);
        return Result.ok();
    }


    @Override
    public Result<String> insertOrUpdate(TemplateGroupEntity entity) {
        if (checkExists(entity)) {
            return Result.error("分组名称已存在，请更换");
        }

        saveOrUpdate(entity);
        return Result.ok();
    }

    @Override
    public void downloadCode(Long id, ZipOutputStream zip) {
        List<TemplateEntity> templateList = getTemplateList(id);

        if (CollUtil.isNotEmpty(templateList)) {
            for (TemplateEntity template : templateList) {
                String content = template.getTemplateContent();
                String path = template.getSimpleName();

                try {
                    // 添加到zip
                    zip.putNextEntry(new ZipEntry(path));
                    IoUtil.writeUtf8(zip, false, content);
                    zip.flush();
                    zip.closeEntry();
                } catch (IOException e) {
                    throw new ServerException("模板写入失败：" + path, e);
                }

                template.setTemplateContent("");
            }


            //生成配置信息
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(XLAUCH_JSON));
                IoUtil.writeUtf8(zip, false, JSON.toJSONString(templateList));
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                throw new ServerException("模板写入失败：" + XLAUCH_JSON, e);
            }
        }
    }

    @Override
    public Result<String> parseFile(String filePath, Long groupId) {
        // 解压文件
        File dir = ZipUtil.unzip(filePath);
        // 找到配置文件
        File xlauchJson = new File(dir, XLAUCH_JSON);
        if (xlauchJson.exists()) {
            // 读取json数据，转为list
            String json = FileUtil.readString(xlauchJson, StandardCharsets.UTF_8);
            if (StrUtil.isNotBlank(json)) {
                //原有列表
                Map<String, Long> map = getTemplateList(groupId).stream().collect(Collectors.toMap(TemplateEntity::getSimpleName, TemplateEntity::getId));

                List<TemplateEntity> templateList = JSON.parseArray(json, TemplateEntity.class);

                templateList.forEach(item -> {
                    item.setGroupId(groupId);
                    // 根据模板名称去找旧数据，如果存在，赋值ID，等同更新操作
                    item.setId(map.get(item.getSimpleName()));
                    File tmp = new File(dir, item.getSimpleName());
                    if (tmp.exists()) {
                        item.setTemplateContent(FileUtil.readString(tmp, StandardCharsets.UTF_8));
                    }
                });

                templateService.saveOrUpdateBatch(templateList);
            }
            return Result.ok();
        }
        return Result.error("找不到" + XLAUCH_JSON + "配置文件，导入失败");
    }


    /**
     * 获取分组下的所有模板
     *
     * @param id
     * @return
     */
    private List<TemplateEntity> getTemplateList(Long id) {
        QueryWrapper<TemplateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", id);
        List<TemplateEntity> list = templateService.list(queryWrapper);
        return list;
    }


    /**
     * 验证重复
     *
     * @param entity
     * @return
     */
    private boolean checkExists(TemplateGroupEntity entity) {
        QueryWrapper<TemplateGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_name", entity.getGroupName());
        if (entity.getId() != null) {
            queryWrapper.ne("id", entity.getId());
        }

        List<TemplateGroupEntity> list = list(queryWrapper);
        return CollUtil.isNotEmpty(list);
    }

}
