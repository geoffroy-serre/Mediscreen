import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NoteService} from "../../services/note.service";
import{Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Patient} from "../../common/patient";
import{PatientService} from "../../services/patient.service";

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
    console.log(this.note);

  }

  get validator() {
    return this.addNoteForm.controls;
  }

}
