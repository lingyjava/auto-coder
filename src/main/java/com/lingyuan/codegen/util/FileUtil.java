package com.lingyuan.codegen.util;

import com.lingyuan.codegen.config.CodeGenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    @Autowired
    private static CodeGenConfig codeGenConfig;

    /**
     * 创建输出目录
     * 输出目录命名为 "code_" + 当前时间戳
     * @return 输出目录路径
     */
    public static String createOutputDirectory(String prefix) {
        // 创建一个子目录，使用时间戳或其他唯一标识符来避免冲突
        String timestamp = String.valueOf(System.currentTimeMillis());
        String outputDir = "code_" + timestamp;
        Path path = Paths.get(prefix + outputDir);
        try {
            // 判断目录是否存在
            if (!Files.exists(path)) {
                // 如果目录不存在，创建目录
                Files.createDirectories(path);
            }
        } catch(IOException e) {
            logger.error(e.getMessage());
        }
        return path.toAbsolutePath().toString();
    }

    /**
     * 将生成的内容写入到文件
     * @param filePath 文件路径
     * @param content  文件内容
     */
    public static void writeToFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());  // 创建父目录
        try (FileWriter writer = new FileWriter(path.toFile())) {
            writer.write(content);
        }
    }

    /**
     * 将目录压缩为 Zip 文件
     * @param sourceDir 需要压缩的目录
     * @param zipFilePath 压缩后的文件路径
     * @return 压缩文件的路径
     */
    public static String zipDirectory(String sourceDir, String zipFilePath) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(Paths.get(zipFilePath)))) {
            File dirToZip = new File(sourceDir);
            zipFile(dirToZip, dirToZip.getName(), zipOut);
        }
        return zipFilePath;
    }

    /**
     * 递归压缩目录中的文件
     * @param fileToZip 需要压缩的文件或目录
     * @param fileName 压缩后的文件名
     * @param zipOut ZipOutputStream
     */
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isDirectory()) {
            // 如果是目录，递归处理
            for (File file : Objects.requireNonNull(fileToZip.listFiles())) {
                zipFile(file, fileName + File.separator + file.getName(), zipOut);
            }
        } else {
            // 如果是文件，写入到 ZIP 中
            try (var fileInputStream = Files.newInputStream(fileToZip.toPath())) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                // 直接复制文件内容到压缩流
                fileInputStream.transferTo(zipOut);
                zipOut.closeEntry();
            }
        }
    }

}
