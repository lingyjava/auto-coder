package com.lingyuan.codegen.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class CodeGenRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3293089521592128805L;

    /**
     * 建表语句
     * */
    private String ddl;

    /**
     * 包名
     * */
    private String basePackage;

    /**
     * 代码作者信息
     */
    private String author;

    private String fileType;

    private boolean useLombok;

    private boolean useSerializable;

}
