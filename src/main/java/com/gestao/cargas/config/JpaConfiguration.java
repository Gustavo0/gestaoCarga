package com.gestao.cargas.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableTransactionManagement
public class JpaConfiguration {
	
	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.jpa.database-platform}")
	private String databasePlatform;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;
	
	@Value("${spring.jpa.properties.hibernate.show_sql}")
	private String showSql;
	
	@Value("${spring.jpa.properties.hibernate.format_sql}")
	private String formatSql;

    //@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(jpaVendorAdapter );

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClassName);

        factoryBean.setDataSource(dataSource);

        Properties props = new Properties();
        props.setProperty("spring.jpa.database-platform" , databasePlatform);
        props.setProperty("spring.jpa.hibernate.ddl-auto", ddlAuto);
        props.setProperty("spring.jpa.properties.hibernate.show_sql", showSql);
        props.setProperty("spring.jpa.properties.hibernate.format_sql", formatSql);

        factoryBean.setJpaProperties(props);

        factoryBean.setPackagesToScan("com.gestao.cargas.entity");

        return factoryBean;

    }
    
    public JpaTransactionManager transactionManager (EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
