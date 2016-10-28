package test.casutilisation.cu05;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Document.Builder.class)
public class Document {
  public final IdDoc iddoc;
  public final CdDoc cddoc;
  public final IdEmpr idempr;
  public final List<MotifRejet> motifsRejets;

  private Document(Builder builder) {
    this.iddoc = builder.iddoc;
    this.cddoc = builder.cddoc;
    this.idempr = builder.idempr;
    this.motifsRejets = builder.motifsRejets;
  }

  public static class Builder {

    private IdDoc iddoc;
    private CdDoc cddoc;
    private IdEmpr idempr;
    private List<MotifRejet> motifsRejets;

    public Builder withIddoc(IdDoc iddoc) {
      this.iddoc = iddoc;
      return this;
    }

    public Builder withCddoc(CdDoc cddoc) {
      this.cddoc = cddoc;
      return this;
    }

    public Builder withIdempr(IdEmpr idempr) {
      this.idempr = idempr;
      return this;
    }

    public Builder withMotifsRejets(List<MotifRejet> motifsRejets) {
      this.motifsRejets = motifsRejets;
      return this;
    }

    public Document build() {
      validate();
      return new Document(this);
    }

    private void validate() {}
  }


}
