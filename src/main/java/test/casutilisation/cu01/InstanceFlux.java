package test.casutilisation.cu01;

import org.joda.time.DateTime;

import com.google.common.base.Preconditions;

public class InstanceFlux {
  public final Long id;
  public final TypeFlux typeFlux;
  public final CodeEtatFlux codeEtatFlux;
  public final DateTime datePriseEnCharge;
  public final DateTime dateDerniereModification;

  private InstanceFlux(Builder builder) {
    this.typeFlux = builder.typeFlux;
    this.codeEtatFlux = builder.codeEtatFlux;
    this.datePriseEnCharge = builder.datePriseEnCharge;
    this.dateDerniereModification = builder.dateDerniereModification;
    this.id = builder.id;
  }

  public static class Builder {

    private TypeFlux typeFlux;
    private CodeEtatFlux codeEtatFlux;
    private DateTime datePriseEnCharge;
    private DateTime dateDerniereModification;
    private Long id;

    public Builder withTypeFlux(TypeFlux typeFlux) {
      this.typeFlux = typeFlux;
      return this;
    }

    public Builder withCodeEtatFlux(CodeEtatFlux codeEtatFlux) {
      this.codeEtatFlux = codeEtatFlux;
      return this;
    }

    public Builder withDatePriseEnCharge(DateTime datePriseEnCharge) {
      this.datePriseEnCharge = datePriseEnCharge;
      return this;
    }

    public Builder withDateDerniereModification(DateTime dateDerniereModification) {
      this.dateDerniereModification = dateDerniereModification;
      return this;
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public InstanceFlux build() {
      validate();
      return new InstanceFlux(this);
    }

    private void validate() {
      Preconditions.checkNotNull(typeFlux, "typeFlux may not be null");
      Preconditions.checkNotNull(codeEtatFlux, "codeEtatFlux may not be null");
      Preconditions.checkNotNull(datePriseEnCharge, "datePriseEnCharge may not be null");
      Preconditions.checkNotNull(dateDerniereModification,
          "dateDerniereModification may not be null");
    }
  }
}
