package com.turkcell.poc.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

  @Primary
  @Bean(name = "transactionManager")
  PlatformTransactionManager transactionManager(@Autowired DataSource datasource) {
    return new DataSourceTransactionManager(datasource);
  }
}
