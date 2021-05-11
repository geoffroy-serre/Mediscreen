import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NoteService} from "../../services/note.service";
import{Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Patient} from "../../common/patient";
import{PatientService} from "../../services/patient.service";
import {Note} from "../../common/note";

@Component({
  selector: 'app-note-add',
  templateUrl: './note-add.component.html',
  styleUrls: ['./note-add.component.css']
})
export class NoteAddComponent implements OnInit {
  private idParam!: string;
  addNoteForm!: FormGroup;
  patient!:Patient;
  status!: number;
  message!: string;
  note!:string;

  constructor(private patientService:PatientService,private noteService: NoteService, private route: ActivatedRoute, private router: Router,private formBuilder: FormBuilder) {
    this.idParam = this.route.snapshot.paramMap.get('id') || '0';
    this.patientFile();
  }

  ngOnInit(): void {
    this.addNoteForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(2)]],
      note: ['', [Validators.required, Validators.minLength(2)]],
    });

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

  onSubmit() {
    if (this.addNoteForm.invalid) {
      return;
    }
    const note = new Note(+this.idParam,new Date(),this.addNoteForm.value.title,this.addNoteForm.value.note)
    console.log(this.note);

    this.noteService.addNote(note).subscribe(
      () => this.router.navigate(['/patient/file',this.idParam]),
      (err: any) => {
        console.log(err)
        this.status = err.status;
        this.message = err.message;
      }
    );

  }

  get validator() {
    return this.addNoteForm.controls;
  }

}
