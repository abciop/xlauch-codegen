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
@TableName("gen_template_group")
public class TemplateGroupEntity {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 基类包名
     */
    private String groupName;
}
