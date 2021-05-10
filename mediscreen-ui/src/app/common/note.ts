export class Note {

  id!: string;
  patientId!: number;
  date!: Date;
 note!:string;


  constructor(patientId: number, date: Date, note: string) {
    this.patientId = patientId;
    this.date = date;
    this.note = note;
  }
}
