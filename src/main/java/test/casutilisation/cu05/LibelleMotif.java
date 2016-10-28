package test.casutilisation.cu05;

public class LibelleMotif {
  public final String value;

  private LibelleMotif(String value) {
    this.value = value;
  }

  public LibelleMotif valueOf(String value) {
    return new LibelleMotif(value);
  }
}
