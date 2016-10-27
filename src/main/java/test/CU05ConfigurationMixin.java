package test;

import test.casutilisation.cu05.CU05Configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder=CU05Configuration.Builder.class)
public abstract class CU05ConfigurationMixin {}
