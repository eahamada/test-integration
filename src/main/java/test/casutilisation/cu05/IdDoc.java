package test.casutilisation.cu05;


public class IdDoc {
  public final String value;

  private IdDoc(String value) {
    this.value = value;
  }

  public static IdDoc valueOf(String value) {
    return new IdDoc(value);
  }
}
