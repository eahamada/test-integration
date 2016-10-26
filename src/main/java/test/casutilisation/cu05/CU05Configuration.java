package test.casutilisation.cu05;

import javax.sql.DataSource;

import org.springframework.http.client.ClientHttpRequestFactory;

public class CU05Configuration {
  public DataSource dataSource;
  public int maxDocuments;
  public RequetesCU05 requetes;
  public String ensuDocumentsUrl;
  public ClientHttpRequestFactory clientHttpRequestFactory;
  
  public static Builder builder(){
    return new Builder();
  }
  
  public Builder toBuilder(){
    return new Builder(this);
  }
  
  private CU05Configuration(Builder builder) {
    this.dataSource = builder.dataSource;
    this.maxDocuments = builder.maxDocuments;
    this.requetes = builder.requetes;
    this.ensuDocumentsUrl = builder.ensuDocumentsUrl;
    this.clientHttpRequestFactory = builder.clientHttpRequestFactory;
  }
  public static class Builder{

    private DataSource dataSource;
    private int maxDocuments;
    private RequetesCU05 requetes;
    private String ensuDocumentsUrl;
    private ClientHttpRequestFactory clientHttpRequestFactory;
    public Builder() {
      
    }
    public Builder(CU05Configuration cu05Configuration) {
      this.dataSource = cu05Configuration.dataSource;
      this.maxDocuments = cu05Configuration.maxDocuments;
      this.requetes = cu05Configuration.requetes;
      this.ensuDocumentsUrl = cu05Configuration.ensuDocumentsUrl;
      this.clientHttpRequestFactory = cu05Configuration.clientHttpRequestFactory;
      
    }
    public Builder withDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      return this;
    }
    public Builder withMaxDocuments(int maxDocuments) {
      this.maxDocuments = maxDocuments;
      return this;
    }
    public Builder withRequetes(RequetesCU05 requetes) {
      this.requetes = requetes;
      return this;
    }
    public Builder withEnsuDocumentsUrl(String ensuDocumentsUrl) {
      this.ensuDocumentsUrl = ensuDocumentsUrl;
      return this;
    }
    public Builder withClientHttpRequestFactory(ClientHttpRequestFactory clientHttpRequestFactory) {
      this.clientHttpRequestFactory = clientHttpRequestFactory;
      return this;
    }
    public CU05Configuration build() {
      validate();
      return new CU05Configuration(this);
    }
    private void validate() {
    }
  }

}
