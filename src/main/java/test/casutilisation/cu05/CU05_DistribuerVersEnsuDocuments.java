package test.casutilisation.cu05;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import fr.gouv.finances.ensu.documents.bean.Document;
import fr.gouv.finances.ensu.documents.ws.report.GlobalReport;

@Component
public class CU05_DistribuerVersEnsuDocuments {
  
  @Bean
  public MessageSource<Object> fluxATraiter(DataSource datasource, RequetesCU05 requetes ) {
    final JdbcPollingChannelAdapter jdbcPollingChannelAdapter = new JdbcPollingChannelAdapter(datasource, requetes.trouverDocumentParEtat);
    jdbcPollingChannelAdapter.setRowMapper(new RowMapper<Document>() {
      @Override
      public Document mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Document result = new Document();
        result.setDonnees(resultSet.getBytes("fichier_pdf"));
        result.setCdDoc(resultSet.getString("cd_doc"));
        result.setCdDoc(resultSet.getString("id_tag"));
        result.setCdDoc(resultSet.getString("cd_doc"));
        return result;
      }
    });
    return jdbcPollingChannelAdapter;
  }
  
  @Bean
  public MessageHandler constitutionDS05(@Value("${ensuDocumentsUrl:http://10.153.219.91:8380/end-appli/}") final String ensuDocumentsUrl, final ClientHttpRequestFactory clientHttpRequestFactory ){
    final HttpRequestExecutingMessageHandler result = new HttpRequestExecutingMessageHandler(ensuDocumentsUrl);
    result.setRequestFactory(clientHttpRequestFactory);
    result.setExpectedResponseType(GlobalReport.class);
    return result;
  }
  
  @Bean
  public MessageHandler traitementReponseDS05(@Value("${ensuDocumentsUrl:http://10.153.219.91:8380/end-appli/}") final String ensuDocumentsUrl, final ClientHttpRequestFactory clientHttpRequestFactory ){
//    clientHttpRequestFactory.createRequest(null,null).execute();
    final HttpRequestExecutingMessageHandler result = new HttpRequestExecutingMessageHandler(ensuDocumentsUrl);
    result.setRequestFactory(clientHttpRequestFactory);
    return result;
  }
 
  
  @Bean(name="requestFactory")
  public ClientHttpRequestFactory clientHttpRequestFactory() {
    final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(200);
    cm.setDefaultMaxPerRoute(20);
    final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
    final ClientHttpRequestFactory result = new HttpComponentsClientHttpRequestFactory(httpClient);
    return result;
  }

  private  IntegrationFlow simpleIntegrationFlow( @Qualifier("") MessageSource<?> messageSource, Consumer<SourcePollingChannelAdapterSpec> consumer, MessageHandler messageHandler) {
    final IntegrationFlow result = IntegrationFlows
        .from(messageSource, consumer)
        .handle(messageHandler)
        .get();
    return result;
  }
}
