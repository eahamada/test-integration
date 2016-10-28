package test.casutilisation.cu05;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(builder=MotifRejet.Builder.class)
public class MotifRejet {
  public final Reference reference;
  public final CodeErreur codeErreur;
  public final LibelleErreur libelleErreur;
  public final CodeMotif codeMotif;
  public final LibelleMotif libelleMotif;
  public final Commentaires commentaires;
  private MotifRejet(Builder builder) {
    this.reference = builder.reference;
    this.codeErreur = builder.codeErreur;
    this.libelleErreur = builder.libelleErreur;
    this.codeMotif = builder.codeMotif;
    this.libelleMotif = builder.libelleMotif;
    this.commentaires = builder.commentaires;
  }
  public static class Builder{

    private Reference reference;
    private CodeErreur codeErreur;
    private LibelleErreur libelleErreur;
    private CodeMotif codeMotif;
    private LibelleMotif libelleMotif;
    private Commentaires commentaires;
    public Builder withReference(Reference reference) {
      this.reference = reference;
      return this;
    }
    public Builder withCodeErreur(CodeErreur codeErreur) {
      this.codeErreur = codeErreur;
      return this;
    }
    public Builder withLibelleErreur(LibelleErreur libelleErreur) {
      this.libelleErreur = libelleErreur;
      return this;
    }
    public Builder withCodeMotif(CodeMotif codeMotif) {
      this.codeMotif = codeMotif;
      return this;
    }
    public Builder withLibelleMotif(LibelleMotif libelleMotif) {
      this.libelleMotif = libelleMotif;
      return this;
    }
    public Builder withCommentaires(Commentaires commentaires) {
      this.commentaires = commentaires;
      return this;
    }
    public MotifRejet build() {
      validate();
      return new MotifRejet(this);
    }
    private void validate() {
    }
  }
  
  
}
