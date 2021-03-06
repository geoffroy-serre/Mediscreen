package com.mediscreen.diabetesEstimation.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, Character> {

  public void initialize(Gender sex) {
    // used only if your annotation has attributes
  }

  public boolean isValid(Character sex, ConstraintValidatorContext constraintContext) {
    // Bean Validation specification recommends to consider null values as
    // being valid. If null is not a valid value for an element, it should
    // be annotated with @NotNull explicitly.
    if (sex == null) {
      return true;
    }
    return sex.equals('F') || sex.equals('M') || sex.equals('m') || sex.equals('f');

  }
}

