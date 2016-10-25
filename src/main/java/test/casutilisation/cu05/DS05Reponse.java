package test.casutilisation.cu05;

import com.google.common.base.Preconditions;

public class DS05Reponse {
  public final IdTag idTag;
  public final UrlDoc urlDoc;
  
  private DS05Reponse(Builder builder) {
    this.idTag = builder.idTag;
    this.urlDoc = builder.urlDoc;
  }
  public static class Builder{

    private IdTag idTag;
    private UrlDoc urlDoc;
    public Builder withIdTag(IdTag idTag) {
      this.idTag = idTag;
      return this;
    }
    public Builder withUrlDoc(UrlDoc urlDoc) {
      this.urlDoc = urlDoc;
      return this;
    }
    public DS05Reponse build() {
      validate();
      return new DS05Reponse(this);
    }
    private void validate() {
      Preconditions.checkNotNull(idTag, "idTag may not be null");
      Preconditions.checkNotNull(urlDoc, "urlDoc may not be null");
    }
  }
}
