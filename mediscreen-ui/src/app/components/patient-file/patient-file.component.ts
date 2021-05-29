import {Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NoteService} from "../../services/note.service";
import {Note} from "../../common/note";
import {ModalService} from "../../_modal";
import {DiabetesEstimationService} from "../../services/diabetes-estimation.service";
import {EstimationResult} from "../../common/EstimationResult";

@Component({
  selector: 'app-patient-file',
  templateUrl: './patient-file.component.html',
  styleUrls: ['./patient-file.component.css']
})
export class PatientFileComponent implements OnInit {
  patient!: Patient;
  notes!: Note[];
  sortedNotes!: Note[];
  notesContentOnly: String[] = [];
  status!: number;
  message!: string;
  private readonly idParam!: string;
  diabetesEstimation!: EstimationResult;

  constructor(private modalService: ModalService, private noteService: NoteService, private patientService: PatientService, private route: ActivatedRoute, private router: Router, private estimationService: DiabetesEstimationService) {
    this.idParam = this.route.snapshot.paramMap.get('id') || '0';
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(() => {
      this.patientFile();
      this.getNotes();
    });
    this.waitForData();
  }

  private waitForData() {
    if (this.patient != null && this.notes != null) {
      this.getEstimation();
    } else {
      setTimeout(() => this.waitForData(), 0);
    }
  }

  private patientFile() {
    this.patientService.getPatient(this.idParam).subscribe(
      (patient: Patient) => {
        this.patient = patient;
      },
      (err: any) => {
        this.status = err.status;
        this.message = err.message;
      }
    );
  }

  deletePatient() {
    this.deleteNotesByPatientId(+this.idParam);
    this.patientService.deletePatient(this.idParam).subscribe(
      data => {
        this.status = data.status;
      },
      error => {
        this.status = error.status;
        this.message = error.error.error;
      }
    );
    setTimeout(() => this.router.navigate(['/patients']), 50);
  }

  deleteNote(id: string) {

    this.noteService.deleteNote(id).subscribe(
      data => {
        this.status = data.status;
      },
      error => {
        this.status = error.status;
        this.message = error.error.error;
      }
    );
    this.closeModal(id);
    window.location.reload();
  }

  deleteNotesByPatientId(id: number) {
    this.noteService.deleteNotesByPatientID(id).subscribe(
      data => {
        this.status = data.status;
      },
      error => {
        this.status = error.status;
        this.message = error.error.error;
      }
    );
  }

  private getNotes() {
    this.noteService.getNotesForPatientID(this.idParam).subscribe(
      (notes: Note[]) => {
        this.notes = notes;
        this.sortData();
      },
      (err: any) => {
        this.status = err.status;
      },
    )
  }

  private sortData() {
    return this?.notes?.sort((a: any, b: any) => {
      return Date.parse(b.date) - Date.parse(a.date);
    });
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }

  public getEstimation() {
    this.notes.forEach(note => this.notesContentOnly.push(note.note));
    this.estimationService.getEstimation(this.patient?.gender, this.patient?.dateOfBirth, this.notesContentOnly).subscribe(
      (estimation: EstimationResult) => {
        this.diabetesEstimation =estimation;
      },
      (err: any) => {
        this.status = err.status;
      },
    )
  }
}
