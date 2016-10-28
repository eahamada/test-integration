package test.casutilisation.cu05;

public class Reference {
 public final String value;

private Reference(String value) {
  this.value = value;
}

public static Reference valueOf(String value) {
  return new Reference(value);
}
}
