package test.casutilisation.gateway;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import fr.gouv.finances.ensu.documents.ws.report.GlobalReport;
@Component
public class TestGateway {
  
  @Bean
  public MessageHandler logger() {
      LoggingHandler loggingHandler = new LoggingHandler("DEBUG");
      loggingHandler.setLoggerName("logger");
      return loggingHandler;
  }

  @Bean
  public MessageHandler httpGateway(@Value("http://10.153.219.91:8380/end-appli/") URI uri) {
      HttpRequestExecutingMessageHandler httpHandler = new HttpRequestExecutingMessageHandler(uri);
      httpHandler.setExpectedResponseType(GlobalReport.class);
      httpHandler.setHttpMethod(HttpMethod.POST);
      httpHandler.setMessageConverters(messageConverters());
      return httpHandler;
  }

  private List<HttpMessageConverter<?>> messageConverters() {
    final List<HttpMessageConverter<?>> result = ImmutableList.<HttpMessageConverter<?>>builder()
        .add(new ByteArrayHttpMessageConverter())
        .add(new MappingJackson2HttpMessageConverter())
        .build();
    return result;
  }
  @Bean
  public IntegrationFlow httpFlow(MessageHandler httpGateway) {
      return IntegrationFlows.from("ds05Channel")
              .handle(httpGateway)
              .handle(logger())
              .get();
  }
}
