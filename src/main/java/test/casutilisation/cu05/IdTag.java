package test.casutilisation.cu05;

public class IdTag {
  public final String value;
  private IdTag(final String value){
    this.value = value;
  }
  public static IdTag valueOf(String value){
    return new IdTag(value);
  }
}
