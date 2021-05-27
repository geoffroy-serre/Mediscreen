package com.mediscreen.diabeteEstimation.service;

import com.mediscreen.diabeteEstimation.model.EstimationResult;
import java.time.LocalDate;
import java.util.List;

public interface EstimationService {

  /**
   * Return the result of diabetes estimation based on risks factors.
   * gender is auto converted to upperCase to avoir false result.
   * See Api documentation to learn more about the calculation.
   * @param gender Character
   * @param birthdate LocalDate
   * @param notes List<String>
   * @return String
   */
  EstimationResult riskEstimation(Character gender, LocalDate birthdate, List<String> notes);

  /**
   * Return the number of different risk triggers found form given List.
   * See Api documentation to learn more risks triggers.
   * @param notes List<String>
   * @return int
   */
  int riskCountFromNotes(List<String> notes);

  /**
   * Return the estimation of diabetes risks for given parameters
   * See Api documentation to learn more about the calculation.
   * @param risks int
   * @param age int
   * @param gender char
   * @return String
   */
  String estimationResult(int risks, int age, Character gender);
}
