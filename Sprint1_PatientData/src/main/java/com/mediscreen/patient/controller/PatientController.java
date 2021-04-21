package com.mediscreen.patient.controller;

import com.mediscreen.patient.customValidator.Gender;
import com.mediscreen.patient.entity.Patient;
import com.mediscreen.patient.service.PatientService;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.*;

@RestController
@Validated
public class PatientController {

  @Autowired
  PatientService patientService;

  Logger logger = LoggerFactory.getLogger(PatientController.class);

  @PostMapping("patient/add")
  public void addPatient(@RequestParam @Size(min = 2, max = 255,message="family must be between {min} and {max} characters long") String family,
                         @RequestParam @Valid @Size(min = 2, max = 255,
                                 message="given must be between {min} and {max} characters long")  String given,
                         @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                         @RequestParam @Gender  Character sex,
                         @RequestParam(defaultValue = "not specified",required = false) @Size(min = 2, max =
                                 255,
                                 message="address must be " +
                                 "between" +
                                 " {min} and {max} characters long") String address,
                         @RequestParam (defaultValue = "not specified",required = false) @Size(min = 2, max =
                                 255,
                                 message="phone must be between " +
                                 "{min} and {max} characters long") String phone,
                         HttpServletResponse response) {
    logger.info("Enter addPatient in patient microservice");
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
    logger.info("Entering updatePatient for patient id "+patient.getId());
    if (patient.getAddress().isEmpty()){
      patient.setAddress("not specified");
    }
    if (patient.getPhoneNumber().isEmpty()){
      patient.setPhoneNumber("not specified");
    }

    if (patientService.updatePatient(patient)) {
      logger.debug("Patient updated: status 200");
      response.setStatus(200);
    } else {
      logger.debug("Patient not updated: status 409");
      response.setStatus(409);
    }
  }

  @GetMapping("patient")
  public Patient getPatientByFamilyAndGivenName(
          @RequestParam @Size(min=2, max=255, message="familyName must be between {min} and {max} characters long") String familyName,
          @RequestParam  @Size(min=2, max=255, message="givenName must be between {min} and {max}" +
                  " characters long") String givenName,
          HttpServletResponse response) {
Patient patient = patientService.findPatientByFamilyNameAndGivenName(familyName, givenName);
if(patient ==null){
  logger.debug("No patient found for "+familyName+" "+givenName);
  response.setStatus(404);
  return null;
}
    logger.debug("Patient found for "+familyName+" "+givenName+" returning patient");
    return patient;
  }

 /*TODO Create Delete Controller by id.
 * TODO Create Get patient list (all).
 *  TODO write tests for those.
 *   TODO Deal with appointements: create table, get post put delete controller for 'em.
  */
}
