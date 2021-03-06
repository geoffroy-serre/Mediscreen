import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Patient} from '../common/patient';
import {catchError} from 'rxjs/operators';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = environment.apiUrlPatient;

  constructor(private httpClient: HttpClient) {
  }

  getPatients(): Observable<Patient[]> {
    return this.httpClient.get<Patient[]>(this.baseUrl + '/patients')
      .pipe(catchError(this.handleError));
  }

  getPatient(id: string): Observable<Patient> {
    const patientFileUrl = this.baseUrl + '/patient/file?id=' + id;
    return this.httpClient.get<Patient>(patientFileUrl)
      .pipe(catchError(this.handleError));
  }

  addPatient(patient: Patient): Observable<Patient> {
    if (patient.address == null) {
      patient.address ="";
    }
    if (patient.phoneNumber == null) {
      patient.phoneNumber="";
    }
    const params = new HttpParams()
      .set('family', patient.familyName)
      .set('given', patient.givenName)
      .set('dob', patient.dateOfBirth.toString())
      .set('sex', patient.gender)
  .set('address', patient.address)
      .set('phone', patient.phoneNumber)

    const addUrl = this.baseUrl + '/patient/add?' + params;
    return this.httpClient.post<Patient>(addUrl, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    })
      .pipe(catchError(this.handleError));
  }

  deletePatient(id: string) {
    return this.httpClient.delete<Patient>(this.baseUrl + '/patient/delete?id=' + id, {observe: 'response'})
      .pipe(catchError(this.handleError));
  }

  searchPatient(family: string, given: string): Observable<Patient[]> {
    const params = new HttpParams()
      .set('familyName', family)
      .set('givenName', given);
    const searchUrl = this.baseUrl + '/patient/search?' + params;
    return this.httpClient.get<Patient[]>(searchUrl)
      .pipe(catchError(this.handleError));
  }

  updatePatient(patient: Patient): Observable<void> {
    return this.httpClient.put<void>(this.baseUrl + '/patient/update', patient,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      })
      .pipe(catchError(this.handleError));

  }

  private handleError(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse);
  }
}


