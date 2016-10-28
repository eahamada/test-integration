package test.casutilisation.cu05;


public class CodeErreur {
  public final Integer value;

  private CodeErreur(Integer value) {
    this.value = value;
  }
  public static CodeErreur valueOf(Integer value) {
    return new CodeErreur(value);
  }
  public static CodeErreur valueOf(String value) {
    return new CodeErreur(Integer.parseInt(value));
  }
}
