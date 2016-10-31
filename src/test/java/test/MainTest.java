package test;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import fr.gouv.finances.ensu.documents.ws.report.GlobalReport;



public class MainTest {

  @Test
  public void testGlobalReport() throws Exception {
    final String xml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><documents><document iddoc=\"id_doc\" cddoc=\"cd_doc\" idempr=\"id_empr\"><motifsRejets><motifRejet reference=\"CODE_DOCUMENT_INCORRECT_201000\"><codeErreur>20</codeErreur><libelleErreur>Un ou plusieurs paramètres incorrects</libelleErreur><codeMotif>1000</codeMotif><libelleMotif>Code document incorrect</libelleMotif><commentaires></commentaires></motifRejet><motifRejet reference=\"IDENTIFIANT_DOCUMENT_INCORRECT_201001\"><codeErreur>20</codeErreur><libelleErreur>Un ou plusieurs paramètres incorrects</libelleErreur><codeMotif>1001</codeMotif><libelleMotif>Identifiant du document incorrect</libelleMotif><commentaires></commentaires></motifRejet><motifRejet reference=\"EMPREINTE_INCORRECTE_201002\"><codeErreur>20</codeErreur><libelleErreur>Un ou plusieurs paramètres incorrects</libelleErreur><codeMotif>1002</codeMotif><libelleMotif>Empreinte incorrecte</libelleMotif><commentaires></commentaires></motifRejet></motifsRejets></document><document iddoc=\"id_doc\" cddoc=\"cd_doc\" idempr=\"id_empr\"><motifsRejets><motifRejet reference=\"CODE_DOCUMENT_INCORRECT_201000\"><codeErreur>20</codeErreur><libelleErreur>Un ou plusieurs paramètres incorrects</libelleErreur><codeMotif>1000</codeMotif><libelleMotif>Code document incorrect</libelleMotif><commentaires></commentaires></motifRejet><motifRejet reference=\"IDENTIFIANT_DOCUMENT_INCORRECT_201001\"><codeErreur>20</codeErreur><libelleErreur>Un ou plusieurs paramètres incorrects</libelleErreur><codeMotif>1001</codeMotif><libelleMotif>Identifiant du document incorrect</libelleMotif><commentaires></commentaires></motifRejet><motifRejet reference=\"EMPREINTE_INCORRECTE_201002\"><codeErreur>20</codeErreur><libelleErreur>Un ou plusieurs paramètres incorrects</libelleErreur><codeMotif>1002</codeMotif><libelleMotif>Empreinte incorrecte</libelleMotif><commentaires></commentaires></motifRejet></motifsRejets></document></documents>";
    final XmlMapper result = new XmlMapper();
    result.registerModule(new JaxbAnnotationModule(){
      @Override
      public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.setMixInAnnotations(GlobalReport.class, GlobalReportMixin.class);
      }
    });
    GlobalReport r = result.readValue(xml, GlobalReport.class);
    System.out.println(r.getReports().size());
  }

}
