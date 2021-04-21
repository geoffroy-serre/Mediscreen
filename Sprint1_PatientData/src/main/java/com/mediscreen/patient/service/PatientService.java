package com.mediscreen.patient.service;

import com.mediscreen.patient.entity.Patient;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientService {
  /**
   * Save new patient with params.
   * Return true if dont exist and save successfully.
   * @param family String
   * @param given String
   * @param dob LocalDate
   * @param sex Character
   * @param address String
   * @param phone String
   * @return boolean
   */
  boolean addPatient(String family, String given, LocalDate dob, Character sex, String address,
                     String phone);

  /**
   * Return a Patient if one is found with right family and Given Name.
   * @param family String
   * @param given String
   * @return Optional<Patient>
   */
  Patient findPatientByFamilyNameAndGivenName(String family, String given);

  /**
   * Return True if patient already exist and have been updated.
   * @param patient Patient
   * @return boolean
   */
  boolean updatePatient(Patient patient);

  /**
   * Return True if patient exist.
   * @param patient Patient
   * @return boolean
   */
  boolean existsPatient(Patient patient);

  /**
   * Return True if patient exist.
   * @param family String
   * @param given String
   * @param dob LocalDate
   * @return boolean
   */
  boolean existsPatientByFamilyAndGivenAndDob(String family, String given, LocalDate dob);

  /**
   * Find All patients
   * @return List of Patients
   */
  List<Patient> findPatients();
}
