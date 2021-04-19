package com.mediscreen.patient.controller;

import com.mediscreen.patient.entity.Patient;
import com.mediscreen.patient.service.PatientService;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
public class PatientController {


  PatientService patientService;

  Logger logger = LoggerFactory.getLogger(PatientController.class);

  @PostMapping("patient/add")
  public void addPatient(@RequestParam @Min(1) @Max(255) String family,
                         @RequestParam @Min(1) @Max(255) String given,
                         @RequestParam @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dob,
                         @RequestParam @NotNull @Min(1) @Max(1) Character sex,
                         @RequestParam @Min(1) @Max(255) String address,
                         @RequestParam @Min(1) @Max(15) String phone,
                         HttpServletResponse response) {
    logger.info("Enter addPatient in patient microservice");
    if(patientService.addPatient(family,given,dob,sex,address,phone)){
response.setStatus(200);
    }
    else{
      response.setStatus(409);
    }
  }


  @PutMapping("patient/update")
  public void updatePatient (@Valid Patient patient, HttpServletResponse response){
    if(patientService.updatePatient(patient)){
      response.setStatus(200);
    }
    else{
      response.setStatus(409);
    }
  }

  @GetMapping("/patient")
  public Optional<Patient> getPatientByFamilyAndGivenName(
          @RequestParam @Min(1) @Max(255) String familyName,
          @RequestParam @Min(1) @Max(255)String givenName){

    return patientService.findPatientByFamilyNameAndGivenName(familyName,givenName);
  }


}
