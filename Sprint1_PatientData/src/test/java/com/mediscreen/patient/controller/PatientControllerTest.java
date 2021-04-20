package com.mediscreen.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mediscreen.patient.entity.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.service.PatientServiceImpl;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PatientController.class)
public class PatientControllerTest {

  @MockBean
  private PatientService patientService;


  @Autowired
  MockMvc mockMvc;

  Patient patient;

  @BeforeEach()
  void setupPatient() {
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
    String jsonRequest = "{ \"id\":\"134\",\"familyName\":\"Starman\"," +
            "\"givenName\":\"Emil\"," +
            "\"dateOfBirth\":\"1982-04-14\"," +
            "\"gender\":\"m\"," +
            "\"address\":\"Frankfurt\"," +
            "\"phoneNumber\":\"654485\"}";
    when(patientService.updatePatient(any(Patient.class))).thenReturn(true);
    mockMvc.perform(put("/patient/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isOk());
  }

  @Test
  public void updatePatientUnknown() throws Exception {
   String jsonRequest = "{ \"id\":\"134\",\"familyName\":\"Starman\"," +
            "\"givenName\":\"Emil\"," +
            "\"dateOfBirth\":\"1982-04-14\",\"gender\":\"m\"," +
            "\"address\":\"Frankfurt\"," +
            "\"phoneNumber\":\"654485\"}";
    when(patientService.updatePatient(any(Patient.class))).thenReturn(false);
    mockMvc.perform(put("/patient/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isConflict());
  }

  @Test
  public void updatePatientNotValid() throws Exception {
    patient.setAddress("");
    mockMvc.perform(put("/patient/update")
            .contentType(MediaType.APPLICATION_JSON)
            .flashAttr("patient", patient))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void addPatientNotValid() throws Exception {
    patient.setAddress("m");
    when(patientService.addPatient(patient.getFamilyName(),patient.getGivenName(),
            patient.getDateOfBirth(),patient.getGender(),patient.getAddress(),
            patient.getPhoneNumber())).thenReturn(true);
    mockMvc.perform(post("/patient/add")
            .param("family", patient.getFamilyName())
            .param("given", patient.getGivenName())
            .param("dob", patient.getDateOfBirth().toString())
            .param("sex", patient.getGender().toString())
            .param("address", patient.getAddress())
            .param("phone", patient.getPhoneNumber()))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void addPatientMissingParam() throws Exception {
    mockMvc.perform(post("/patient/add")
            .param("given", patient.getGivenName())
            .param("dob", patient.getDateOfBirth().toString())
            .param("sex", patient.getGender().toString())
            .param("address", patient.getAddress())
            .param("phone", patient.getPhoneNumber()))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void addPatient() throws Exception {
    when(patientService.addPatient(patient.getFamilyName(),patient.getGivenName(),
            patient.getDateOfBirth(),patient.getGender(),patient.getAddress(),
            patient.getPhoneNumber())).thenReturn(true);
    mockMvc.perform(post("/patient/add")
            .param("family", patient.getFamilyName())
            .param("given", patient.getGivenName())
            .param("dob", patient.getDateOfBirth().toString())
            .param("sex", patient.getGender().toString())
            .param("address", patient.getAddress())
            .param("phone", patient.getPhoneNumber()))
            .andExpect(status().isOk());
  }

  @Test
  public void getPatient() throws Exception {
    when(patientService.findPatientByFamilyNameAndGivenName(patient.getFamilyName() ,
            patient.getGivenName())).thenReturn(patient);
    mockMvc.perform(get("/patient")
            .param("familyName", patient.getFamilyName())
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isOk());
  }
  @Test
  public void getPatientNoResult() throws Exception {
    when(patientService.findPatientByFamilyNameAndGivenName(patient.getFamilyName() ,
            patient.getGivenName())).thenReturn(null);
    mockMvc.perform(get("/patient")
            .param("familyName", patient.getFamilyName())
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isNotFound());
  }

  @Test
  public void getPatientNotValid() throws Exception {
    patient.setFamilyName("r");
    mockMvc.perform(get("/patient")
            .param("familyName", patient.getFamilyName())
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isBadRequest());
  }
  @Test
  public void getPatientMissingParam() throws Exception {
    mockMvc.perform(get("/patient")
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isBadRequest());
  }


}

