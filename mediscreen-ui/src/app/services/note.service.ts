import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {Note} from "../common/note";

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private baseUrl = 'http://localhost:8082'

  constructor(private httpClient: HttpClient) {
  }


  getNotes(): Observable<Note[]> {
    console.log(this.httpClient.get<Note[]>(this.baseUrl + '/notes'));
    return this.httpClient.get<Note[]>(this.baseUrl + '/notes')
      .pipe(catchError(this.handleError));
  }

  getNote(id: string): Observable<Note> {
    const patientFileUrl = this.baseUrl + '/note?id=' + id;
    return this.httpClient.get<Note>(patientFileUrl)
      .pipe(catchError(this.handleError));
  }

  getNotesForPatientID(id: string): Observable<Note[]> {
    const patientFileUrl = this.baseUrl + '/notes/patient?id=' + id;
    return this.httpClient.get<Note[]>(patientFileUrl)
      .pipe(catchError(this.handleError));
  }

  addNote(note: Note): Observable<void> {
    console.log(note);
    const addUrl = this.baseUrl + '/note/add';
    return this.httpClient.post<void>(addUrl,note, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    })
      .pipe(catchError(this.handleError));
  }

  deleteNote(id: string) {
    return this.httpClient.delete<Note>(this.baseUrl + '/patient/delete?id=' + id, {observe: 'response'})
      .pipe(catchError(this.handleError));
  }

  updateNote(note: Note): Observable<void> {
    console.error(note.toString());
    return this.httpClient.put<void>(this.baseUrl + '/patient/update', note,
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

