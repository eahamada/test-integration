package test.casutilisation.cu05;

public class CodeDocument {
  public final String value;
  private CodeDocument(final String value){
    this.value = value;
  }
  public static CodeDocument valueOf(final String value){
    return new CodeDocument(value);
  }
}
