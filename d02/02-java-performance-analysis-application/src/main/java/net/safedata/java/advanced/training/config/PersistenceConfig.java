package net.safedata.java.advanced.training.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

//@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("net.safedata.java.advanced.training.domain.repository")
@EntityScan("net.net.safedata.java.advanced.training.domain.model")
public class PersistenceConfig {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public javax.sql.DataSource dataSource() {
        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName("connection-pool");
        hikariConfig.setMaximumPoolSize(AVAILABLE_PROCESSORS * 2);
        hikariConfig.setMinimumIdle(AVAILABLE_PROCESSORS / 2);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(60000);
        hikariConfig.setMaxLifetime(120000);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);

        return getDataSource(hikariConfig);
    }

    @Bean(destroyMethod = "close")
    public HikariDataSource getDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setAnnotatedPackages("net.safedata.java.advanced.training.domain.model");
        localSessionFactoryBean.setDataSource(dataSource());

        Properties properties = new Properties();
        properties.setProperty("jakarta.persistence.jdbc.url", "org.hibernate.dialect.OracleDialect");

        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }
}
