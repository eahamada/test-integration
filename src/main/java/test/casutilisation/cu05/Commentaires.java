package test.casutilisation.cu05;

public class Commentaires {
  public final String value;

  private Commentaires(String value) {
    this.value = value;
  }

  public Commentaires valueOf(String value) {
    return new Commentaires(value);
  }
}
