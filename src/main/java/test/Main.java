package test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

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
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.ByteArrayMessageConverter;

import test.casutilisation.cu05.CU05Configuration;
import test.casutilisation.cu05.RequetesCU05;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import fr.gouv.finances.ensu.documents.bean.Document;


@Configuration
@IntegrationComponentScan
@ComponentScan(basePackages="test.casutilisation.gateway")
@EnableIntegration
@PropertySource(value = {"classpath:app.properties","classpath:datasource.properties"}, ignoreResourceNotFound = true)
public class Main {

  private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    final ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
    MessageChannel ds05 = ctx.getBean("ds05Channel", MessageChannel.class);
    final byte[] bytes = readBytesFromInputStream(new FileInputStream(new File("src/main/resources/casutilisation/cu05/576d4c1c4c301fc33fe4100a.pdf")));
    ds05.send(new Message<byte[] >() {

      @Override
      public MessageHeaders getHeaders() {
        
        Map<String, Object> headers = ImmutableMap.<String, Object>builder()
            .put("Content-Type", "application/octet-stream")
            .put("Accept", "application/json")
            .build();
        MessageHeaders result = new MessageHeaders(headers);
        return result;
      }

      @Override
      public byte[]  getPayload() {
        final Document element = new Document();
        element.setCdDoc("cd_doc");
        element.setIdDoc("id_doc");
        element.setIdEmpr("id_empr");
        element.setDonnees(bytes);
        byte[] result = new SerializingConverter().convert(element);
        return result;       
      }
    });
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
  public static DataSource dataSource(@Value("${datasource.properties:src/main/resources/datasource.properties}")  String propertiesFile){
    HikariConfig config = new HikariConfig(propertiesFile);
    HikariDataSource result = new HikariDataSource(config);
    return result;
  }
  
  @Bean
  public static ObjectMapper objectMapper(){
    final ObjectMapper result = new ObjectMapper(new YAMLFactory());
    result.registerModule(new SimpleModule(){
      private static final long serialVersionUID = 6696953139605352745L;

      @Override
      public void setupModule(SetupContext context)
      {
        context.setMixInAnnotations(CU05Configuration.class, CU05ConfigurationMixin.class);
        context.setMixInAnnotations(RequetesCU05.class, RequetesCU05Mixin.class);
      }
    });
    
    return result;
  }

  private static byte[] readBytesFromInputStream(InputStream in) throws IOException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final byte[] buffer = new byte[8192];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) != -1) {
        baos.write(buffer, 0, bytesRead);
    }
    final byte[] bytes = baos.toByteArray();
    return bytes;
  }
  private static void closeQuietly(InputStream... ins) throws IOException {
    for (InputStream in : ins) {
      if (in != null) {
          in.close();
      }
    }
  }

  private static void closeQuietly(OutputStream... outs) throws IOException {
    for (OutputStream out : outs) {
      if (out != null) {
          out.close();
      }
    }
  }
}
