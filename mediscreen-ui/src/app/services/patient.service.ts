import {Injectable} from '@angular/core';
import axios from "axios";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = 'http://localhost:8081';

  constructor() {
  }

  getPatients() {
    const patientsUrl = this.baseUrl + "/patients";
    return axios.get(patientsUrl);
  }

  getPatient(id: string) {
    const patientFileUrl = this.baseUrl + '/patient/file?id=' + id;
    return axios.get(patientFileUrl);
  }

}


