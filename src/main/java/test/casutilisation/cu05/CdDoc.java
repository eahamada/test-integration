package test.casutilisation.cu05;

public class CdDoc {
  public final String value;
  public CdDoc(String value) {
    this.value = value;
  }
  public static CdDoc valueOf(String value){
    return new CdDoc(value);
  }
}
