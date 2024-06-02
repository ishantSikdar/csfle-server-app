package com.ishant.csfle.config.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = { "com.ishant.csfle.repository.appDB" },
        mongoTemplateRef = AppDBConfig.MONGO_TEMPLATE
)
public class AppDBConfig {
    protected static final String MONGO_TEMPLATE = "appDBMongoTemplate";
}