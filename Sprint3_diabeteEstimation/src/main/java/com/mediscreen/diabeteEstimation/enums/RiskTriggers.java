package com.mediscreen.diabeteEstimation.enums;

public enum RiskTriggers {
  HEMO("Hémoglobine A1C"),MICROALBUMINE("MicroAlbumine"),TAILLE("Taille"),
  POIDS("Poids"),FUMEUR("Fumeur"),ANORMAL("Anormal"),
  CHOLESTEROL("Cholesterol"),  VERTIGE("Vertige"),
  RECHUTE("Rechute"),REACTION("Réaction"),ANTICORPS("Anticorps");

  private String name;

  RiskTriggers(String name) {
    this.name=name;
  }

  public String getName(){
    return this.name;
  }
}
