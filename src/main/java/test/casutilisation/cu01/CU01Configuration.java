package test.casutilisation.cu01;

import com.google.common.base.Preconditions;

public class CU01Configuration {
  public final String dossierReception;

  private CU01Configuration(Builder builder) {
    this.dossierReception = builder.dossierReception;
  }

  public static class Builder{

    private String dossierReception;

    public Builder withDossierReception(String dossierReception) {
      this.dossierReception = dossierReception;
      return this;
    }

    public CU01Configuration build() {
      validate();
      return new CU01Configuration(this);
    }

    private void validate() {
//      Preconditions.checkArgument(!StringUtils.isBlank(dossierReception), "dossierReception may not be blank");
    }
  }
}
