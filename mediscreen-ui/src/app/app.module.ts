import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {PatientsListComponent} from './components/patients-list/patients-list.component';
import {HttpClientModule} from "@angular/common/http";
import {PatientService} from "./services/patient.service";
import {RouterModule, Routes} from "@angular/router";
import {NotFoundComponent} from './components/not-found/not-found.component';
import {HomeComponent} from './components/home/home.component';
import {PatientFileComponent} from './components/patient-file/patient-file.component';
import {PatientAddComponent} from './components/patient-add/patient-add.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PatientSearchComponent} from './components/patient-search/patient-search.component';
import {PatientUpdateComponent} from './components/patient-update/patient-update.component';
import {DatePipe} from "@angular/common";
import {NoteAddComponent} from './components/note-add/note-add.component';
import {ModalModule} from "./_modal";
import {NoteUpdateComponent} from './components/note-update/note-update.component';
import {ColorsDirective} from "./common/colorsDirective";


const routes: Routes = [
  {path: 'note/update/:id', component: NoteUpdateComponent},
  {path: 'note/add/:id', component: NoteAddComponent},
  {path: 'patient/update/:id', component: PatientUpdateComponent},
  {path: 'patient/file/:id', component: PatientFileComponent},
  {path: 'patient/search/:family/:given', component: PatientsListComponent},
  {path: 'patient/add', component: PatientAddComponent},
  {path: 'patients', component: PatientsListComponent},
  {path: 'home', component: HomeComponent},
  {path: '', component: HomeComponent},
  {path: 'error', component: NotFoundComponent},
  {path: '**', component: NotFoundComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    PatientsListComponent,
    NotFoundComponent,
    HomeComponent,
    PatientFileComponent,
    PatientAddComponent,
    PatientSearchComponent,
    PatientUpdateComponent,
    NoteAddComponent,
    NoteUpdateComponent,
    ColorsDirective
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule
  ],
  providers: [PatientService, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
