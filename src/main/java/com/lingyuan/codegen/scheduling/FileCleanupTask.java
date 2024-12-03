package com.lingyuan.codegen.scheduling;

import com.lingyuan.codegen.config.CodeGenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
public class FileCleanupTask {

    private final CodeGenConfig codeGenConfig;

    private final Logger logger = LoggerFactory.getLogger(FileCleanupTask.class);

    @Autowired
    public FileCleanupTask(CodeGenConfig codeGenConfig) {
        this.codeGenConfig = codeGenConfig;
    }

    /**
     * 文件最大存放时间，超过这个时间的文件会被删除
     */
    private static final long MAX_AGE_DAY = 1;

    /**
     * 定期清理过期文件
     * 每天 0 点执行：0 0 0 * * ?
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanMultipleDirectories() {
        // 待清理的目录
        final List<String> DIRECTORIES = List.of(
                codeGenConfig.getOutputDir(),
                codeGenConfig.getZipDir()
        );

        for (String dirPath : DIRECTORIES) {
            Path directory = Paths.get(dirPath);
            cleanOldFiles(directory);  // 清理每个目录
        }
    }


    public void cleanOldFiles(Path directory) {
        try (Stream<Path> filesStream = Files.walk(directory)) {
            filesStream
                    .filter(Files::isRegularFile)  // 只处理普通文件，不处理目录
                    .filter(this::isFileExpired)  // 过滤过期文件
                    .forEach(this::deleteFile);  // 删除过期文件
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        // 删除空目录
        deleteEmptyDirectories(directory);
    }

    /**
     * 判断文件是否过期
     * @param file 文件路径
     * @return true: 文件过期，false: 文件未过期
     */
    private boolean isFileExpired(Path file) {
        try {
            // 获取文件的最后修改时间
            long lastModifiedTime = Files.getLastModifiedTime(file).toMillis();
            long currentTime = System.currentTimeMillis();

            // 判断文件是否过期
            return (currentTime - lastModifiedTime) > TimeUnit.DAYS.toMillis(MAX_AGE_DAY);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;  // 如果获取文件信息失败，默认不删除
        }
    }

    /**
     * 删除文件
     * @param file 文件路径
     */
    private void deleteFile(Path file) {
        try {
            Files.delete(file);
            logger.info("Deleted file: {}", file);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 删除空目录
     *
     * @param directoryPath 目录路径
     */
    private void deleteEmptyDirectories(Path directoryPath) {
        try (Stream<Path> directoriesStream = Files.walk(directoryPath)) {
            directoriesStream
                    .filter(Files::isDirectory)  // 只处理目录
                    .sorted(Comparator.reverseOrder())  // 从文件夹深处开始删除
                    .forEach(this::deleteIfEmpty);  // 删除空目录
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 删除空目录
     *
     * @param directory 要检查并删除的目录
     */
    private void deleteIfEmpty(Path directory) {
        // 不删除根目录或非空目录
        if (directory.equals(Paths.get(codeGenConfig.getOutputDir())) || directory.equals(Paths.get(codeGenConfig.getZipDir()))) {
            logger.info("Skipping root directory: {}", directory);
            return;
        }
        try (Stream<Path> stream = Files.list(directory)) {
            // 如果目录为空，则删除该目录
            if (stream.findAny().isEmpty()) {
                Files.delete(directory);  // 删除空目录
                logger.info("Deleted empty directory: {}", directory);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
