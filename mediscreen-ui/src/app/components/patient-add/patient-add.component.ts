import {Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";


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
      gender: ['', Validators.required]
    });

  }


  onSubmit() {
    if (this.addUserForm.invalid) {
      console.log(this.addUserForm.invalid);
      return;
    }
    const patient = new Patient(this.addUserForm.value.family, this.addUserForm.value.given,
      this.addUserForm.value.birthdate, this.addUserForm.value.gender,
      this.addUserForm.value.address, this.addUserForm.value.phone);
    this.patientService.addPatient(patient).subscribe(
      data => {
        this.status = data.status;
      },
      error => {
        this.status = error.status;
        this.message = error.error.error;
      }
    );
    setTimeout(() => this.router.navigate(['/patients']), 10);

  }

  get validator() {
    console.log(this.addUserForm.controls);
    return this.addUserForm.controls;
  }


}
