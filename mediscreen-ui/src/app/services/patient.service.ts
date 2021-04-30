import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Patient} from '../common/patient';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = 'http://localhost:8081';

  constructor(private httpClient: HttpClient) {
  }

  getPatients(): Observable<HttpResponse<Patient[]>> {
    return this.httpClient.get<Patient[]>(this.baseUrl + '/patients', {observe: 'response'})
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  getPatient(id: string): Observable<HttpResponse<Patient>> {
    const patientFileUrl = this.baseUrl + '/patient/file?id=' + id;
    return this.httpClient.get<Patient>(patientFileUrl, {observe: 'response'})
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  addPatient(patient: Patient): Observable<any> {
    const params = new HttpParams()
      .set('family', patient.familyName)
      .set('given', patient.givenName)
      .set('dob', patient.dateOfBirth.toString())
      .set('address', patient.address)
      .set('sex', patient.gender)
      .set('phone', patient.phoneNumber);
    return this.httpClient.post<Patient>(this.baseUrl + '/patient/add', params, {observe: 'response'})
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  deletePatient(id: string) {
    return this.httpClient.delete<Patient>(this.baseUrl + '/patient/delete?id=' + id, {observe: 'response'})
      .pipe(
        catchError(err => {
          console.log(err);
          return throwError(err);
        })
      );
  }

  searchPatient(family: string, given: string): Observable<HttpResponse<Patient[]>> {
    const params = new HttpParams()
      .set('familyName', family)
      .set('givenName', given);
    const searchUrl = this.baseUrl + '/patient/search?' + params;
    return this.httpClient.get<Patient[]>(searchUrl, {observe: 'response'})
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

}


