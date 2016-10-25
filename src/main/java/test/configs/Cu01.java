package test.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class Cu01 {
  
  @Bean
  public IntegrationFlow simpleIntegrationFlow(FileReadingMessageSource fileReadingMessageSource, Consumer<SourcePollingChannelAdapterSpec> consumer, MessageHandler messageHandler) {
    final IntegrationFlow result = IntegrationFlows
        .from(fileReadingMessageSource, consumer)
        .handle(messageHandler)
        .get();
    return result;
  }
}
