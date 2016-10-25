package test.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.FileListFilter;
import org.springframework.integration.file.filters.LastModifiedFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
public class Cu01Beans {
  private final static String DEFAULT_LOGGING_LEVEL = LoggingHandler.Level.INFO.name();
  
  public FileToStringTransformer fileToStringTransformer() {
    FileToStringTransformer fileToStringTransformer = new FileToStringTransformer();
    fileToStringTransformer.setCharset("UTF-8");
    fileToStringTransformer.setDeleteFiles(true);
    return fileToStringTransformer;
  } 

  @Bean
  public Consumer<SourcePollingChannelAdapterSpec> poller(@Value("${delay:1000}") final Long delay) {
    final Consumer<SourcePollingChannelAdapterSpec> result =
        new Consumer<SourcePollingChannelAdapterSpec>() {
          @Override
          public void accept(final SourcePollingChannelAdapterSpec t) {
            t.poller(Pollers.fixedRate(delay).get());
          }
        };
    return result;
  }
  
  @Bean
  public FileReadingMessageSource inboundAdapter(@Value("${directory:./in}") final File directory) {
    final FileReadingMessageSource result =
        Files.inboundAdapter(directory, orderByLastModified()).autoCreateDirectory(true).preventDuplicates().get();
    result.setFilter(filter());
    return result;
  }

  private Comparator<File> orderByLastModified() {
    final Comparator<File> result = new Comparator<File>() {

      @Override
      public int compare(File o1, File o2) {
        return (int) (o2.lastModified() - o1.lastModified());
      }};
    return result;
  }

  private FileListFilter<File> filter() {
    Collection<FileListFilter<File>> fileFilters = new ArrayList<FileListFilter<File>>();
    final LastModifiedFileListFilter lastModifiedFileListFilter = new LastModifiedFileListFilter();
    lastModifiedFileListFilter.setAge(2, TimeUnit.MINUTES);
    fileFilters.add(lastModifiedFileListFilter);
    FileListFilter<File> result = new CompositeFileListFilter<File>(fileFilters);
    return result;
  }

  private MessageHandler loggingHandler() {
    new MessageHandler() {
      
      @Override
      public void handleMessage(Message<?> arg0) throws MessagingException {
        if(arg0 instanceof File) {
          File flux = (File) arg0;
          
//          File.createTempFile();
        }else{
          throw new IllegalArgumentException();
        }
      }
    };
    final LoggingHandler result = new LoggingHandler(DEFAULT_LOGGING_LEVEL);
    return result;
  }
}
