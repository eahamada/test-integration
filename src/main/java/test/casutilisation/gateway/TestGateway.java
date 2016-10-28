package test.casutilisation.gateway;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.converter.SerializingHttpMessageConverter;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import com.google.common.collect.ImmutableList;

import fr.gouv.finances.ensu.documents.ws.report.GlobalReport;
@Component
public class TestGateway {
  
  @Bean
  public MessageHandler logger() {
      LoggingHandler loggingHandler = new LoggingHandler("INFO");
      loggingHandler.setLoggerName("logger");
      return loggingHandler;
  }

  @Bean
  public MessageHandler httpGateway(@Value("http://10.153.219.91:8380/end-appli/") URI uri) {
      HttpRequestExecutingMessageHandler httpHandler = new HttpRequestExecutingMessageHandler(uri);
      httpHandler.setExpectedResponseType(GlobalReport.class);
      httpHandler.setHttpMethod(HttpMethod.POST);
      final SerializingHttpMessageConverter element = new SerializingHttpMessageConverter(){
        @Override
        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
          return true;    // DOCUMENTEZ_MOI Raccord de méthode auto-généré
        }
      };
      element.setSupportedMediaTypes(ImmutableList.of(MediaType.APPLICATION_OCTET_STREAM));
      httpHandler.setMessageConverters(
        ImmutableList.<HttpMessageConverter<?>>builder()
          .add(new ByteArrayHttpMessageConverter())
          .add(new MappingJackson2HttpMessageConverter())
          .add(element)
          .build());
      httpHandler.setErrorHandler(new DefaultResponseErrorHandler());
      return httpHandler;
  }

 
  @Bean
  public IntegrationFlow httpFlow(MessageHandler httpGateway) {
      return IntegrationFlows.from("ds05Channel")
              .handle(httpGateway)
              .handle(logger())
              .get();
  }
}
