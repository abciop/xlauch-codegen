package com.xlauch.generator.service;

import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.config.template.TemplateInfo;
import com.xlauch.generator.vo.TemplateVO;

import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 *
 * @author 阿沐 babamu@126.com
 */
public interface GeneratorService {


    void downloadCode(Long tableId, String templates, ZipOutputStream zip);

    void generatorCode(Long tableId);

    /**
     * 代码生成
     *
     * @param tableId
     * @param templates
     */
    void generatorCode(Long tableId, Long[] templates);
}
