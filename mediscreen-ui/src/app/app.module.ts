import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PatientsListComponent } from './components/patients-list/patients-list.component';
import {HttpClientModule} from "@angular/common/http";
import {PatientService} from "./services/patient.service";

@NgModule({
  declarations: [
    AppComponent,
    PatientsListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [PatientService],
  bootstrap: [AppComponent]
})
export class AppModule { }
