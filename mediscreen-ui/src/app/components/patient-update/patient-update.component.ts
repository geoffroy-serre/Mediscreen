import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {DatePipe, formatDate} from "@angular/common";


@Component({
  selector: 'app-patient-update',
  templateUrl: './patient-update.component.html',
  styleUrls: ['./patient-update.component.css']
})
export class PatientUpdateComponent implements OnInit, AfterViewInit {
  patient!: Patient;
  status: number = 200;
  message!: string;
  error!: string;
  maxdate = new Date();

  updateUserForm = this.formBuilder.group({
    family: ['', [Validators.required, Validators.minLength(2)]],
    given: ['', [Validators.required, Validators.minLength(2)]],
    birthdate: ['', [Validators.required, Validators.max(this.maxdate.getUTCFullYear())]],
    address: [],
    phone: [],
    gender: []
  });
  private readonly idParam!: string;

  constructor(private datePipe: DatePipe, private cdr: ChangeDetectorRef, private patientService: PatientService, private router: Router, private formBuilder: FormBuilder, private route: ActivatedRoute) {
    this.idParam = this.route.snapshot.paramMap.get('id') || '0';
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(() => {
      this.getPatientForUpdate();
    });


  }

  ngAfterViewInit() {
   // this.populateValues();
    this.cdr.detectChanges();
  }

  onSubmit() {
    if (this.updateUserForm.invalid) {
      console.log(this.updateUserForm.invalid);
      return;
    }
    this.mapFormValuesToPatient();
    this.patient.id = +this.idParam;
    this.patientService.updatePatient(this.patient).subscribe(
      () => this.router.navigate(['/patient/file/' + this.idParam]),
      (err: any) => {
        this.status = err.status;
        this.message = err.message;
      }
    );

  }

  mapFormValuesToPatient() {
    this.patient.id = this.updateUserForm.value.family;
    this.patient.familyName = this.updateUserForm.value.family;
    this.patient.givenName = this.updateUserForm.value.given;
    this.patient.address = this.updateUserForm.value.address;
    this.patient.gender = this.updateUserForm.value.gender;
    this.patient.dateOfBirth = this.updateUserForm.value.birthdate;
    this.patient.phoneNumber = this.updateUserForm.value.phone;
  }

  get validator() {
    return this.updateUserForm.controls;
  }

  private getPatientForUpdate() {
    this.patientService.getPatient(this.idParam).subscribe(
      (patient: Patient) => {
        this.patient = patient;
        this.editPatient(patient);
        console.error(patient);
      },
      (err: any) => {
        this.status = err.status;
        this.message = err.message;
        console.error(err)
      }
    );
  }

  editPatient(patient: Patient) {
    console.log(this.updateUserForm.value.birthdate);
    this.updateUserForm.patchValue({
      family: patient.familyName,
      given: patient.givenName,
      phone: patient.phoneNumber,
      address: patient.address,
      gender: patient.gender,
      birthDate:formatDate(patient.dateOfBirth,'dd-MM-yyyy','en')
    });
    console.log(this.updateUserForm.value.birthdate);
    console.log("DATE IN: " + formatDate(patient.dateOfBirth,'dd-MM-yyyy','en'));
  }

  /*populateValues() {
    this.updateUserForm.get('family')?.setValue(`${this.patient?.familyName}`);
  }*/

}
