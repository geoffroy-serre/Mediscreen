package com.mediscreen.diabetesEstimation.service;


import com.mediscreen.diabetesEstimation.enums.EstimationNames;
import com.mediscreen.diabetesEstimation.enums.Gender;
import com.mediscreen.diabetesEstimation.enums.RiskTriggers;
import com.mediscreen.diabetesEstimation.model.DiabetesResult;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EstimationServiceImpl implements EstimationService {
  Logger logger = LoggerFactory.getLogger(EstimationServiceImpl.class);

  /**
   * @inheritDoc
   */
  @Override
  public DiabetesResult riskEstimation(Character gender, LocalDate birthdate,
                                       List<String> notes) {
    logger.debug("Entering riskEstimation");
    gender = Character.toUpperCase(gender);
    int age = ageCalculation(birthdate);
    int riskOccurrences = riskCountFromNotes(notes);
    return new DiabetesResult(estimationResult(riskOccurrences, age, gender));
  }

  /**
   * Return the number of different risk triggers found form given List.
   * See Api documentation to learn more risks triggers.
   *
   * @param notes List<String>
   * @return int
   */
  private int riskCountFromNotes(List<String> notes) {
    logger.debug("Entering riskCountFromNotes");
    EnumSet<RiskTriggers> riskTriggers = EnumSet.allOf(RiskTriggers.class);
    List<RiskTriggers> risksPresent = new ArrayList<>();
    for (RiskTriggers risk : riskTriggers) {
      for (String note : notes) {
        note = removeDiacriticalMarks(note);
        if (StringUtils.containsIgnoreCase(note, risk.getName()) && !risksPresent.contains(risk)) {
          risksPresent.add(risk);
        }
      }
    }
    logger.debug("Returning result: " + risksPresent.size());
    return risksPresent.size();
  }


  /**
   * Return the estimation of diabetes risks for given parameters
   * See Api documentation to learn more about the calculation.
   *
   * @param risks  int
   * @param age    int
   * @param gender char
   * @return String
   */
  private String estimationResult(int risks, int age, Character gender) {
    logger.debug("Entering estimationResult with {},{},{}", risks, age, gender);
    gender = Character.toUpperCase(gender);
    String result = EstimationNames.NONE.getEstimationName();
    if ((gender == Gender.F.getGender() && age < 30 && risks >= 7)
            || (gender == Gender.M.getGender() && age < 30 && risks >= 5)
            || (age >= 30 && risks >= 8)) {
      logger.debug("Returning result: " + EstimationNames.EARLY_ONSET.getEstimationName());
      result = EstimationNames.EARLY_ONSET.getEstimationName();
    } else if ((gender == Gender.M.getGender() && age < 30 && risks >= 3)
            || (gender == Gender.F.getGender() && age < 30 && risks >= 4)
            || (age >= 30 && risks >= 6)) {
      logger.debug("Returning result: " + EstimationNames.IN_DANGER.getEstimationName());
      result = EstimationNames.IN_DANGER.getEstimationName();
    } else if ((age >= 30 && risks >= 2)) {
      logger.debug("Returning result: " + EstimationNames.BORDERLINE.getEstimationName());
      result = EstimationNames.BORDERLINE.getEstimationName();
    }
    logger.debug("Returning result: " + EstimationNames.NONE.getEstimationName());
    return result;
  }


  /**
   * Calculate duration between current date and given date.
   *
   * @param birthdate LocalDate
   * @return int age
   */
  private int ageCalculation(LocalDate birthdate) {
    logger.debug("Entering ageCalculation for : " + birthdate);
    return Period.between(birthdate, LocalDate.now()).getYears();
  }

  /**
   * Remove all accented letter(s), and replace them with non accented letter(s).
   *
   * @param stringToClean String
   * @return String
   */
  private String removeDiacriticalMarks(String stringToClean) {
    logger.debug("Entering removeDiacriticalMarks()");
    return Normalizer.normalize(stringToClean, Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }

}
