package com.mediscreen.patient.controller;

import com.mediscreen.patient.entity.Patient;
import com.mediscreen.patient.service.PatientService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
  public void updatePatientNoAddress() throws Exception {
    String jsonRequest = "{ \"id\":\"134\",\"familyName\":\"Starman\"," +
            "\"givenName\":\"Emil\"," +
            "\"dateOfBirth\":\"1982-04-14\",\"gender\":\"m\"," +
            "\"address\":\"\"," +
            "\"phoneNumber\":\"654485\"}";
    when(patientService.updatePatient(any(Patient.class))).thenReturn(true);
    mockMvc.perform(put("/patient/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isOk());
  }

  @Test
  public void updatePatientNoAddressNoFamily() throws Exception {
    String jsonRequest = "{ \"id\":\"134\",\"familyName\":\"\"," +
            "\"givenName\":\"Emil\"," +
            "\"dateOfBirth\":\"1982-04-14\",\"gender\":\"m\"," +
            "\"address\":\"\"," +
            "\"phoneNumber\":\"654485\"}";
    when(patientService.updatePatient(any(Patient.class))).thenReturn(true);
    mockMvc.perform(put("/patient/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void updatePatientNoAddressNoPhone() throws Exception {
    String jsonRequest = "{ \"id\":\"134\",\"familyName\":\"Starman\"," +
            "\"givenName\":\"Emil\"," +
            "\"dateOfBirth\":\"1982-04-14\",\"gender\":\"m\"," +
            "\"address\":\"\"," +
            "\"phoneNumber\":\"\"}";
    when(patientService.updatePatient(any(Patient.class))).thenReturn(true);
    mockMvc.perform(put("/patient/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isOk());
  }

  @Test
  public void addPatientNotValid() throws Exception {
    patient.setAddress("m");
    when(patientService.addPatient(patient.getFamilyName(), patient.getGivenName(),
            patient.getDateOfBirth(), patient.getGender(), patient.getAddress(),
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
  public void addPatientMissingMandatoryParam() throws Exception {
    mockMvc.perform(post("/patient/add")
            .param("given", patient.getGivenName())
            .param("dob", patient.getDateOfBirth().toString())
            .param("sex", patient.getGender().toString())
            .param("address", patient.getAddress())
            .param("phone", patient.getPhoneNumber()))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void addPatientNoAddressOk() throws Exception {
    when(patientService.addPatient(anyString(), anyString(), any(LocalDate.class), anyChar(),
            anyString(), anyString())).thenReturn(true);
    mockMvc.perform(post("/patient/add")
            .param("family", patient.getFamilyName())
            .param("given", patient.getGivenName())
            .param("dob", patient.getDateOfBirth().toString())
            .param("sex", patient.getGender().toString())
            .param("phone", patient.getPhoneNumber()))
            .andExpect(status().isOk());
  }

  @Test
  public void addPatientNoPhoneOk() throws Exception {
    when(patientService.addPatient(anyString(), anyString(), any(LocalDate.class), anyChar(),
            anyString(), anyString())).thenReturn(true);
    mockMvc.perform(post("/patient/add")
            .param("family", patient.getFamilyName())
            .param("given", patient.getGivenName())
            .param("dob", patient.getDateOfBirth().toString())
            .param("sex", patient.getGender().toString())
            .param("address", patient.getAddress()))
            .andExpect(status().isOk());
  }

  @Test
  public void addPatientNoAddressNoPhoneOk() throws Exception {
    when(patientService.addPatient(anyString(), anyString(), any(LocalDate.class), anyChar(),
            anyString(), anyString())).thenReturn(true);
    mockMvc.perform(post("/patient/add")
            .param("family", patient.getFamilyName())
            .param("given", patient.getGivenName())
            .param("dob", patient.getDateOfBirth().toString())
            .param("sex", patient.getGender().toString()))
            .andExpect(status().isOk());
  }

  @Test
  public void addPatient() throws Exception {
    when(patientService.addPatient(patient.getFamilyName(), patient.getGivenName(),
            patient.getDateOfBirth(), patient.getGender(), patient.getAddress(),
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
  public void addPatientAlreadyExists() throws Exception {
    when(patientService.addPatient(patient.getFamilyName(), patient.getGivenName(),
            patient.getDateOfBirth(), patient.getGender(), patient.getAddress(),
            patient.getPhoneNumber())).thenReturn(false);
    mockMvc.perform(post("/patient/add")
            .param("family", patient.getFamilyName())
            .param("given", patient.getGivenName())
            .param("dob", patient.getDateOfBirth().toString())
            .param("sex", patient.getGender().toString())
            .param("address", patient.getAddress())
            .param("phone", patient.getPhoneNumber()))
            .andExpect(status().isConflict());
  }

  @Test
  public void getPatientSearch() throws Exception {
    List<Patient> patients = new ArrayList<>();
    patients.add(patient);
    when(patientService.findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName())).thenReturn(patients);
    mockMvc.perform(get("/patient/search")
            .param("familyName", patient.getFamilyName())
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isOk());
  }

  @Test
  public void getPatientNoResult() throws Exception {
    when(patientService.findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName())).thenReturn(null);
    mockMvc.perform(get("/patient")
            .param("familyName", patient.getFamilyName())
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isNotFound());
  }

  @Test
  public void getPatientSearchNotValid() throws Exception {
    patient.setFamilyName("r");
    mockMvc.perform(get("/patient/search")
            .param("familyName", patient.getFamilyName())
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void getPatientSearchMissingParam() throws Exception {
    mockMvc.perform(get("/patient/search")
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isBadRequest());
  }
  @Test
  public void getPatientSearchMNotFound() throws Exception {
    List<Patient> patients = new ArrayList<>();
    when(patientService.findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName())).thenReturn(patients);
    mockMvc.perform(get("/patient/search")
            .param("familyName", patient.getFamilyName())
            .param("givenName", patient.getGivenName()))
            .andExpect(status().isNotFound());
  }

  @Test
  public void getPatients() throws Exception {
List<Patient> patients = new ArrayList<>();

patients.add(patient);
    when(patientService.findPatients()).thenReturn(patients);
    mockMvc.perform(get("/patients")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  public void getPatientsEmpty() throws Exception {
    List<Patient> patients = new ArrayList<>();
    when(patientService.findPatients()).thenReturn(patients);
    mockMvc.perform(get("/patients")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  public void getPatientID() throws Exception {
    patient.setId(134L);
    Optional<Patient> patientO = Optional.of(patient);
    when(patientService.findById(134L)).thenReturn(patientO);
    mockMvc.perform(get("/patient/file")
            .param("id",patient.getId().toString() ))
            .andExpect(status().isOk());
  }

  @Test
  public void getPatientIDNotValid() throws Exception {
    patient.setId(134L);
    when(patientService.findById(134L)).thenReturn(Optional.empty());
    mockMvc.perform(get("/patient/file")
            .param("id",patient.getId().toString()))
            .andExpect(status().isNotFound());
  }

  @Test
  public void getPatientIDMissingParam() throws Exception {
    mockMvc.perform(get("/patient/file"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void deletePatientIDexist() throws Exception {
    patient.setId(134L);
    when(patientService.existsPatientById(134L)).thenReturn(true);
    mockMvc.perform(delete("/patient/delete")
            .param("id",patient.getId().toString() ))
            .andExpect(status().isOk());
  }

  @Test
  public void deletePatientIDDontExist() throws Exception {
    patient.setId(134L);
    when(patientService.existsPatientById(134L)).thenReturn(false);
    mockMvc.perform(delete("/patient/delete")
            .param("id",patient.getId().toString()))
            .andExpect(status().isNotFound());
  }

  @Test
  public void deletePatientIDMissingParam() throws Exception {
    mockMvc.perform(delete("/patient/delete"))
            .andExpect(status().isBadRequest());
  }
}

