package net.safedata.java.advanced.training.testing.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "net.safedata.java.advanced.training.testing.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "net.safedata.java.advanced.training.jpa.model")
public class ProductServiceConfig {
}
