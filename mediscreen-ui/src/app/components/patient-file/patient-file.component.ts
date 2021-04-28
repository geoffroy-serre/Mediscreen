import {Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {ActivatedRoute} from "@angular/router";
import {HttpResponse} from "@angular/common/http";
import {timeout} from "rxjs/operators";

@Component({
  selector: 'app-patient-file',
  templateUrl: './patient-file.component.html',
  styleUrls: ['./patient-file.component.css']
})
export class PatientFileComponent implements OnInit {
  patient!: Patient;
  status!: number;
  message!: string;
  private idParam!: string;

  constructor(private patientService: PatientService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.idParam = this.route.snapshot.paramMap.get('id')||'0';
    this.patientFile().then((response) => {this.patient=response.data; this.status=response.status})
      .catch((err) => {this.message=err.response.data.error; this.status=err.response.status});
  }

  private patientFile() {
    return this.patientService.getPatient(this.idParam);
  }
}
