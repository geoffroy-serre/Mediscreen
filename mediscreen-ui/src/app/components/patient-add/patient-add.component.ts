import {Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.css']
})
export class PatientAddComponent implements OnInit {
  status!: number;
  message!: string;
  error!: string;
  addUserForm!: FormGroup;
  maxdate = new Date();

  constructor(private patientService: PatientService, private router: Router, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.addUserForm = this.formBuilder.group({
      family: ['', [Validators.required, Validators.minLength(2)]],
      given: ['', [Validators.required, Validators.minLength(2)]],
      birthdate: ['', [Validators.required, Validators.max(this.maxdate.getUTCFullYear())]],
      gender: ['', Validators.required],
      address: [],
      phone: []
    });
  }

  onSubmit() {
    if (this.addUserForm.invalid) {
      return;
    }
    const patient: Patient = new Patient(this.addUserForm.value.family,
      this.addUserForm.value.given,
      this.addUserForm.value.birthdate,
      this.addUserForm.value.gender,
      this.addUserForm.value.address,
      this.addUserForm.value.phone
    );

    this.patientService.addPatient(patient).subscribe(
      () => this.router.navigate(['/patients']),
      (err: any) => {
        this.status = err.status;
        this.message = err.message;
      }
    );
  }

  get validator() {
    return this.addUserForm.controls;
  }


}
