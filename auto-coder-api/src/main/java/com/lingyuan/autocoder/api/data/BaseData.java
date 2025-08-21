package com.lingyuan.autocoder.api.data;

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
public class BaseData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fileName;
}
