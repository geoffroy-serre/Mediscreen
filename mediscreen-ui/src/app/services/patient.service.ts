import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Patient} from "../common/patient";
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PatientService {


  private baseUrl = 'http://localhost:8081/patients';

  constructor(private httpClient: HttpClient) {
  }

  getPatients(): Observable<HttpResponse<Patient[]>> {
    return this.httpClient.get<Patient[]>(this.baseUrl,{observe:'response'})
      .pipe(
        catchError(err => {
          console.log(err);
          return throwError(err)
        })
      )
  }


}

  interface GetResponse {
 patient:Patient[];
}

