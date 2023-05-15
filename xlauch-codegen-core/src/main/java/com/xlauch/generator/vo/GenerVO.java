package com.xlauch.generator.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 类描述 : 代码生成VO
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/10 9:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerVO {

    /**
     * id
     */
    private Long[] tableIds;

    /**
     * 要生成的模板
     */
    private Long[] templates;
}
