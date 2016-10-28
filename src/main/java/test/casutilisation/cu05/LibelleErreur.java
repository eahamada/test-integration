package test.casutilisation.cu05;

public class LibelleErreur {
public final String value;

private LibelleErreur(String value) {
  this.value = value;
}

public static LibelleErreur valueOf(String value) {
  return new LibelleErreur(value);
}
}
