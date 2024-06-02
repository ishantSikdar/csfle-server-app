package com.ishant.csfle.config.database;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MultiDBConfig {
    @Primary
    @Bean(name = "appDBProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.app")
    public MongoProperties getAppDBProperties() throws Exception {
        return new MongoProperties();
    }

    @Bean(name = "keyVaultDBProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.keyvault")
    public MongoProperties getKeyVaultDBProperties() throws Exception {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "appDBMongoTemplate")
    public MongoTemplate appDBMongoTemplate() throws Exception {
        return new MongoTemplate(newdb1MongoDatabaseFactory(getAppDBProperties()));
    }

    @Bean(name = "keyVaultDBMongoTemplate")
    public MongoTemplate keyVaultDBMongoTemplate() throws Exception {
        return new MongoTemplate(newdb2MongoDatabaseFactory(getKeyVaultDBProperties()));
    }

    @Primary
    @Bean
    public MongoDatabaseFactory newdb1MongoDatabaseFactory(MongoProperties mongo) throws Exception {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

    @Bean
    public MongoDatabaseFactory newdb2MongoDatabaseFactory(MongoProperties mongo) throws Exception {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

}