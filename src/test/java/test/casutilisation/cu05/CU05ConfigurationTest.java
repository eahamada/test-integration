package test.casutilisation.cu05;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import test.Main;

import com.fasterxml.jackson.databind.ObjectMapper;


public class CU05ConfigurationTest {
  @Test
  public void testValueOf() throws Exception {
    ObjectMapper objectMapper = Main.objectMapper();
    CU05Configuration result = objectMapper.readValue(new File("src/main/resources/casutilisation/cu05/cu05_configuration.yaml"), CU05Configuration.class);
    System.out.println(result.requetes.identificationFluxATraiter);
    
  }
}
