package com.lingyuan.codegen.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Configuration
public class CodeGenConfig {

    @Value("${output-dir}")
    private String outputDir;

    @Value("${zip-dir}")
    private String zipDir;

    private final Logger logger = LoggerFactory.getLogger(CodeGenConfig.class);

    public void createRootDirectory(String rootDirectory) {
        try {
            Path path = Paths.get(rootDirectory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                logger.info("Root directory created at: {}", path.toAbsolutePath());
            }
        } catch (Exception e) {
            logger.error("Failed to create root directory: {}", e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        this.createRootDirectory(outputDir);
        this.createRootDirectory(zipDir);
    }

}
