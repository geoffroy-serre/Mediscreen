package com.mediscreen.patient.service;

import com.mediscreen.patient.entity.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.utils.UtilsStringFormatter;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

  @Autowired
  PatientRepository patientRepository;

  Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

  /**
   * @inheritDoc
   */
  @Override
  public boolean addPatient(String family, String given, LocalDate dob, Character sex,
                            String address,
                            String phone) {
    logger.debug("Entering addPatient");
    if (!existsPatientByFamilyAndGivenAndDob(family, given, dob)) {
      Patient patient = new Patient(
              UtilsStringFormatter.TrimSpaces(family),
              UtilsStringFormatter.TrimSpaces(given),
              dob,
              sex,
              UtilsStringFormatter.TrimSpaces(address),
              phone);
      patientRepository.save(patient);
      logger.debug("patient saved, returning true.");
      return true;
    }
    logger.debug("patient already exist. Returning false");
    return false;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean updatePatient(Patient patient) {
    logger.debug("Entering updatePatient");
    if (existsPatient(patient)) {
      patientRepository.save(patient);
      logger.debug("patient updated, returning true.");
      return true;
    }
    logger.debug("patient dont exist. Returning false");
    return false;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Patient findPatientByFamilyNameAndGivenName(String family, String given) {
    logger.debug("Entering findPatientByFamilyNameAndGivenName :"+family+" "+given);
    return patientRepository.findPatientByFamilyNameAndGivenName(family, given);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean existsPatient(Patient patient) {
    logger.debug("Entering existsPatient for id: "+patient.getId());
    return patientRepository.existsPatientById(patient.getId());
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean existsPatientByFamilyAndGivenAndDob(String family, String given, LocalDate dob) {
    logger.debug("Entering existsPatientByFamilyAndGivenAndDob :"+family+" "+given+" "+ dob);
    return patientRepository.existsPatientByFamilyNameAndGivenNameAndDateOfBirth(family, given,
            dob);
  }
}
