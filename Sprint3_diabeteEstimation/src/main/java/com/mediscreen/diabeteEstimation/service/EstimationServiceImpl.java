package com.mediscreen.diabeteEstimation.service;


import com.mediscreen.diabeteEstimation.enums.EstimationNames;
import com.mediscreen.diabeteEstimation.enums.Gender;
import com.mediscreen.diabeteEstimation.enums.RiskTriggers;
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
  public String riskEstimation(Character gender, LocalDate birthdate, List<String> notes) {
    logger.debug("Entering riskEstimation");
    gender = Character.toUpperCase(gender);
    int age = ageCalculation(birthdate);
    int riskOccurrences = riskCountFromNotes(notes);
    return estimationResult(riskOccurrences, age, gender);
  }

  /**
   * @inheritDoc
   */
  @Override
  public int riskCountFromNotes(List<String> notes) {
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
   * @inheritDoc
   */
  @Override
  public String estimationResult(int risks, int age, char gender) {
    logger.debug("Entering estimationResult with {},{},{}",risks,age,gender);
    gender = Character.toUpperCase(gender);
    if ((gender == Gender.F.getGender() && age < 30 && risks >= 7)
            || (gender == Gender.M.getGender() && age < 30 && risks >= 5)
            || (age >= 30 && risks >= 8)) {
      logger.debug("Returning result: "+ EstimationNames.EARLY_ONSET.getEstimationName());
      return EstimationNames.EARLY_ONSET.getEstimationName();
    } else if ((gender == Gender.M.getGender() && age < 30 && risks >= 3)
            || (gender == Gender.F.getGender() && age < 30 && risks >= 4)
            || (age >= 30 && risks >= 6)) {
      logger.debug("Returning result: "+ EstimationNames.IN_DANGER.getEstimationName());
      return EstimationNames.IN_DANGER.getEstimationName();
    } else if ((age >= 30 && risks >= 2)) {
      logger.debug("Returning result: "+ EstimationNames.BORDERLINE.getEstimationName());
      return EstimationNames.BORDERLINE.getEstimationName();
    }
    logger.debug("Returning result: "+ EstimationNames.NONE.getEstimationName());
    return EstimationNames.NONE.getEstimationName();
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
   * Remove all accented letter(s), and remplace them with non accented letter(s).
   * @param stringToClean String
   * @return String
   */
  private String removeDiacriticalMarks(String stringToClean) {
    logger.debug("Entering removeDiacriticalMarks()");
    return Normalizer.normalize(stringToClean, Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }

}
