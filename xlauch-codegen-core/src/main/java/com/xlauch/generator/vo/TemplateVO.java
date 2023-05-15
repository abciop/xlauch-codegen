package com.xlauch.generator.vo;

import lombok.Data;

/**
 * <p>
 * 类描述 : 模板VO
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/9 11:21
 */
@Data
public class TemplateVO {

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 简单名称，用于显示
     */
    private String simpleName ;
}
