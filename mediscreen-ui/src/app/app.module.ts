import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PatientsListComponent } from './components/patients-list/patients-list.component';
import {HttpClientModule} from "@angular/common/http";
import {PatientService} from "./services/patient.service";
import {RouterModule, Routes} from "@angular/router";
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomeComponent } from './components/home/home.component';
import { AppointementsCalendarComponent } from './components/appointements-calendar/appointements-calendar.component';
import { PatientFileComponent } from './components/patient-file/patient-file.component';
import { PatientAddComponent } from './components/patient-add/patient-add.component';


const routes: Routes = [
  {path: 'patient/file/:id', component: PatientFileComponent},
  {path: 'patient/add', component: PatientAddComponent},
  {path: 'appointment/list', component: AppointementsCalendarComponent},
  {path: 'patients', component: PatientsListComponent},
  {path: 'home', component: HomeComponent},
  {path: '', component: HomeComponent},
  {path: 'error', component: NotFoundComponent},
  {path:'**', component: NotFoundComponent}
]


@NgModule({
  declarations: [
    AppComponent,
    PatientsListComponent,
    NotFoundComponent,
    HomeComponent,
    AppointementsCalendarComponent,
    PatientFileComponent,
    PatientAddComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule
  ],
  providers: [PatientService],
  bootstrap: [AppComponent]
})
export class AppModule { }
