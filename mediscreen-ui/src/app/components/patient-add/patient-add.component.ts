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
patient!:Patient;
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
      return;
    }
    this.mapFormValuesToPatient();
    this.patientService.addPatient(this.patient).subscribe(
      () => this.router.navigate(['/patients']),
      (err: any) => {
        console.log(err)
        this.status = err.status;
        this.message = err.message;
      }
    );
  }
    mapFormValuesToPatient() {
      this.patient.id = this.addUserForm.value.family;
      this.patient.familyName = this.addUserForm.value.family;
      this.patient.givenName = this.addUserForm.value.given;
      this.patient.address = this.addUserForm.value.address;
      this.patient.gender = this.addUserForm.value.gender;
      this.patient.dateOfBirth = this.addUserForm.value.birthdate;
      this.patient.phoneNumber = this.addUserForm.value.phone;
    }

  get validator() {
    console.log(this.addUserForm.controls);
    return this.addUserForm.controls;
  }


}
