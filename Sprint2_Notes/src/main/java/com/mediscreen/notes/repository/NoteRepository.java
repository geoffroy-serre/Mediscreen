package com.mediscreen.notes.repository;

import com.mediscreen.notes.model.Note;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note,Long> {

  boolean deleteNoteById(String id);
  List<Note> findByPatientId(String id);
  List<Note> findByPatientIdAndDate(String id, LocalDate date);
  void deleteById(String id);
  boolean existsById(String id);
  Optional<Note> findById(String id);
}
