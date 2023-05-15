package com.xlauch.generator.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import com.xlauch.generator.common.exception.ServerException;
import com.xlauch.generator.common.utils.Result;
import com.xlauch.generator.entity.TemplateGroupEntity;
import com.xlauch.generator.service.TemplateGroupService;
import com.xlauch.generator.vo.TemplateGroupVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 14:55
 */
@Controller
@RequestMapping("xlauch-codegen/gen/templateGroup")
@AllArgsConstructor
public class TeamplateGroupController {

    private final TemplateGroupService templateGroupService;

    /**
     * 获取模板列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("list")
    public Result<List<TemplateGroupVO>> list() {
        return templateGroupService.getGroupList();
    }


    /**
     * 新增分组
     *
     * @param entity
     * @return
     */
    @PostMapping
    @ResponseBody
    public Result<String> save(@RequestBody TemplateGroupEntity entity) {
        return templateGroupService.insertOrUpdate(entity);
    }

    /**
     * 修改分组
     *
     * @param entity
     * @return
     */
    @PutMapping
    @ResponseBody
    public Result<String> update(@RequestBody @Valid TemplateGroupEntity entity) {
        return templateGroupService.insertOrUpdate(entity);
    }

    /**
     * 删除分组
     *
     * @param id
     * @return
     */
    @GetMapping("delete/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Long id) {
        return templateGroupService.deleteGroup(id);
    }


    /**
     * 导出
     *
     * @param id
     */
    @GetMapping("download")
    public void download(Long id, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        // 生成代码
        templateGroupService.downloadCode(id, zip);
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
     * @param groupId
     * @param file
     * @return
     */
    @PostMapping("/upload/{groupId}")
    @ResponseBody
    public Result<String> upload(@PathVariable("groupId") Long groupId, @RequestParam("file") MultipartFile file) {
        String uploadPath = "e://";
        String filePath = transferToFile(uploadPath, file);
        System.out.println("filePath : " + filePath);
        return templateGroupService.parseFile(filePath, groupId);
    }


    /**
     * 把文件上传转成本地文件
     *
     * @param uploadPath
     * @param file
     * @return
     */
    public String transferToFile(String uploadPath, MultipartFile file) {
        FileUtil.mkdir(uploadPath + DateUtil.today());
        String filePath = uploadPath + DateUtil.today() + "/" + IdUtil.fastSimpleUUID() + "." + FileNameUtil.getSuffix(file.getOriginalFilename());
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new ServerException("transferToFile fail");
        }
        return filePath;
    }
}
