package test.casutilisation.cu05;

public class EmpreinteDocument {
  public final String value;
  private EmpreinteDocument(final String value){
    this.value = value;
  }
  public static EmpreinteDocument valueOf(final String value){
    return new EmpreinteDocument(value);
  }
}
