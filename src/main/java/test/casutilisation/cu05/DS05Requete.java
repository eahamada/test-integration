package test.casutilisation.cu05;

import com.google.common.base.Preconditions;


public class DS05Requete {
  public final FichierPDF fichier;
  public final IdTag idTag;
  public final CodeDocument cdDoc;
  public final EmpreinteDocument idEmpr;
  
  
  private DS05Requete(Builder builder) {
    this.fichier = builder.fichier;
    this.idTag = builder.idTag;
    this.cdDoc = builder.cdDoc;
    this.idEmpr = builder.idEmpr;
  }
  public static Builder builder(){
    return new Builder();
  }
  public static class Builder{

    private FichierPDF fichier;
    private IdTag idTag;
    private CodeDocument cdDoc;
    private EmpreinteDocument idEmpr;
    public Builder() {

    }
    
    public Builder withFichier(FichierPDF fichier) {
      this.fichier = fichier;
      return this;
    }
    public Builder withIdTag(IdTag idTag) {
      this.idTag = idTag;
      return this;
    }
    public Builder withCdDoc(CodeDocument cdDoc) {
      this.cdDoc = cdDoc;
      return this;
    }
    public Builder withIdEmpr(EmpreinteDocument idEmpr) {
      this.idEmpr = idEmpr;
      return this;
    }
    public DS05Requete build() {
      validate();
      return new DS05Requete(this);
    }
    private void validate() {
      Preconditions.checkNotNull(fichier, "fichier may not be null");
      Preconditions.checkNotNull(idTag, "idTag may not be null");
      Preconditions.checkNotNull(cdDoc, "cdDoc may not be null");
    }
  }
  
}
