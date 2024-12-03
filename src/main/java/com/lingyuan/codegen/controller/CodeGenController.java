package com.lingyuan.codegen.controller;

import com.lingyuan.codegen.config.CodeGenConfig;
import com.lingyuan.codegen.req.CodeGenRequest;
import com.lingyuan.codegen.resp.ApiResponse;
import com.lingyuan.codegen.service.CodeGenService;
import net.sf.jsqlparser.JSQLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class CodeGenController {

    private final CodeGenService codeGenService;
    private final CodeGenConfig codeGenConfig;

    @Autowired
    public CodeGenController(CodeGenService codeGenService, CodeGenConfig codeGenConfig) {
        this.codeGenService = codeGenService;
        this.codeGenConfig = codeGenConfig;
    }

    @PostMapping("/generate-code")
    public ApiResponse<String> generateCode(@RequestBody CodeGenRequest request) throws JSQLParserException, IOException {
        return ApiResponse.success(codeGenService.generateCode(request));
    }

    @PostMapping("/generate-multiple")
    public ApiResponse<String> generateMultiple(@RequestBody CodeGenRequest request) throws JSQLParserException, IOException {
        return ApiResponse.success(codeGenService.generateFile(request));
    }

    /** 下载文件接口 */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadZip(@PathVariable String fileName) throws IOException {
        // 修改为 ZIP 文件实际存储路径
        Path zipFilePath = Paths.get(codeGenConfig.getZipDir() + fileName);

        // 检查文件是否存在
        if (Files.exists(zipFilePath)) {
            byte[] zipFileContent = Files.readAllBytes(zipFilePath);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + zipFilePath.getFileName())
                    .body(zipFileContent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

}
