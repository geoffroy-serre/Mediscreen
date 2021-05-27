package com.mediscreen.diabeteEstimation.enums;

public enum EstimationNames {
  NONE("None"), BORDERLINE("Borderline"),IN_DANGER("In danger"), EARLY_ONSET("Early onset")
  ;

  private final String estimationName;

  EstimationNames(String estimationName) {
    this.estimationName = estimationName;
  }

  public String getEstimationName() {
    return estimationName;
  }
}
