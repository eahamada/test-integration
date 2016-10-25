package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@IntegrationComponentScan
@ComponentScan(basePackages="test.casutilisation.cu01")
@EnableIntegration
@PropertySource(value = {"classpath:app.properties","classpath:datasource.properties"}, ignoreResourceNotFound = true)
public class Main {

  private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    final ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
    LOGGER.info("Hit 'Enter' to terminate");
    getReader().readLine();
    ctx.close();
  }

  private static BufferedReader getReader() {
    final BufferedReader result = new BufferedReader(new InputStreamReader(System.in));
    return result;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    final PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
    return result;
  }
  
  @Bean(destroyMethod="close")
  public static DataSource datasource(@Value("${datasource.properties:src/main/resources/datasource.properties}")  String propertiesFile){
    HikariConfig config = new HikariConfig(propertiesFile);
    HikariDataSource result = new HikariDataSource(config);
    return result;
  }
  
}
