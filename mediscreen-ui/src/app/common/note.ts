export class Note {

  id!: string;
  patientId!: number;
  date!: Date;
  title!: string;
  note!: string;


  constructor(patientId: number, date: Date, title: string, note: string) {
    this.patientId = patientId;
    this.date = date;
    this.title = title;
    this.note = note;
  }
}
