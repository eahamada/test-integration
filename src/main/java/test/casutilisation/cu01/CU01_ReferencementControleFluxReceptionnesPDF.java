package test.casutilisation.cu01;


import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.integration.jdbc.MessagePreparedStatementSetter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:cu01_configuration.properties", ignoreResourceNotFound = true)
public class CU01_ReferencementControleFluxReceptionnesPDF {
  
  private final static Logger LOGGER =
      LoggerFactory.getLogger(CU01_ReferencementControleFluxReceptionnesPDF.class);

  @Bean(name = "cu01_prise_en_charge_flux_receptionnes")
  public IntegrationFlow priseEnChargeFluxReceptionnes(
      @Qualifier("priseEnChargeFluxReceptionnes") final FileReadingMessageSource inboundAdapter,
      @Qualifier("cu01Poller") final Consumer<SourcePollingChannelAdapterSpec> poller,
      @Qualifier("creerInstanceFlux") final MessageHandler messageHandler) {
    return integrationFlow(inboundAdapter, poller, messageHandler);
  }
  
  @Bean(name="priseEnChargeFluxReceptionnes")
  public FileReadingMessageSource receptionPdf(@Value("${dossierReception:./cu01/priseEnChargeFluxReceptionnes}") File directory, @Qualifier("cu01_reception_order_comparator") Comparator<File> receptionOrderComparator){
     FileReadingMessageSource result = Files.inboundAdapter(directory, receptionOrderComparator).autoCreateDirectory(true).ignoreHidden().nioLocker().get();
    return result;
  }
  @Bean(name="cu01Poller")
  public Consumer<SourcePollingChannelAdapterSpec> poller(){
    final Consumer<SourcePollingChannelAdapterSpec> result = new Consumer<SourcePollingChannelAdapterSpec>() {

      public void accept(final SourcePollingChannelAdapterSpec t) {
            t.poller(Pollers.fixedRate(5, TimeUnit.SECONDS));
      }};
    return result;
      
  }
  
  
  @Bean(name="creerInstanceFlux")
  public MessageHandler creerInstanceFlux(DataSource dataSource) {
    JdbcMessageHandler result = new JdbcMessageHandler(dataSource,
        "");
    result.setPreparedStatementSetter(new MessagePreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, Message<?> requestMessage) throws SQLException {
        final Timestamp now = new Timestamp(DateTime.now().getMillis());
        ps.setString(1, String.valueOf(requestMessage.getHeaders().get("type")));
        ps.setInt(2, 0);
        ps.setTimestamp(3, now);
        ps.setTimestamp(4, now);
      }
    });
    return result;
  }
  
  
  
  @Bean(name="cu01_reception_order_comparator")
  public Comparator<File> receptionOrderComparator(){
    final Comparator<File> result = new Comparator<File>() {
      public int compare(File f1, File f2) {
        return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
      }
    };
    return result;
  }
  
//  @Bean
//  public IntegrationFlow controleFluxReceptionnes(){
//    return integrationFlow(fluxPdfCodeEtat0, poller, priseEnChargePdf);
//  }
//  
//  @Bean
//  public MessageSource<Object> jdbcMessageSource(DataSource datasource, RequetesCU01 requetes) {
//    final JdbcPollingChannelAdapter jdbcPollingChannelAdapter = new JdbcPollingChannelAdapter(datasource, requetes.fluxCodeEtat0);
//    return jdbcPollingChannelAdapter;
//  }
//  @Bean
//  public MessageSource<Object> jdbcMessageSource() {
//    final JdbcPollingChannelAdapter jdbcPollingChannelAdapter = new JdbcPollingChannelAdapter(datasource, requetes.fluxCodeEtat0);
//    return jdbcPollingChannelAdapter;
//  }

  private  IntegrationFlow integrationFlow(final FileReadingMessageSource  messageSource,final Consumer<SourcePollingChannelAdapterSpec> poller, final  MessageHandler messageHandler) {
    final IntegrationFlow result = IntegrationFlows
        .from(messageSource, poller)
        .handle(messageHandler)
        .get();
    return result;
  }
}
