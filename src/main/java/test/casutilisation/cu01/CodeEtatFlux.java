package test.casutilisation.cu01;


public enum CodeEtatFlux {
  EN_ATTENTE_CONTROLE_INTEGRITE(0),
  EN_ATTENTE_INTEGRATION(1),
  FLUX_EN_ATTENTE_TRAITEMENT(2),
  FLUX_REJETE_EN_ATTENTE_REFECTION(5),
  CALCUL_DISTRIBUTION_EFFECTUE(6),
  FLUX_AYANT_FAIT_L_OBJET_D_UNE_REFECTION(17),
  FLUX_REJETE(19),
  EN_ATTENTE_D_ASSOCIATION_DE_REFECTION(21),
  FLUX_DE_REFECTION_ASSOCIE_NON_RECEPTIONNE(23);
  public final int code;
  private CodeEtatFlux(int code)
  {
      this.code = code;

  }
  public static CodeEtatFlux valueOf(int code){
      switch (code)
      {
          case 0:
              return EN_ATTENTE_CONTROLE_INTEGRITE;
          case 1:
              return EN_ATTENTE_INTEGRATION;
          default:
              throw new IllegalArgumentException(code+" est un code flux invalide.");
      }
  }
}
