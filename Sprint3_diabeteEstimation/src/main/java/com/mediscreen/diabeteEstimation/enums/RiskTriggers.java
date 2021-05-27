package com.mediscreen.diabeteEstimation.enums;

public enum RiskTriggers {
  HEMO("Hemoglobin A1C"),MICROALBUMINE("MicroAlbumin"),TAILLE("Height"),
  POIDS("Weight"),FUMEUR("Smoker"),ANORMAL("Abnormal"),
  CHOLESTEROL("Cholesterol"),  VERTIGE("Dizziness"),
  RECHUTE("Relapse"),REACTION("reaction"),ANTICORPS("Antibodies");

  private final String name;

  RiskTriggers(String name) {
    this.name=name;
  }

  public String getName(){
    return this.name;
  }
}
