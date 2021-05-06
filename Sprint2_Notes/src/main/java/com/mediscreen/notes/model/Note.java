package com.mediscreen.notes.model;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document(collection = "Notes")
public class Note {

  @Id
  private Long id;

  @NotNull
  @Field(value="patient_id")
  private Long patientId;

  @NotNull
  @Field(value="date")
  private LocalDate date;

  @NotBlank
  @Field(value = "note")
  private String note;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getPatientId() {
    return patientId;
  }
  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }
  public LocalDate getDate() {
    return date;
  }
  public void setDate(LocalDate date) {
    this.date = date;
  }
  public String getNote() {
    return note;
  }
  public void setNote(String note) {
    this.note = note;
  }

  @Override
  public String toString() {
    return "id=" + this.id +
            ", patientId=" + this.patientId +
            ", date=" + this.date +
            ", note='" + this.note + '\'' +
            '}';
  }
}
