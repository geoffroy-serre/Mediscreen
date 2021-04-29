import {Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {ActivatedRoute} from "@angular/router";
import {HttpResponse} from "@angular/common/http";
import {map, timeout} from "rxjs/operators";

@Component({
  selector: 'app-patient-file',
  templateUrl: './patient-file.component.html',
  styleUrls: ['./patient-file.component.css']
})
export class PatientFileComponent implements OnInit {
  patient!: HttpResponse<Patient>;
  status!: number;
  message!: string;
  private idParam!: string;

  constructor(private patientService: PatientService, private route: ActivatedRoute) {
    this.idParam = this.route.snapshot.paramMap.get('id') || '0';
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(() => {
      this.patientFile();
    });
  }

  private patientFile() {
    this.patientService.getPatient(this.idParam).subscribe(
      data => {
        map((response: { body: any; }) => response.body);
        console.log(data);
        console.log('Data body in ts: ', data.body);
        this.patient = data;
        console.log('Patient body in ts:', this.patient.body);
      },
      error => {
        this.status = error.status;
        this.message = error.error.error;
      }
    );
  }

  deletePatient(){
      this.patientService.deletePatient(this.idParam).subscribe(
        data => {
          this.status = data.status;
        },
        error => {
          this.status = error.status;
          this.message = error.error.error;
        }
      );

  }
}
