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


const routes: Routes = [

  {path: 'appointment/list', component: AppointementsCalendarComponent},
  {path: 'patients', component: PatientsListComponent},
  {path: 'home', component: HomeComponent},
  {path: '', redirectTo:'/home', pathMatch: 'full'},
  {path: 'error', component: NotFoundComponent},
  {path:'**', component: NotFoundComponent}
]


@NgModule({
  declarations: [
    AppComponent,
    PatientsListComponent,
    NotFoundComponent,
    HomeComponent,
    AppointementsCalendarComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule
  ],
  providers: [PatientService],
  bootstrap: [AppComponent]
})
export class AppModule { }
