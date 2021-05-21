package com.mediscreen.diabeteEstimation.controller;


import com.mediscreen.diabeteEstimation.service.EstimationService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class EstimationController {

  @Autowired
  EstimationService estimationService;

  @GetMapping("estimation")
  public String riskEstimation(Character gender, LocalDate birthdate, List<String> notes){
    return estimationService.riskEstimation(gender,birthdate,notes);
  }
}
