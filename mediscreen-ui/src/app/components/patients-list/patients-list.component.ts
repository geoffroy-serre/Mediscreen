import { Component, OnInit } from '@angular/core';
import {PatientService} from "../../services/patient.service";
import {Patient} from "../../common/patient";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.css']
})
export class PatientsListComponent implements OnInit {

  patients! : HttpResponse<Patient[]>
  status!:number;
  message!:string;
  constructor(private patientService:PatientService) { }

  ngOnInit(): void {
    this.listPatients();
  }

  listPatients() {
    this.patientService.getPatients().subscribe(
      data => {
        this.patients = data;
        this.status = data.status;
      },
      error => {
        this.status = error.status;
        this.message = error.error.error;
      }
    )
  }

}
