import {Component, OnInit} from '@angular/core';
import {Patient} from "../../common/patient";
import {PatientService} from "../../services/patient.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpResponse} from "@angular/common/http";
import {map, timeout} from "rxjs/operators";
import {Observable} from "rxjs";
import {NoteService} from "../../services/note.service";
import {Note} from "../../common/note";
import {ModalService} from "../../_modal";

@Component({
  selector: 'app-patient-file',
  templateUrl: './patient-file.component.html',
  styleUrls: ['./patient-file.component.css']
})
export class PatientFileComponent implements OnInit {
  patient!: Patient;
  notes!: Note[];
  status!: number;
  message!: string;
  private idParam!: string;

  constructor(private modalService: ModalService,private noteService: NoteService,private patientService: PatientService, private route: ActivatedRoute, private router:Router) {
    this.idParam = this.route.snapshot.paramMap.get('id') || '0';
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(() => {
      this.patientFile();

    });
    this.getNotes();
  }

  private patientFile() {
    this.patientService.getPatient(this.idParam).subscribe(
      (patient: Patient) => {
        this.patient = patient;
      },
      (err: any) => {
        this.status = err.status;
        this.message = err.message;
        console.error(err)
      }
    );
  }

  deletePatient(){
      this.patientService.deletePatient(this.idParam).subscribe(
        data => {
          this.status = data.status;
        },
        error => {
          this.status = error.status;
          this.message = error.error.error;
        }
      );
    setTimeout(()=>this.router.navigate(['/patients']),50);
  }

  getNotes(){
    this.noteService.getNotesForPatientID(this.idParam).subscribe(
      (notes: Note[]) => {
        this.notes = notes;
        console.log("NOTES: "+notes.toString());
      },
      (err: any) => {
        this.status = err.status;
        console.error(err)
      },
    )
  }

  openModal(id:string){
    this.modalService.open(id);
  }
  closeModal(id: string) {
    this.modalService.close(id);
  }
}
