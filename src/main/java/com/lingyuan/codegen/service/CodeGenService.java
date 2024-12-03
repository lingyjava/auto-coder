package com.lingyuan.codegen.service;

import com.lingyuan.codegen.config.CodeGenConfig;
import com.lingyuan.codegen.enums.FileType;
import com.lingyuan.codegen.exception.BusinessException;
import com.lingyuan.codegen.model.TableMetadata;
import com.lingyuan.codegen.req.CodeGenRequest;
import com.lingyuan.codegen.util.*;
import net.sf.jsqlparser.JSQLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CodeGenService {

    private final CodeGenConfig codeGenConfig;

    @Autowired
    public CodeGenService(CodeGenConfig codeGenConfig) {
        this.codeGenConfig = codeGenConfig;
    }

    /**
     * 生成一个代码文件并返回代码字符串
     */
    public String generateCode(CodeGenRequest request) throws JSQLParserException, IOException {
        if (!RequestValidator.validateFileTypes(request.getFileType())) {
            throw new IllegalArgumentException("not found file type");
        }

        // 解析 DDL，获取列的元信息
        TableMetadata tableMetadata = SQLParserUtil.parseCreateTable(request.getDdl());

        // 准备数据模型
        Map<String, Object> dataModel = prepareDataModel(tableMetadata, request);

        return FreeMarkerUtil.generate(Objects.requireNonNull(FileType.getFileType(request.getFileType())).getTemplatePath(), dataModel);
    }

    /**
     * 生成代码文件并返回压缩包路径
     *
     * @param request 请求参数
     * @return 压缩包路径
     */
    public String generateFile(CodeGenRequest request) throws JSQLParserException, IOException {
        // 解析 DDL，获取列的元信息
        TableMetadata tableMetadata = SQLParserUtil.parseCreateTable(request.getDdl());

        // 准备数据模型
        Map<String, Object> dataModel = prepareDataModel(tableMetadata, request);

        // 创建输出目录
        String outputPath = FileUtil.createOutputDirectory(codeGenConfig.getOutputDir());

        // 遍历需要生成的文件类型
        for (FileType f : FileType.values()) {
            generateFile(f, dataModel, outputPath);
        }

        // 打包生成的文件
        String fileName = new File(outputPath).getName() + ".zip";
        FileUtil.zipDirectory(outputPath, codeGenConfig.getZipDir() + fileName);
        return fileName;
    }

    /**
     * 准备数据模型
     *
     * @param tableMetadata 表元信息
     * @param request 请求参数
     * @return 数据模型
     */
    private Map<String, Object> prepareDataModel(TableMetadata tableMetadata, CodeGenRequest request) {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("tableName", tableMetadata.getTableName());
        dataModel.put("columns", tableMetadata.getColumns());
        dataModel.put("basePackage", request.getBasePackage());
        dataModel.put("author", request.getAuthor());
        dataModel.put("className", TypeConverter.convertToClassName(tableMetadata.getTableName()));
        dataModel.put("useLombok", request.isUseLombok());
        dataModel.put("useSerializable", request.isUseSerializable());
        dataModel.put("importTypes", TypeConverter.getImportTypes(tableMetadata.getColumns()));
        return dataModel;
    }

    /**
     * 根据文件类型生成文件
     *
     * @param fileType   文件类型
     * @param dataModel  数据模型
     * @param outputDir  输出目录
     */
    private void generateFile(FileType fileType, Map<String, Object> dataModel, String outputDir) {
        try {
            String content = FreeMarkerUtil.generate(fileType.getTemplatePath(), dataModel);
            String fileName = fileType.getFileNameTemplate().replace("{className}", (String) dataModel.get("className"));
            FileUtil.writeToFile(outputDir + File.separator + fileName, content);
        } catch (IOException e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to generate " + fileType.getType() + " file.");
        }
    }
}
