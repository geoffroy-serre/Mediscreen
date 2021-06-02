package com.mediscreen.diabetesEstimation.service;

import com.mediscreen.diabetesEstimation.model.DiabetesResult;
import java.time.LocalDate;
import java.util.List;

public interface EstimationService {

  /**
   * Return the result of diabetes estimation based on risks factors.
   * gender is auto converted to upperCase to avoir false result.
   * See Api documentation to learn more about the calculation.
   *
   * @param gender    Character
   * @param birthdate LocalDate
   * @param notes     List<String>
   * @return String
   */
  DiabetesResult riskEstimation(Character gender, LocalDate birthdate, List<String> notes);


}
