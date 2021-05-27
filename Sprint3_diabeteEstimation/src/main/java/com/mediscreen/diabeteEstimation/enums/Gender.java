package com.mediscreen.diabeteEstimation.enums;

public enum Gender {
  F('F'),M('M');

private final Character gender;
  Gender(char gender) {
    this.gender = gender;
  }

  public Character getGender() {
    return gender;
  }
}
