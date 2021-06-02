package com.mediscreen.diabetesEstimation.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class EstimationServiceImplTest {

  EstimationService estimationService = new EstimationServiceImpl();

  List<String> notes = new ArrayList<>();

  @Test
  void riskEstimation() {
    notes.add("Smoker");
    notes.add("Smoker");
    notes.add("dizziness");
    notes.add("not exists");
    LocalDate birthdate = LocalDate.of(1982, 4, 14);
    assertEquals(estimationService.riskEstimation('m', birthdate, notes).getResult(), "Borderline");
  }

  @Test
  void riskCountFromNotes() throws NoSuchMethodException, InvocationTargetException,
          IllegalAccessException {
    Method method = EstimationServiceImpl.class.getDeclaredMethod("riskCountFromNotes",
            List.class);
    method.setAccessible(true);
    notes.add("Smoker");
    notes.add("Smoker");
    notes.add("dizziness");
    notes.add("not exists");
    assertEquals(method.invoke(estimationService, notes), 2);
    assertNotEquals(method.invoke(estimationService, notes), 4);
    assertNotEquals(method.invoke(estimationService, notes), 3);
    notes.clear();
  }

  /*

  Tests  for patient over 30 years old
  It also test corrects results if age is equal to 30 because its processed as over 30.

   */

  @Test
  void estimationResultNoneOver30() throws NoSuchMethodException, InvocationTargetException,
          IllegalAccessException {
    Method method = EstimationServiceImpl.class.getDeclaredMethod("estimationResult",
            int.class, int.class, Character.class);
    method.setAccessible(true);
    assertEquals(method.invoke(estimationService, 0, 32, 'm'), "None");
    assertEquals(method.invoke(estimationService, 0, 32, 'f'), "None");
    assertEquals(method.invoke(estimationService, 1, 32, 'm'), "None");
    assertEquals(method.invoke(estimationService, 1, 32, 'f'), "None");
    /*
    When age is 30, it is considered as over 30
    */
    assertEquals(method.invoke(estimationService, 0, 30, 'm'), "None");
    assertEquals(method.invoke(estimationService, 0, 30, 'f'), "None");
    assertEquals(method.invoke(estimationService, 1, 30, 'm'), "None");
    assertEquals(method.invoke(estimationService, 1, 30, 'f'), "None");
  }


  @Test
  void estimationResultBorderLineOver30() throws NoSuchMethodException, InvocationTargetException
          , IllegalAccessException {
    Method method = EstimationServiceImpl.class.getDeclaredMethod("estimationResult",
            int.class, int.class, Character.class);
    method.setAccessible(true);
    assertEquals(method.invoke(estimationService, 2, 32, 'm'), "Borderline");
    assertEquals(method.invoke(estimationService, 2, 32, 'f'), "Borderline");
    assertEquals(method.invoke(estimationService, 3, 32, 'm'), "Borderline");
    assertEquals(method.invoke(estimationService, 3, 32, 'f'), "Borderline");
    assertEquals(method.invoke(estimationService, 4, 32, 'm'), "Borderline");
    assertEquals(method.invoke(estimationService, 4, 32, 'f'), "Borderline");
    assertEquals(method.invoke(estimationService, 5, 32, 'm'), "Borderline");
    assertEquals(method.invoke(estimationService, 5, 32, 'f'), "Borderline");
    /*
    When age is 30, it is considered as over 30
    */
    assertEquals(method.invoke(estimationService, 2, 30, 'm'), "Borderline");
    assertEquals(method.invoke(estimationService, 2, 30, 'f'), "Borderline");
    assertEquals(method.invoke(estimationService, 3, 30, 'm'), "Borderline");
    assertEquals(method.invoke(estimationService, 3, 30, 'f'), "Borderline");
    assertEquals(method.invoke(estimationService, 4, 30, 'm'), "Borderline");
    assertEquals(method.invoke(estimationService, 4, 30, 'f'), "Borderline");
    assertEquals(method.invoke(estimationService, 5, 30, 'm'), "Borderline");
    assertEquals(method.invoke(estimationService, 5, 30, 'f'), "Borderline");
  }

  @Test
  void estimationResultInDangerOver30() throws InvocationTargetException, IllegalAccessException,
          NoSuchMethodException {
    Method method = EstimationServiceImpl.class.getDeclaredMethod("estimationResult",
            int.class, int.class, Character.class);
    method.setAccessible(true);
    assertEquals(method.invoke(estimationService, 6, 32, 'm'), "In danger");
    assertEquals(method.invoke(estimationService, 6, 32, 'f'), "In danger");
    assertEquals(method.invoke(estimationService, 7, 32, 'm'), "In danger");
    assertEquals(method.invoke(estimationService, 7, 32, 'f'), "In danger");

    /*
    When age is 30, it is considered as over 30
    */
    assertEquals(method.invoke(estimationService, 6, 30, 'm'), "In danger");
    assertEquals(method.invoke(estimationService, 6, 30, 'f'), "In danger");
    assertEquals(method.invoke(estimationService, 7, 30, 'm'), "In danger");
    assertEquals(method.invoke(estimationService, 7, 30, 'f'), "In danger");
  }

  @Test
  void estimationResultEarlyOnSetOver30() throws NoSuchMethodException, InvocationTargetException
          , IllegalAccessException {
    Method method = EstimationServiceImpl.class.getDeclaredMethod("estimationResult",
            int.class, int.class, Character.class);
    method.setAccessible(true);
    assertEquals(method.invoke(estimationService, 8, 32, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 8, 32, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 9, 32, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 9, 32, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 10, 32, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 10, 32, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 11, 32, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 11, 32, 'f'), "Early onset");

    /*
    When age is 30, it is considered as over 30
    */
    assertEquals(method.invoke(estimationService, 8, 30, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 8, 30, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 9, 30, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 9, 30, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 10, 30, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 10, 30, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 11, 30, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 11, 30, 'f'), "Early onset");
  }


  /*

  Tests  for patient under 30 years old

   */

  @Test
  void estimationResultNoneUnder30() throws NoSuchMethodException, InvocationTargetException,
          IllegalAccessException {
    Method method = EstimationServiceImpl.class.getDeclaredMethod("estimationResult",
            int.class, int.class, Character.class);
    method.setAccessible(true);
    assertEquals(method.invoke(estimationService, 0, 29, 'm'), "None");
    assertEquals(method.invoke(estimationService, 0, 29, 'f'), "None");
    assertEquals(method.invoke(estimationService, 1, 29, 'm'), "None");
    assertEquals(method.invoke(estimationService, 1, 29, 'f'), "None");
    assertEquals(method.invoke(estimationService, 2, 29, 'f'), "None");
    assertEquals(method.invoke(estimationService, 2, 29, '2'), "None");
    assertEquals(method.invoke(estimationService, 3, 29, 'f'), "None");
  }


  @Test
  void estimationResultInDangerUnder30() throws NoSuchMethodException, InvocationTargetException,
          IllegalAccessException {
    Method method = EstimationServiceImpl.class.getDeclaredMethod("estimationResult",
            int.class, int.class, Character.class);
    method.setAccessible(true);
    assertEquals(method.invoke(estimationService, 3, 29, 'm'), "In danger");
    assertEquals(method.invoke(estimationService, 4, 29, 'f'), "In danger");
    assertEquals(method.invoke(estimationService, 4, 29, 'm'), "In danger");
    assertEquals(method.invoke(estimationService, 4, 29, 'f'), "In danger");
    assertEquals(method.invoke(estimationService, 5, 29, 'f'), "In danger");
    assertEquals(method.invoke(estimationService, 6, 29, 'f'), "In danger");

  }

  @Test
  void estimationResultEarlyOnSetUnder30() throws NoSuchMethodException,
          InvocationTargetException, IllegalAccessException {
    Method method = EstimationServiceImpl.class.getDeclaredMethod("estimationResult",
            int.class, int.class, Character.class);
    method.setAccessible(true);
    assertEquals(method.invoke(estimationService, 5, 29, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 6, 29, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 7, 29, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 8, 29, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 9, 29, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 10, 29, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 11, 29, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 12, 29, 'm'), "Early onset");
    assertEquals(method.invoke(estimationService, 7, 29, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 8, 29, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 8, 29, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 10, 29, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 11, 29, 'f'), "Early onset");
    assertEquals(method.invoke(estimationService, 12, 29, 'f'), "Early onset");

  }

  @Test
  void ageCalculationTest() throws NoSuchMethodException, InvocationTargetException,
          IllegalAccessException {
    /*
    Setting method to accessible as it it private, for testing purposes.
     */
    Method method = EstimationServiceImpl.class.getDeclaredMethod("ageCalculation",
            LocalDate.class);
    method.setAccessible(true);

    LocalDate birthDate = LocalDate.of(1982, 4, 14);
    assertEquals(method.invoke(estimationService, birthDate), 39);
  }


}
