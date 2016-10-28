package test.casutilisation.cu05;

public class CodeMotif {
  public final Integer value;

  private CodeMotif(Integer value) {
    this.value = value;
  }

  public static CodeMotif valueOf(Integer value) {
    return new CodeMotif(value);
  }
  
  public static CodeMotif valueOf(String value) {
    return new CodeMotif(Integer.parseInt(value));
  }
}
