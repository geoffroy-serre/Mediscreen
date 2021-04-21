import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Patient} from "../common/patient";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = 'http://localhost:8081/patients';

  constructor(private httpClient: HttpClient) {
  }

  getPatients(): Observable<Patient[]> {
    return this.httpClient.get<Patient[]>(this.baseUrl);
  }
}

  interface GetResponse {
 patient:Patient[];
}

