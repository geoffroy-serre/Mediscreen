package com.mediscreen.diabeteEstimation.service;


import com.mediscreen.diabeteEstimation.enums.RiskTriggers;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class EstimationServiceImpl implements EstimationService {
  /**
   * Parcourir la liste de note.
   * Pour chaque note verifier la présence du déclencheur.
   * Meme si il est présent plusieurs fois le déclencheur n'est compté qu'une fois.
   * Selon l'âge et le sex et le nombre de déclencheur on renvoi l'estimation.
   */


  /**
   * @inheritDoc
   */
  @Override
  public String riskEstimation(Character gender, LocalDate birthdate, List<String> notes) {
    int age = ageCalculation(birthdate);
    int riskOccurrences = riskCountFromNotes(notes);
    return estimationResult(riskOccurrences, age, gender);
  }

  /**
   * @inheritDoc
   */
  @Override
  public int riskCountFromNotes(List<String> notes) {
    EnumSet<RiskTriggers> riskTriggers = EnumSet.allOf(RiskTriggers.class);
    Map<String, Integer> count = new HashMap<>();
    for (RiskTriggers risk : riskTriggers) {
      for (String string : notes) {
        string = removeDiacriticalMarks(string);
        if (StringUtils.containsIgnoreCase(string, risk.toString())) {
          if (count.containsKey(risk.toString())) {
            int truc = count.get(risk.toString());
            count.put(risk.toString(), truc + 1);
          } else {
            count.put(risk.toString(), 1);
          }
        }
      }
    }
    System.out.println(count);
    return count.size();
  }

  /**
   * @inheritDoc
   */
  @Override
  public String estimationResult(int risks, int age, char gender) {
    String result = "";
    switch (risks) {
      case 0:
      case 1:
        result = "None";
        break;
      case 2:
        if (age > 30) {
          result = "Borderline";
        }
        if (age < 30) {
          result = "None";
        }
        break;
      case 3:
        if (age < 30 && gender == 'm' || age < 30 && gender == 'M') {
          result = "In Danger";
        }
        if (age < 30 && gender == 'f' || age < 30 && gender == 'F') {
          result = "None";
        }
        break;
      case 4:
        if (age < 30 && gender == 'f' || age < 30 && gender == 'F') {
          result = "In Danger";
        }
        break;
      case 5:
        if (age < 30 && gender == 'm' || age < 30 && gender == 'M') {
          result = "Early Onset";
        }
        if (age >30) {
          result = "BorderLine";
        }
        break;
      case 6:
        if (age > 30) {
          result = "In Danger";
        }
        if (age < 30 && gender == 'm' || age < 30 && gender == 'M') {
          result = "Early Onset";
        }
        break;
      case 7:
        if (age < 30 && gender == 'f' || age < 30 && gender == 'F') {
          result = "Early Onset";
        }
        break;
      case 8:
        if (age > 30) {
          result = "Early Onset";
        }
        break;
      default:
        if (risks > 8 && age > 30) {
          result = "Early Onset";
        } else {
          result = "Can't determine";
        }
        break;
    }
    return result;
  }


  /**
   * Calculate duration between current date and given date.
   *
   * @param birthdate LocalDate
   * @return int age
   */
  private int ageCalculation(LocalDate birthdate) {
    return Period.between(birthdate, LocalDate.now()).getYears();
  }

  public static String removeDiacriticalMarks(String string) {
    return Normalizer.normalize(string, Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }

}
