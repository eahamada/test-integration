package test.casutilisation.cu05;

import com.google.common.base.Preconditions;


public class RequetesCU05 {
  public String identificationFluxATraiter ;
  public String majCodeEtatFlux;
  public String majCodeEtatDocument;
  private RequetesCU05(Builder builder) {
    this.identificationFluxATraiter = builder.identificationFluxATraiter;
    this.majCodeEtatFlux = builder.majCodeEtatFlux;
    this.majCodeEtatDocument = builder.majCodeEtatDocument;
  }
  public static class Builder{

    private String identificationFluxATraiter;
    private String majCodeEtatFlux;
    private String majCodeEtatDocument;
    public Builder withIdentificationFluxATraiter(String identificationFluxATraiter) {
      this.identificationFluxATraiter = identificationFluxATraiter;
      return this;
    }
    public Builder withMajCodeEtatFlux(String majCodeEtatFlux) {
      this.majCodeEtatFlux = majCodeEtatFlux;
      return this;
    }
    public Builder withMajCodeEtatDocument(String majCodeEtatDocument) {
      this.majCodeEtatDocument = majCodeEtatDocument;
      return this;
    }
    public RequetesCU05 build() {
      validate();
      return new RequetesCU05(this);
    }
    private void validate() {
    }
  }
}
