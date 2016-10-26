package test.casutilisation.cu05;

import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.http.converter.SerializingHttpMessageConverter;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

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
  public MessageHandler constitutionDS05(final CU05Configuration cu05configuration){
    final HttpRequestExecutingMessageHandler result = new HttpRequestExecutingMessageHandler(cu05configuration.ensuDocumentsUrl);
    result.setRequestFactory(cu05configuration.clientHttpRequestFactory);
    result.setHttpMethod(HttpMethod.POST);
    result.setMessageConverters(messageConverters());
    result.setExpectedResponseType(GlobalReport.class);
    return result;
  }
  
  private List<HttpMessageConverter<?>> messageConverters() {
    final List<HttpMessageConverter<?>> result = ImmutableList.<HttpMessageConverter<?>>builder()
        .add(new ByteArrayHttpMessageConverter())
        .add(new StringHttpMessageConverter(Charset.forName("UTF-8")))
        .add(new MappingJackson2HttpMessageConverter())
        .add(new SerializingHttpMessageConverter())
        .build();
    return result;
  }

  @Bean
  public MessageHandler traitementReponseDS05(final CU05Configuration cu05configuration ){
//    clientHttpRequestFactory.createRequest(null,null).execute();
    final HttpRequestExecutingMessageHandler result = new HttpRequestExecutingMessageHandler(cu05configuration.ensuDocumentsUrl);
    result.setRequestFactory(cu05configuration.clientHttpRequestFactory);
    return result;
  }
  
  @Bean
  public Consumer<SourcePollingChannelAdapterSpec> cu05poller(){
    final Consumer<SourcePollingChannelAdapterSpec> result = new Consumer<SourcePollingChannelAdapterSpec>() {

      public void accept(final SourcePollingChannelAdapterSpec t) {
            t.poller(Pollers.fixedRate(5, TimeUnit.SECONDS));
      }};
    return result;
      
  }
  
  @Bean
  public IntegrationFlow cu05Flow(@Qualifier("fluxATraiter") final MessageSource<?> messageSource, @Qualifier("cu05poller") final Consumer<SourcePollingChannelAdapterSpec> consumer, @Qualifier("constitutionDS05") MessageHandler messageHandler) {
    messageHandler.handleMessage(new GenericMessage<Document>(new Document()));
    return simpleIntegrationFlow(messageSource, consumer, messageHandler);
  }
  
  @Bean
  public IntegrationFlow testCu05Flow(@Qualifier("fluxATraiter") final MessageSource<?> messageSource, @Qualifier("cu05poller") final Consumer<SourcePollingChannelAdapterSpec> consumer, @Qualifier("constitutionDS05") MessageHandler messageHandler) {
    return simpleIntegrationFlow(messageSource, consumer, messageHandler);
  }
  

  private  IntegrationFlow simpleIntegrationFlow( final MessageSource<?> messageSource, final Consumer<SourcePollingChannelAdapterSpec> consumer, final MessageHandler messageHandler) {
    final IntegrationFlow result = IntegrationFlows
        .from(messageSource, consumer)
        .handle(messageHandler)
        .get();
    return result;
  }
}
