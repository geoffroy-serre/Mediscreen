import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {Note} from "../common/note";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {EstimationResult} from "../common/EstimationResult";

@Injectable({
  providedIn: 'root'
})
export class DiabetesEstimationService {

  constructor(private httpClient: HttpClient) {
  }

  baseUrl: String = "http://localhost:8083"

  public getEstimation(gender: string, birthdate: Date, notes: String[]): Observable<EstimationResult> {
    const params = new HttpParams()
      .set('gender', gender)
      .set('birthdate', birthdate?.toString())
      .set('notes', notes?.toString())
    const addUrl = this.baseUrl + '/estimation?' + params;
    return this.httpClient.get<EstimationResult>(addUrl, {
      headers: new HttpHeaders({
        'Content-Type': 'plain/text'
      })
    })
      .pipe(catchError(this.handleError));
  }

  private handleError(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse);
  }
}
