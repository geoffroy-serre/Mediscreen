import { Component, OnInit } from '@angular/core';
import {PatientService} from "../../services/patient.service";
import {Patient} from "../../common/patient";

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.css']
})
export class PatientsListComponent implements OnInit {

  patient! : Patient[];
  constructor(private patientService:PatientService) { }

  ngOnInit(): void {
    this.listPatients();
  }

  listPatients() {
    this.patientService.getPatients().subscribe(
      data => {
        this.patient = data;
      }
    )
  }

}
