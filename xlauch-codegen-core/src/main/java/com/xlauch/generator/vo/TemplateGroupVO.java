package com.xlauch.generator.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.xlauch.generator.entity.TemplateEntity;
import lombok.Data;

import java.util.List;

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
public class TemplateGroupVO {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 基类包名
     */
    private String groupName;

    /**
     * 模板列表
     */
    private List<TemplateEntity> templateList;
}
