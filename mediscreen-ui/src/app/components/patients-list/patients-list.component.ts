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

  patients! : Patient[];
  status!:number;
  message!:string;
  constructor(private patientService:PatientService) { }

  ngOnInit(): void {
    this.listPatients().then((response) => {this.patients=response.data; this.status=response.status})
      .catch((err) => {this.message=err.response.data.error; this.status=err.response.status});
  }

  listPatients() {
    return this.patientService.getPatients();
  }

}
