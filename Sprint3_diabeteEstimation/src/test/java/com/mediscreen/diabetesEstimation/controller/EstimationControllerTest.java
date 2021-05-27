package com.mediscreen.diabetesEstimation.controller;

import com.mediscreen.diabetesEstimation.model.EstimationResult;
import com.mediscreen.diabetesEstimation.service.EstimationService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstimationController.class)
class EstimationControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private EstimationService estimationService;

  List<String> notes = new ArrayList<>();

  @Test
  public void riskEstimationTest() throws Exception {
    notes.add("Smoker");
    notes.add("Smoker");
    notes.add("dizziness");
    notes.add("not exists");
    Character gender = 'm';
    LocalDate birthDate = LocalDate.of(1982, 4, 14);
    EstimationResult result= new EstimationResult("Borderline");
when(estimationService.riskEstimation(gender,birthDate,notes)).thenReturn(result);
    mockMvc.perform(get("/estimation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("gender", String.valueOf(gender))
            .param("birthdate", String.valueOf(birthDate))
            .param("notes", String.valueOf(notes)))
            .andExpect(status().isOk());

    notes.clear();
  }

  @Test
  public void riskEstimationTestBadNoNotes() throws Exception {
    Character gender = 'm';
    LocalDate birthDate = LocalDate.of(1982, 4, 14);
    EstimationResult result= new EstimationResult("Borderline");
    when(estimationService.riskEstimation(gender,birthDate,notes)).thenReturn(result);
    mockMvc.perform(get("/estimation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("gender", String.valueOf(gender))
            .param("birthdate", String.valueOf(birthDate)))
            .andExpect(status().isBadRequest());

    notes.clear();
  }

  @Test
  public void riskEstimationTestBadNoGender() throws Exception {
    notes.add("Smoker");
    notes.add("Smoker");
    notes.add("dizziness");
    notes.add("not exists");
    Character gender = 'm';
    LocalDate birthDate = LocalDate.of(1982, 4, 14);
    EstimationResult result= new EstimationResult("Borderline");
    when(estimationService.riskEstimation(gender,birthDate,notes)).thenReturn(result);
    mockMvc.perform(get("/estimation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("birthdate", String.valueOf(birthDate))
            .param("notes", String.valueOf(notes)))
            .andExpect(status().isBadRequest());

    notes.clear();
  }

  @Test
  public void riskEstimationTestBadGender() throws Exception {
    notes.add("Smoker");
    notes.add("Smoker");
    notes.add("dizziness");
    notes.add("not exists");
    Character gender = 'Q';
    LocalDate birthDate = LocalDate.of(1982, 4, 14);
    EstimationResult result= new EstimationResult("Borderline");
    when(estimationService.riskEstimation(gender,birthDate,notes)).thenReturn(result);
    mockMvc.perform(get("/estimation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("gender", String.valueOf(gender))
            .param("birthdate", String.valueOf(birthDate))
            .param("notes", String.valueOf(notes)))
            .andExpect(status().isBadRequest());

    notes.clear();
  }

  @Test
  public void riskEstimationTestBadNoBirthDate() throws Exception {
    notes.add("Smoker");
    notes.add("Smoker");
    notes.add("dizziness");
    notes.add("not exists");
    Character gender = 'm';
    LocalDate birthDate = LocalDate.of(1982, 4, 14);
    EstimationResult result= new EstimationResult("Borderline");
    when(estimationService.riskEstimation(gender,birthDate,notes)).thenReturn(result);
    mockMvc.perform(get("/estimation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("birthdate", String.valueOf(birthDate))
            .param("notes", String.valueOf(notes)))
            .andExpect(status().isBadRequest());

    notes.clear();
  }
}

