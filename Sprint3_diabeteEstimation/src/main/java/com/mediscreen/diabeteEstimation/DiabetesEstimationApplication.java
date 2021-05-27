package com.mediscreen.diabeteEstimation;

import com.mediscreen.diabeteEstimation.service.EstimationService;
import com.mediscreen.diabeteEstimation.service.EstimationServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiabetesEstimationApplication {


  public static void main(String[] args) {
    EstimationService estimationService = new EstimationServiceImpl();

    SpringApplication.run(DiabetesEstimationApplication.class, args);
    List<String> notes = new ArrayList<>();
    notes.add("Hemoglobin A1C");
    notes.add("Hemoglobin A1C");
    notes.add("Hemoglobin A1C");
    notes.add("weight");
    notes.add("weight");
    notes.add("height");
    notes.add("dizziness");
    notes.add("réaction");
    notes.add("Rien");
    notes.add("Rien");
    notes.add("Rien");

    System.out.println(notes);
    LocalDate date = LocalDate.of(1982,4,14);
    System.out.println(estimationService.riskEstimation('m',date,notes));

  }


}
