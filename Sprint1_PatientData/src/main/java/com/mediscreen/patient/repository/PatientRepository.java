package com.mediscreen.patient.repository;

import com.mediscreen.patient.entity.Patient;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
  boolean existsPatientByFamilyNameAndGivenNameAndDateOfBirth(String family, String given,
                                                              LocalDate dob);
  Optional<Patient> findPatientByFamilyNameAndGivenName(String family, String given);
  boolean existsPatient(Patient patient);
}
