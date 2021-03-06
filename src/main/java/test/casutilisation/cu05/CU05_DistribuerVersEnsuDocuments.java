package test.casutilisation.cu05;

import java.net.URI;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.converter.SerializingHttpMessageConverter;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.inbound.RequestMapping;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.google.common.collect.ImmutableList;

import fr.gouv.finances.ensu.documents.bean.Document;
import fr.gouv.finances.ensu.documents.ws.report.GlobalReport;

@Component
public class CU05_DistribuerVersEnsuDocuments {
  
  @Bean
  public MessageSource<Object> fluxATraiter(final CU05Configuration cu05configuration ) {
    final JdbcPollingChannelAdapter result = new JdbcPollingChannelAdapter(cu05configuration.dataSource, cu05configuration.requetes.identificationFluxATraiter);
    result.setMaxRowsPerPoll(cu05configuration.maxDocuments);
    result.setRowMapper(new RowMapper<Document>() {
      @Override
      public Document mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Document result = new Document();
        result.setDonnees(resultSet.getBytes("fichier_pdf"));
        result.setCdDoc(resultSet.getString("cd_doc"));
        result.setIdDoc(resultSet.getString("id_tag"));
        result.setIdEmpr(resultSet.getString("id_empr"));
        return result;
      }
    });
    return result;
  }
  
  @Bean
  public MessageHandler httpGateway(@Value("http://10.153.219.91:8380/end-appli/") URI uri) {
    HttpRequestExecutingMessageHandler httpHandler = new HttpRequestExecutingMessageHandler(uri);
    httpHandler.setExpectedResponseType(GlobalReport.class);
    httpHandler.setHttpMethod(HttpMethod.GET);
    httpHandler.setMessageConverters(
      ImmutableList.<HttpMessageConverter<?>>builder()
        .add(new ByteArrayHttpMessageConverter())
        .add(new MappingJackson2HttpMessageConverter())
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
private MessageHandler logger() {
    LoggingHandler loggingHandler = new LoggingHandler("INFO");
    loggingHandler.setLoggerName("logger");
    return loggingHandler;
}
  public MessageChannel requestChannel() {
      return MessageChannels.direct().get();
  }

  
  private List<HttpMessageConverter<?>> messageConverters() {
    final MappingJackson2HttpMessageConverter jacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
    jacksonHttpMessageConverter.setSupportedMediaTypes(ImmutableList.<MediaType>builder()
        .add(MediaType.APPLICATION_JSON)
        .add(MediaType.APPLICATION_OCTET_STREAM)
        .build());
    final List<HttpMessageConverter<?>> result = ImmutableList.<HttpMessageConverter<?>>builder()
        .add(jacksonHttpMessageConverter)
        .build();
    return result;
  }


  public Consumer<SourcePollingChannelAdapterSpec> cu05poller(){
    final Consumer<SourcePollingChannelAdapterSpec> result = new Consumer<SourcePollingChannelAdapterSpec>() {

      public void accept(final SourcePollingChannelAdapterSpec t) {
            t.poller(Pollers.fixedRate(5, TimeUnit.SECONDS));
      }};
    return result;
      
  }
  
//  public IntegrationFlow cu05Flow(@Qualifier("fluxATraiter") final MessageSource<?> messageSource,  @Qualifier("constitutionDS05") HttpRequestHandlingMessagingGateway gateway) {
//    final IntegrationFlow result = IntegrationFlows
//        .from(messageSource, cu05poller())
//        .handle(gateway)
//        .get();
//    gateway.getRequestChannel().send(MessageBuilder.withPayload(new Document()).build());
//    return result;
//  }
  
  public HttpRequestHandlingMessagingGateway httpGate() {
      HttpRequestHandlingMessagingGateway result = new HttpRequestHandlingMessagingGateway(true);
//      RequestMapping requestMapping = new RequestMapping();
//      requestMapping.setMethods(HttpMethod.POST);
//      requestMapping.setPathPatterns("/foo");
//      result.setRequestMapping(requestMapping);
//      result.setRequestChannel(requestChannel());
      return result;
  }

  @Bean
  public IntegrationFlow flow() {
      return IntegrationFlows.from(requestChannel())
              .handle(httpGate())
              .get();
  }
}
