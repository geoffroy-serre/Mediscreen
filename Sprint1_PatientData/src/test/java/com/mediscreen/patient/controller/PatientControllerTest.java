package com.mediscreen.patient.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.PatientApplication;
import com.mediscreen.patient.entity.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.PatientService;
import java.time.LocalDate;
import java.util.Optional;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

  @MockBean
  private PatientService patientService;
  @MockBean
  private PatientRepository patientRepository;

  @Autowired
  MockMvc mockMvc;

  public static Patient patient;

  @BeforeEach()
  void  setupPatient(){
    String family = "testFamily";
    String given = "testGiven";
    LocalDate dob = LocalDate.of(1982, 4, 14);
    Character sex = 'M';
    String address = "123 testAddress St";
    String phone = "123-456-758";
    patient = new Patient(family, given, dob, sex, address, phone);
  }


  @Test
  public void updatePatient() throws Exception {
    when(patientService.updatePatient(patient)).thenReturn(true);
    mockMvc.perform(put("/patient/update")
    .contentType(MediaType.APPLICATION_JSON)
    .flashAttr("patient",patient))
            .andExpect(status().isOk());
  }
  @Test
  public void updatePatientUnknown() throws Exception {
    patient.setAddress("a");
    when(patientService.updatePatient(patient)).thenReturn(false);
    mockMvc.perform(put("/patient/update")
            .contentType(MediaType.APPLICATION_JSON)
            .flashAttr("patient",patient))
            .andExpect(status().isConflict());
  }
}

