import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-patient-search',
  templateUrl: './patient-search.component.html',
  styleUrls: ['./patient-search.component.css']
})
export class PatientSearchComponent implements OnInit {

  status!: number;
  message!: string;
  error!: string;
  searchForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router) {
  }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      family: ['', Validators.required],
      given: ['', Validators.required]
    })
  }

  get validator() {
    return this.searchForm.controls;
  }

  onSubmit() {

    if (this.searchForm.invalid) {
      console.log(this.searchForm.invalid);
      return;
    }
    console.log(this.searchForm.value.family);
    this.router.navigateByUrl(`patient/search/${this.searchForm.value.family}/${this.searchForm.value.given}`);
  }

}
