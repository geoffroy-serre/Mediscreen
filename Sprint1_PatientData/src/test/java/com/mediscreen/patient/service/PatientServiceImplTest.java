package com.mediscreen.patient.service;

import com.mediscreen.patient.entity.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

  @Mock
  PatientRepository patientRepository;

  @InjectMocks
  PatientService patientService = new PatientServiceImpl();

  @Mock
  HttpServletResponse response;


  public static Patient patient;

  @BeforeAll
  public static void setup() {
    String family = "testFamily";
    String given = "testGiven";
    LocalDate dob = LocalDate.of(1982, 4, 14);
    Character sex = 'M';
    String address = "123 testAdress St";
    String phone = "123-456-758";
    patient = new Patient(family, given, dob, sex, address, phone);
  }

  @Test
  void addPatientUnKnown() {
    when(patientRepository.existsPatientByFamilyNameAndGivenNameAndDateOfBirth(patient.getFamilyName(), patient.getGivenName(),
            patient.getDateOfBirth())).thenReturn(false);
    assertTrue(patientService.addPatient(patient.getFamilyName(), patient.getGivenName(),
            patient.getDateOfBirth(),
            patient.getGender(), patient.getAddress(),
            patient.getPhoneNumber()));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientByFamilyNameAndGivenNameAndDateOfBirth(patient.getFamilyName(), patient.getGivenName(), patient.getDateOfBirth());
    Mockito.verify(patientRepository, Mockito.times(1)).save(any(Patient.class));
  }

  @Test
  void addPatientKnown() {
    when(patientRepository.existsPatientByFamilyNameAndGivenNameAndDateOfBirth(patient.getFamilyName(), patient.getGivenName(),
            patient.getDateOfBirth())).thenReturn(true);
    assertFalse(patientService.addPatient(patient.getFamilyName(), patient.getGivenName(),
            patient.getDateOfBirth(),
            patient.getGender(), patient.getAddress(),
            patient.getPhoneNumber()));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientByFamilyNameAndGivenNameAndDateOfBirth(patient.getFamilyName(), patient.getGivenName(),
            patient.getDateOfBirth());
    Mockito.verify(patientRepository, Mockito.times(0)).save(any(Patient.class));
  }

  @Test
  void updatePatient() {
    when(patientRepository.existsPatientById(patient.getId())).thenReturn(true);
    assertTrue(patientService.updatePatient(patient));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientById(patient.getId());
    Mockito.verify(patientRepository, Mockito.times(1)).save(any(Patient.class));
  }
  @Test
  void getPatients() {
    List<Patient> patients = new ArrayList<>();
    when(patientRepository.findAll()).thenReturn(patients);
    assertNotNull(patientService.findPatients());
    Mockito.verify(patientRepository, Mockito.times(1)).findAll();
  }

  @Test
  void updatePatientUnKnown() {
    when(patientRepository.existsPatientById(patient.getId())).thenReturn(false);
    assertFalse(patientService.updatePatient(patient));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientById(patient.getId());
    Mockito.verify(patientRepository, Mockito.times(0)).save(any(Patient.class));
  }

  @Test
  void findPatientByFamilyNameAndGivenName() {
    List<Patient> patients = new ArrayList<>();
    patients.add(patient);
    when(patientRepository.findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName())).thenReturn(patients);
    assertNotNull(patientService.findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName()));
    Mockito.verify(patientRepository, Mockito.times(1)).findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName());
  }
  @Test
  void findPatientByFamilyNameAndGivenNameNoResult() {
    when(patientRepository.findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName())).thenReturn(null);
    assertNull(patientService.findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName()));
    Mockito.verify(patientRepository, Mockito.times(1)).findPatientByFamilyNameAndGivenName(patient.getFamilyName(),
            patient.getGivenName());
  }

  @Test
  void existsPatient() {
    when(patientRepository.existsPatientById(patient.getId())).thenReturn(false);
    assertFalse(patientService.existsPatient(patient));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientById(patient.getId());
  }
  @Test
  void existsPatientNot() {
    when(patientRepository.existsPatientById(patient.getId())).thenReturn(false);
    assertFalse(patientService.existsPatient(patient));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientById(patient.getId());
  }

  @Test
  void existsPatientByFamilyAndGivenAndDob() {
    when(patientRepository.existsPatientByFamilyNameAndGivenNameAndDateOfBirth(patient.getFamilyName() ,patient.getGivenName(),patient.getDateOfBirth())).thenReturn(true);
    assertTrue(patientService.existsPatientByFamilyAndGivenAndDob(patient.getFamilyName()
            ,patient.getGivenName(),patient.getDateOfBirth()));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientByFamilyNameAndGivenNameAndDateOfBirth(patient.getFamilyName() ,patient.getGivenName(),patient.getDateOfBirth());
  }
  @Test
  void existsPatientByFamilyAndGivenAndDobFalse() {
    when(patientRepository.existsPatientByFamilyNameAndGivenNameAndDateOfBirth(patient.getFamilyName() ,patient.getGivenName(),patient.getDateOfBirth())).thenReturn(false);
    assertFalse(patientService.existsPatientByFamilyAndGivenAndDob(patient.getFamilyName()
            ,patient.getGivenName(),patient.getDateOfBirth()));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientByFamilyNameAndGivenNameAndDateOfBirth(patient.getFamilyName() ,patient.getGivenName(),patient.getDateOfBirth());
  }

  @Test
  void findById() {
    when(patientRepository.findById(134L)).thenReturn(Optional.of(patient));
    assertFalse(patientService.findById(134L).isEmpty());
    Mockito.verify(patientRepository, Mockito.times(1)).findById(134L);
  }
  @Test
  void findByIdEmpty() {
    when(patientRepository.findById(134L)).thenReturn(Optional.empty());
    assertTrue(patientService.findById(134L).isEmpty());
    Mockito.verify(patientRepository, Mockito.times(1)).findById(134L);
  }
  @Test
  void existsPatientById() {
    when(patientRepository.existsPatientById(134L)).thenReturn(true);
    assertTrue(patientService.existsPatientById(134L));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientById(134L);
  }
  @Test
  void existsByIdEmpty() {
    when(patientRepository.existsPatientById(134L)).thenReturn(false);
    assertFalse(patientService.existsPatientById(134L));
    Mockito.verify(patientRepository, Mockito.times(1)).existsPatientById(134L);
  }
  @Test
  void deleteById() {
    //Check that no errors at all are thrown
    assertAll(()->patientService.deletePatientById(134L));
    Mockito.verify(patientRepository, Mockito.times(1)).deleteById(134L);
  }
}
