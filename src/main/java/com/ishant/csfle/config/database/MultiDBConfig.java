package com.ishant.csfle.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
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
        return new MongoTemplate(appDBMongoDatabaseFactory(getAppDBProperties()));
    }

    @Bean(name = "keyVaultDBMongoTemplate")
    public MongoTemplate keyVaultDBMongoTemplate() throws Exception {
        return new MongoTemplate(keyVaultDBMongoDatabaseFactory(getKeyVaultDBProperties()));
    }

    @Primary
    @Bean(name = "appDBMongoDatabaseFactory")
    public MongoDatabaseFactory appDBMongoDatabaseFactory(MongoProperties mongo) throws Exception {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

    @Bean(name = "keyVaultDBMongoDatabaseFactory")
    public MongoDatabaseFactory keyVaultDBMongoDatabaseFactory(MongoProperties mongo) throws Exception {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

    @Primary
    @Bean("appDBTransactionManager")
    public MongoTransactionManager appDBTransactionManager(@Qualifier("appDBMongoDatabaseFactory") MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean("keyVaultDBTransactionManager")
    public MongoTransactionManager keyVaultDBTransactionManager(@Qualifier("keyVaultDBMongoDatabaseFactory") MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}