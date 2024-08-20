package com.lingyuan.api.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

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
