package com.xlauch.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 类描述 : 项目实体类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/12 11:11
 */
@Data
@TableName("gen_project")
public class ProjectEntity {

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 项目名
     */
    private String projectName;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 版本号
     */
    private String version;
    /**
     * 后端项目路径
     */
    private String backendPath;
    /**
     * 前端项目路径
     */
    private String frontendPath;
    /**
     * 作者
     */
    private String author;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 创建时间
     */
    private Date createTime;

}
