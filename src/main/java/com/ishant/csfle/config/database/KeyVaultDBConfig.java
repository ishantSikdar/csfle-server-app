package com.ishant.csfle.config.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = { "com.ishant.csfle.repository.keyVault" },
        mongoTemplateRef = KeyVaultDBConfig.MONGO_TEMPLATE
)
public class KeyVaultDBConfig {
    protected static final String MONGO_TEMPLATE = "keyVaultDBMongoTemplate";
}