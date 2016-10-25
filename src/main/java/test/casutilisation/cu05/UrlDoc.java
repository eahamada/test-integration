package test.casutilisation.cu05;

public class UrlDoc {
  public final String value;
  private UrlDoc(final String value) {
    this.value = value;
  }
  public static UrlDoc valueOf(final String value){
    return new UrlDoc(value);
  }
}
