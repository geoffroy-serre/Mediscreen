import {Component, OnInit} from '@angular/core';
import {PatientService} from "../../services/patient.service";
import {Patient} from "../../common/patient";
import {HttpResponse} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.css']
})
export class PatientsListComponent implements OnInit {

  patients!: HttpResponse<Patient[]>
  status!: number;
  message!: string;
  searchMode: boolean = false;

  constructor(private patientService: PatientService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.listPatients();
  }

  listPatients() {
    this.searchMode = this.route.snapshot.paramMap.has('family' && 'given');
    if (this.searchMode) {

      this.handleSearchPatient();
    } else {
      this.handlePatientsList();
    }

  }

  handleSearchPatient() {
    const family = this.route.snapshot.paramMap.get('family')||'none';
    const given = this.route.snapshot.paramMap.get('given')||'none';
    this.patientService.searchPatient(family,given).subscribe(
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

  handlePatientsList() {
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
