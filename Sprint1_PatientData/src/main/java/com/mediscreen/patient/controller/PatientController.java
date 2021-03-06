package com.mediscreen.patient.controller;

import com.mediscreen.patient.customValidator.Gender;
import com.mediscreen.patient.entity.Patient;
import com.mediscreen.patient.exceptions.PatientNotFoundException;
import com.mediscreen.patient.service.PatientService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@Validated
@CrossOrigin(origins = "${mediscreen.ui.cross.origin}")
public class PatientController {

  @Autowired
  PatientService patientService;

  Logger logger = LoggerFactory.getLogger(PatientController.class);

  @PostMapping("patient/add")
  public void addPatient(@RequestParam @Size(min = 2, max = 60, message = "family must be " +
          "between {min} and {max} characters long") String family,
                         @RequestParam @Valid @Size(min = 2, max = 60,
                                 message = "given must be between {min} and {max} characters " +
                                         "long") String given,
                         @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                         @RequestParam @Gender Character sex,
                         @RequestParam(defaultValue = "not specified", required = false) @Size(min = 2, max =
                                 100,
                                 message = "address must be " +
                                         "between" +
                                         " {min} and {max} characters long") String address,
                         @RequestParam(defaultValue = "not specified", required = false) @Size(min = 2, max =
                                 20,
                                 message = "phone must be between " +
                                         "{min} and {max} characters long") String phone,
                         HttpServletResponse response) {
    logger.info("Enter addPatient in patient microservice");
    System.out.println(address);
    if (patientService.addPatient(family, given, dob, sex, address, phone)) {
      logger.debug("Patient added: status 200");
      response.setStatus(200);
    } else {
      logger.debug("Patient not added: status 409");
      response.setStatus(409);
    }
  }

  @PutMapping("patient/update")
  public void updatePatient(@RequestBody @Valid Patient patient, HttpServletResponse response) {
    logger.info("Entering updatePatient for patient id " + patient.getId());
    if (patient.getAddress().isEmpty()) {
      patient.setAddress("not specified");
    }
    if (patient.getPhoneNumber().isEmpty()) {
      patient.setPhoneNumber("not specified");
    }

    if (patientService.updatePatient(patient)) {
      logger.debug("Patient updated: status 200");
      response.setStatus(200);
    } else {
      logger.debug("Patient not updated: status 404");
      response.setStatus(404);
    }
  }

  @GetMapping("patient/search")
  public List<Patient> getPatientByFamilyAndGivenName(
          @RequestParam @Size(min = 2, max = 255, message = "familyName must be between {min} and" +
                  " {max} characters long") String familyName,
          @RequestParam @Size(min = 2, max = 255, message = "givenName must be between {min} and " +
                  "{max}" +
                  " characters long") String givenName,
          HttpServletResponse response) {
    List<Patient> patients = patientService.findPatientByFamilyNameAndGivenName(familyName,
            givenName);
    if (patients.isEmpty()) {
      logger.debug("No patient found for " + familyName + " " + givenName);
      response.setStatus(404);
      return new ArrayList<Patient>();
    }


    logger.debug("Patient found for " + familyName + " " + givenName + " returning patient");
    return patients;
  }


  @GetMapping("patients")
  public List<Patient> getAllPatient(HttpServletResponse response) {
    logger.debug("Return Patients List");
    List<Patient> patients = patientService.findPatients();
    if (patients.isEmpty()) {
      response.setStatus(404);
      return patients;
    }
    return patients;
  }

  @GetMapping("patient/file")
  public Optional<Patient> getPatientById(@RequestParam long id) {
    logger.debug("Return Patient with id:" + id);
    if (patientService.findById(id).isEmpty()) {
      throw new PatientNotFoundException();
    }
    return patientService.findById(id);
  }

  @DeleteMapping("patient/delete")
  public void deletePatientById(@RequestParam @Valid Long id, HttpServletResponse response) {
    if (patientService.existsPatientById(id)) {
      patientService.deletePatientById(id);
      response.setStatus(200);
    } else {
      response.setStatus(404);
    }
  }


}
