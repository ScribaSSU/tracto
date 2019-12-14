package com.scribassu.tracto.config;

/*
Configuration for @CreatedBy and @CreatedDate
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableJpaRepositories("com.scribassu.tracto.repository")
@Component
public class JpaConfig {

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
