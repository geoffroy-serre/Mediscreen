package com.mediscreen.diabeteEstimation.controller;


import com.mediscreen.diabeteEstimation.service.EstimationService;
import com.mediscreen.diabeteEstimation.service.EstimationServiceImpl;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class EstimationController {
  Logger logger = LoggerFactory.getLogger(EstimationController.class);

  @Autowired
  EstimationService estimationService;

  @GetMapping("estimation")
  public String riskEstimation(@RequestParam Character gender, @RequestParam LocalDate birthdate,
                               @RequestParam List<String> notes){
    logger.debug("Entering riskEstimation");
    return estimationService.riskEstimation(gender,birthdate,notes);
  }
}
