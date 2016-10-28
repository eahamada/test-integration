package test.casutilisation.cu05;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

@JsonDeserialize(builder=DS05Reponse.Builder.class)
public class DS05Reponse {
  public final List<Document> documents;
  public final Document document;
  private DS05Reponse(Builder builder) {
      this.documents = builder.documents;
    
    this.document = builder.document;
  }
  public static class Builder{

    private List<Document> documents;
    private Document document;

    public Builder withDocuments(List<Document> documents) {
      this.documents = documents;
      return this;
    }

    public Builder withDocument(Document document) {
      this.document = document;
      return this;
    }

    public DS05Reponse build() {
      validate();
      return new DS05Reponse(this);
    }

    private void validate() {
    }
  }
  
}
