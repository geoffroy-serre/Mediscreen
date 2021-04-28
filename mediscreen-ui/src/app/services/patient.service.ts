import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Patient} from "../common/patient";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = 'http://localhost:8081';

  constructor(private httpClient: HttpClient) {
  }

  getPatients(): Observable<HttpResponse<Patient[]>> {
    return this.httpClient.get<Patient[]>(this.baseUrl + "/patients", {observe: 'response'})
      .pipe(
        catchError(err => {
          console.log(err);
          return throwError(err)
        })
      )
  }

  getPatient(id: string): Observable<HttpResponse<Patient>> {
    const patientFileUrl = this.baseUrl+'/patient/file?id='+id;
    console.log(patientFileUrl)
    return this.httpClient.get<Patient>(patientFileUrl, {observe: 'response'})
      .pipe(
        catchError(err => {
          console.log(err);
          return throwError(err)
        })
      )
  }

}


