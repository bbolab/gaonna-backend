package com.bbolab.gaonna.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.bbolab.gaonna.core.repository")
@EntityScan(basePackages = "com.bbolab.gaonna.core.domain")
public class DatasourceConfig {

}
