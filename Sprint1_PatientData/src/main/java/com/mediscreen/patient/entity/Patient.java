package com.mediscreen.patient.entity;


import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Patient {

  @NotBlank
  @Column(name = "familly_name")
  @Size(min = 2, max = 60, message = "Family '${validatedValue}' must be between {min} and {max} " +
          "characters long")
  String familyName;
  @NotBlank
  @Column(name = "given_name")
  @Size(min = 2, max = 60, message = "Given '${validatedValue}' must be between {min} and {max} " +
          "characters long")
  String givenName;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "date_of_birth")
  LocalDate dateOfBirth;
  @NotNull
  @Column(name = "gender")
  Character gender;
  @Column(name = "address")
  @Size(max = 100, message = "Address '${validatedValue}' must be between {min} and {max}" +
          " " +
          "characters long")
  String address;
  @Column(name = "phone_number")
  @Size(max = 20, message = "Phone '${validatedValue}' must be between {min} and {max} " +
          "characters long")
  String phoneNumber;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;


  public Patient() {
  }

  public Patient(String familyName, String givenName, LocalDate dateOfBirth, Character gender,
                 String address, String phoneNumber) {
    this.familyName = familyName;
    this.givenName = givenName;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Character getGender() {
    return gender;
  }

  public void setGender(Character gender) {
    this.gender = gender;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "Patient{" +
            "id=" + id +
            ", familyName='" + familyName + '\'' +
            ", givenName='" + givenName + '\'' +
            ", dateOfBirth=" + dateOfBirth +
            ", gender=" + gender +
            ", address='" + address + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            '}';
  }
}
