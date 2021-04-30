import { Component, OnInit } from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.css']
})
export class PatientAddComponent implements OnInit {

  patient = new Patient;
  status!:number;
  message!:string;
  error!:string

  constructor(private patientService: PatientService, private router: Router) {}

  ngOnInit(): void {
  }

  onSubmit(){
    this.patientService.addPatient(this.patient).subscribe(
      data => {
        this.status = data.status;
      },
      error => {
        this.status = error.status;
        this.message = error.error.error;
      }
    );
    setTimeout(()=>this.router.navigate(['/patients']),10);

  }





}
