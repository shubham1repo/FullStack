//package com.example.backend.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.DatabasePopulator;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ResourceLoader;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatabaseConfig {
//
//    @Autowired
//    private ResourceLoader resourceLoader;
//
//    @Value("${spring.sql.init.schema-locations}")
//    private String schemaLocation;
//
//    @Bean
//    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//        Resource resource = resourceLoader.getResource(schemaLocation);
//        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
//        DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(databasePopulator);
//        return initializer;
//    }
//}
//
