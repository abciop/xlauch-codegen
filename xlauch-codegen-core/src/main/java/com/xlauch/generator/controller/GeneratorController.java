package com.xlauch.generator.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.xlauch.generator.config.template.GeneratorInfo;
import com.xlauch.generator.config.template.TemplateInfo;
import com.xlauch.generator.vo.GenerVO;
import com.xlauch.generator.vo.TemplateVO;
import lombok.AllArgsConstructor;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.service.GeneratorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 *
 * @author 阿沐 babamu@126.com
 */
@Controller
@RequestMapping("xlauch-codegen/gen/generator")
@AllArgsConstructor
public class GeneratorController {
    private final GeneratorService generatorService;


    /**
     * 生成代码（zip压缩包）
     *
     * @param tableIds
     * @param templates
     * @param response
     * @throws Exception
     */
    @GetMapping("download")
    public void downloadWithTemplate(String tableIds, String templates, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        // 生成代码
        for (String tableId : tableIds.split(",")) {
            generatorService.downloadCode(Long.parseLong(tableId), templates, zip);
        }

        IoUtil.close(zip);

        // zip压缩包数据
        byte[] data = outputStream.toByteArray();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"xlauch.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), false, data);
    }

    /**
     * 生成代码（自定义目录）
     */
    @ResponseBody
    @PostMapping("code")
    public Result<String> code(@RequestBody Long[] tableIds) throws Exception {
        // 生成代码
        for (Long tableId : tableIds) {
            generatorService.generatorCode(tableId);
        }

        return Result.ok();
    }


    /**
     * 生成代码（自定义目录）,指定生成的内容
     */
    @ResponseBody
    @PostMapping("codeWithTemplate")
    public Result<String> codeWithTemplate(@RequestBody GenerVO generVO) throws Exception {
        if (generVO.getTemplates() == null || generVO.getTemplates().length == 0) {
            return Result.error("请选择要生成的模板");
        }

        // 生成代码
        for (Long tableId : generVO.getTableIds()) {
            generatorService.generatorCode(tableId, generVO.getTemplates());
        }

        return Result.ok();
    }

}
