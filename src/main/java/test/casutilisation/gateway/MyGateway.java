package test.casutilisation.gateway;

import java.util.List;

import org.springframework.integration.annotation.MessagingGateway;

import fr.gouv.finances.ensu.documents.bean.Document;
import fr.gouv.finances.ensu.documents.ws.report.GlobalReport;

@MessagingGateway(defaultRequestChannel = "ds05Channel")
public interface MyGateway {

     GlobalReport sendDSO5(List<Document> doc);

}