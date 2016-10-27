package test;

import test.casutilisation.cu05.RequetesCU05;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder=RequetesCU05.Builder.class)
public abstract class RequetesCU05Mixin {}