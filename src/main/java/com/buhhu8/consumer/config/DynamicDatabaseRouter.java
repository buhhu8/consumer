package com.buhhu8.consumer.config;

import com.buhhu8.consumer.config.Persistance.AbstractRoutingDataSourceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "com.buhhu8.consumer", entityManagerFactoryRef = "entityManager")
public class DynamicDatabaseRouter {

    public static final String PROPERTY_PREFIX = "spring.datasource.";

    private final Environment environment;

    @Bean
    @Primary
    @Scope("prototype")
    public AbstractRoutingDataSourceImpl dataSource() {
        Map<Object, Object> targetDataSources = getTargetDataSources();
        return new AbstractRoutingDataSourceImpl((DataSource)targetDataSources.get("default"), targetDataSources);
    }

    private Map<Object,Object> getTargetDataSources() {

        List<String> databaseNames = environment.getProperty("spring.database-names.list",List.class);
        Map<Object,Object> targetDataSourceMap = new HashMap<>();

        for (String dbName : databaseNames) {

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(environment.getProperty(PROPERTY_PREFIX + dbName + ".driver-class-name"));
            dataSource.setUrl(environment.getProperty(PROPERTY_PREFIX + dbName + ".url"));
            dataSource.setUsername(environment.getProperty(PROPERTY_PREFIX + dbName + ".username"));
            dataSource.setPassword(environment.getProperty(PROPERTY_PREFIX + dbName + ".password"));
            dataSource.setSchema(environment.getProperty(PROPERTY_PREFIX + dbName + ".schema"));
            targetDataSourceMap.put(dbName,dataSource);

        }
        targetDataSourceMap.put("default",targetDataSourceMap.get(databaseNames.get(0)));
        return targetDataSourceMap;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.buhhu8.consumer.model");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.dialect", environment.getProperty("spring.jpa.database-platform"));
        em.setJpaPropertyMap(properties);
        return em;
    }
}