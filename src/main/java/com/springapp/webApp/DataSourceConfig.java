package com.springapp.webApp;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "db1DataSource")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/db1")
                .username("postgres")
                .password("postgre")
                .build();
    }

    @Bean(name = "db2DataSource")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/db2")
                .username("postgres")
                .password("postgre")
                .build();
    }


    @Bean
    public DataSource routingDataSource(@Qualifier("db1DataSource") DataSource db1DataSource,
                                        @Qualifier("db2DataSource") DataSource db2DataSource) {
        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DataSourceContextHolder.getDataSource();
            }
        };

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("db1", db1DataSource);
        targetDataSources.put("db2", db2DataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(db1DataSource);
        return routingDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource routingDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(routingDataSource);
        entityManagerFactoryBean.setPackagesToScan("com.springapp.webApp");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }



}

