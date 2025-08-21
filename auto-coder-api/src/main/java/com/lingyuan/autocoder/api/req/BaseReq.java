package com.lingyuan.autocoder.api.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author LingYuan
 */
@Getter
@Setter
@ToString
public class BaseReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 生成类型
     *
     * @see BusinessTypeEnum
     */
    // private String generateType;

    /**
     * 建表语句
     * */
    private String ddl;

    /**
     * 数据库类型
     * */
    private String dbType = "MySQL";

    /**
     * 使用 Lombok
     * */
    private boolean lombok = false;

    /**
     * 使用序列化
     * */
    private boolean serializable = false;

    /**
     * 包名
     * */
    private String packageName;

    /**
     * 作者名
     * */
    private String author = "AutoCoder";
}
