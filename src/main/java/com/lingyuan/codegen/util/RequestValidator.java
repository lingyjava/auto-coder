package com.lingyuan.codegen.util;

import com.lingyuan.codegen.enums.FileType;

public class RequestValidator {

    /**
     * 验证文件类型是否有效
     */
    public static boolean validateFileTypes(String fileType) {
        return FileType.getFileType(fileType) != null;
    }
}
