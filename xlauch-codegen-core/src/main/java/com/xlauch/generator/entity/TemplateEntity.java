package com.xlauch.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 15:00
 */
@Data
@TableName("gen_template")
public class TemplateEntity {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 分组id
     */
    private Long groupId;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 备注名称
     */
    private String simpleName;
    /**
     * 文件名称
     */
    private String generatorPath;
    /**
     * 模板内容
     */
    private String templateContent;
}
