package com.mediscreen.notes.repository;

import com.mediscreen.notes.model.Note;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note,Long> {

  boolean deleteNoteById(String id);
  List<Note> findNoteByPatientId(Long id);
  List<Note> findByPatientIdAndDate(Long id, LocalDate date);
  boolean deleteById(String id);
  boolean existsById(String id);
  Optional<Note> findById(String id);
}