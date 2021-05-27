package com.mediscreen.diabetesEstimation.controller;


import com.mediscreen.diabetesEstimation.customValidator.Gender;
import com.mediscreen.diabetesEstimation.model.EstimationResult;
import com.mediscreen.diabetesEstimation.service.EstimationService;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "${mediscreen.ui.cross.origin}")
public class EstimationController {
  Logger logger = LoggerFactory.getLogger(EstimationController.class);

  @Autowired
  EstimationService estimationService;

  @GetMapping("estimation")
  public EstimationResult riskEstimation(@RequestParam @Gender Character gender,
                                         @RequestParam @Valid LocalDate birthdate,
                                         @RequestParam @Valid List<String> notes) {
    logger.debug("Entering riskEstimation");
    return estimationService.riskEstimation(gender, birthdate, notes);
  }
}
