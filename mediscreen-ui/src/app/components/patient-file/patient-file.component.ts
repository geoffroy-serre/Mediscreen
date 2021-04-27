import {Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {ActivatedRoute} from "@angular/router";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-patient-file',
  templateUrl: './patient-file.component.html',
  styleUrls: ['./patient-file.component.css']
})
export class PatientFileComponent implements OnInit {
  patient!: HttpResponse<Patient>;
  status!: number;
  message!: string;
  idParam!:string;

  constructor(private patientService: PatientService, private route: ActivatedRoute) {
    // @ts-ignore
    this.idParam = this.route.snapshot.paramMap.get('id');
  }


  ngOnInit(): void {
    this.route.queryParams.subscribe(() => {
      this.handlePatientFile();
    })
  }

  private handlePatientFile() {
    this.patientService.getPatient(this.idParam).subscribe(
      data => {
        console.log(data);
        console.log("Data body in ts: ",data.body);
        this.patient = data;
        console.log('PAtient body in ts:',this.patient.body);
      },
      error => {
        this.status = error.status;
        this.message = error.error.error;
      }
    )
  }
}
