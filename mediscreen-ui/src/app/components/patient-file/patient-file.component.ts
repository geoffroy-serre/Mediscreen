import {Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpResponse} from "@angular/common/http";
import {map, timeout} from "rxjs/operators";
import {Observable} from "rxjs";

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

  constructor(private patientService: PatientService, private route: ActivatedRoute, private router:Router) {
    this.idParam = this.route.snapshot.paramMap.get('id') || '0';
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(() => {
      this.patientFile();
    });
  }

  private patientFile() {
    this.patientService.getPatient(this.idParam).subscribe(
      (patient: Patient) => {
        this.patient = patient;
      },
      (err: any) => {
        this.status = err.status;
        this.message = err.message;
        console.error(err)
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
    setTimeout(()=>this.router.navigate(['/patients']),50);
  }
}
