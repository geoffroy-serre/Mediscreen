package com.mediscreen.notes.model;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

@Document(collection = "Notes")
public class Note {

  @Id
  private String id;

  @NotNull
  @Field(value = "patient_id")
  private Long patientId;

  @NotBlank
  @Size(min=2,max=60)
  @Field(value = "title")
  private String title;

  @NotNull
  @Field(value = "date")
  private LocalDate date;

  @NotBlank
  @Size(min=2)
  @Field(value = "note")
  private String note;

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "id=" + this.id +
            ", patientId=" + this.patientId +
            ", date=" + this.date +
            ", title=" + this.title +
            ", note='" + this.note + '\'' +
            '}';
  }
}
