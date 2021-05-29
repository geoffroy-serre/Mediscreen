import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {Note} from "../common/note";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private baseUrl = environment.apiUrlNotes;

  constructor(private httpClient: HttpClient) {
  }


  getNotes(): Observable<Note[]> {
    console.log(this.httpClient.get<Note[]>(this.baseUrl + '/patHistory'));
    return this.httpClient.get<Note[]>(this.baseUrl + '/patHistory')
      .pipe(catchError(this.handleError));
  }

  getNote(id: string): Observable<Note> {
    const patientFileUrl = this.baseUrl + '/patHistory/note?id=' + id;
    return this.httpClient.get<Note>(patientFileUrl)
      .pipe(catchError(this.handleError));
  }

  getNotesForPatientID(id: string): Observable<Note[]> {
    const patientFileUrl = this.baseUrl + '/patHistory/patient?id=' + id;
    return this.httpClient.get<Note[]>(patientFileUrl)
      .pipe(catchError(this.handleError));
  }

  addNote(note: Note): Observable<void> {
    console.log(note);
    const addUrl = this.baseUrl + '/patHistory/add';
    return this.httpClient.post<void>(addUrl,note, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    })
      .pipe(catchError(this.handleError));
  }

  deleteNote(id: string) {
    const params = new HttpParams()
      .set('id',id);
    return this.httpClient.delete<Note>(this.baseUrl + '/patHistory/delete?' + params, {observe: 'response'})
      .pipe(catchError(this.handleError));
  }
  deleteNotesByPatientID(id: number) {
    return this.httpClient.delete<Note>(this.baseUrl + '/patHistory/patient/delete?id='+id, {observe: 'response'})
      .pipe(catchError(this.handleError));
  }

  updateNote(note: Note): Observable<void> {
    console.error(note.toString());
    return this.httpClient.put<void>(this.baseUrl + '/patHistory/update', note,
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

