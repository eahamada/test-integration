package test;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import fr.gouv.finances.ensu.documents.ws.report.DocumentReport;

public abstract class GlobalReportMixin {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "document")
  private  List<DocumentReport> reports;
}
