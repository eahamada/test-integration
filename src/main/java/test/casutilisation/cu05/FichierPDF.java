package test.casutilisation.cu05;


public class FichierPDF {
  public final byte[] value;

  private FichierPDF(final byte[] value) {
    this.value = value;
  }

  public static FichierPDF valueOf(final byte[] value){
    return new FichierPDF(value);
  }
  
}
