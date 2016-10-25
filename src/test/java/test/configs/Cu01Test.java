package test.configs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.MessageHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.Main;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class})
public class Cu01Test {
  Cu01 cu01;
  @Before
  public void setUp(){
    cu01 = new Cu01();
  }

  @Test
  public void testPriseEnChargeFluxReceptionnes() {
    
    MessageHandler loggingHandler = Mockito.mock(MessageHandler.class   );
    Consumer<SourcePollingChannelAdapterSpec> poller = (Consumer<SourcePollingChannelAdapterSpec>) Mockito.mock(Consumer.class);
    FileReadingMessageSource inboundAdapter = Mockito.mock(FileReadingMessageSource.class) ;
    cu01.priseEnChargeFluxReceptionnes(inboundAdapter, poller, loggingHandler);
  }
}
