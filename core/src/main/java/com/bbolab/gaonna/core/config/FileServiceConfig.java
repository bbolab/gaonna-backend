package com.bbolab.gaonna.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FileServiceConfig {
    @Value("${bbolab.fileservice.root:}")
    private String fileStorageRoot;
}
