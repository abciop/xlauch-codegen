package com.xlauch.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 表单布局 枚举
 *
 * @author 阿沐 babamu@126.com
 */
@Getter
@AllArgsConstructor
public enum FormLayoutEnum {
    ONE(1),
    TWO(2);
    
    private final Integer value;
}
