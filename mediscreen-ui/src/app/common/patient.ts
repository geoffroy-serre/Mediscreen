export class Patient {

  id!: number;
  familyName!: string;
  givenName!: string;
  dateOfBirth!: Date;
  gender!: string;
  address!: string;
  phoneNumber!: string;


  constructor(familyName: string, givenName: string, dateOfBirth: Date, gender: string, address: string, phoneNumber: string) {
    this.familyName = familyName;
    this.givenName = givenName;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

}
