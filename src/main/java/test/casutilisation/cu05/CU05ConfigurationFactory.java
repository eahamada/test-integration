package test.casutilisation.cu05;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CU05ConfigurationFactory {
  
  @Bean
  public CU05Configuration cu05Configuration(final ObjectMapper objectMapper, final ClientHttpRequestFactory clientHttpRequestFactory, final DataSource dataSource) {
    CU05Configuration c;
    try {
      c = objectMapper.readValue(new File("src/main/resources/casutilisation/cu05/cu05_configuration.yaml"), CU05Configuration.class);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    final CU05Configuration result = c.toBuilder()
        .withClientHttpRequestFactory(clientHttpRequestFactory)
        .withDataSource(dataSource)        
        .build();
    return result;
  }
  
  @Bean
  public ClientHttpRequestFactory clientHttpRequestFactory() {
    final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(200);
    cm.setDefaultMaxPerRoute(20);
    final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
    final ClientHttpRequestFactory result = new HttpComponentsClientHttpRequestFactory(httpClient);
    return result;
  }
}
