package com.lingyuan.autocoder.api.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author LingYuan
 */
@Setter
@Getter
@ToString
public class JavaServiceImplData extends JavaServiceData {

    private String serviceName;

    private String mapperName;

}
