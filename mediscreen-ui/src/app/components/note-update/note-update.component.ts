import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Patient} from "../../common/patient";
import {NoteService} from "../../services/note.service";
import {Note} from "../../common/note";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-note-update',
  templateUrl: './note-update.component.html',
  styleUrls: ['./note-update.component.css']
})
export class NoteUpdateComponent implements OnInit, AfterViewInit {

  idParam!: string;
  note!:Note;
  status!:number;
  message!:string;

  updateNoteForm = this.formBuilder.group({
    title: ['', [Validators.required, Validators.minLength(2)]],
    note: ['', [Validators.required, Validators.minLength(2)]]
  });

  constructor(private router: Router,private noteService: NoteService,private formBuilder: FormBuilder, private route: ActivatedRoute,private cdr: ChangeDetectorRef) {
    this.idParam = this.route.snapshot.paramMap.get('id') || '0';
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(() => {
      this.getNoteForUpdate();
    });
  }

  ngAfterViewInit(): void {
    this.cdr.detectChanges();
  }

  onSubmit() {
    if (this.updateNoteForm.invalid) {
      console.log(this.updateNoteForm.invalid);
      return;
    }
    this.mapFormValuesToNote();
    this.note.id = this.idParam;
    this.noteService.updateNote(this.note).subscribe(
      () => this.router.navigate(['/patient/file/' + this.note.patientId]),
      (err: any) => {
        this.status = err.status;
        this.message = err.message;
      }
    );
  }

  mapFormValuesToNote() {
    this.note.id = this.idParam;
    this.note.title = this.updateNoteForm.value.title;
    this.note.note = this.updateNoteForm.value.note;
    this.note.date = new Date();
  }

  get validator() {
    return this.updateNoteForm.controls;
  }

  private getNoteForUpdate() {
    this.noteService.getNote(this.idParam).subscribe(
      (note: Note) => {
        this.note = note;
        this.editNote(note);
        console.error(note);
      },
      (err: any) => {
        this.status = err.status;
        this.message = err.message;
        console.error(err)
      }
    );
  }

  editNote(note: Note) {
    this.updateNoteForm.patchValue({
      title: note.title,
      note: note.note
    });
  }

}
