package test.casutilisation.cu05;

public class IdEmpr {
  public final String value;
  public IdEmpr(String value) {
    this.value = value;
  }
  public static IdEmpr valueOf(String value){
    return new IdEmpr(value);
  }
}
