package com.codecool.men.service;

import com.codecool.men.controller.dto.note.NoteDTO;
import com.codecool.men.controller.exceptions.OperationFailedException;
import com.codecool.men.controller.exceptions.UserNotFoundException;
import com.codecool.men.repository.NoteRepository;
import com.codecool.men.controller.dto.note.NewNoteDTO;
import com.codecool.men.repository.UserRepository;
import com.codecool.men.repository.model.Note;
import com.codecool.men.repository.model.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {
  private final NoteRepository noteRepository;
  private final UserRepository userRepository;

  public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
    this.noteRepository = noteRepository;
    this.userRepository = userRepository;
  }

  public List<NoteDTO> getAllNotes(long noteId) {
    List<Note> notes = noteRepository.findByUserEntityId(noteId);
    return notes.stream().map(note -> new NoteDTO(note.getId(), note.getTitle(), note.getText(), note.isFavorite())).collect(Collectors.toList());
  }
  public List<NoteDTO> getNotesOfToday(long noteId){
    List<Note> notes = noteRepository.findByUserEntityId(noteId);
    notes = notes.stream().filter(Note::isTodaysNote).toList();
    return notes.stream().map(note -> new NoteDTO(note.getId(), note.getTitle(), note.getText(), note.isFavorite())).collect(Collectors.toList());
  }

  public NoteDTO getNote(long userId, long noteId) {
    Note note = noteRepository.findByIdAndUserEntityId(noteId, userId);
    if (note == null) {
      throw new OperationFailedException("Note not found!");
    }
    return new NoteDTO(note.getId(), note.getTitle(), note.getText(), note.isFavorite());
  }

  public boolean deleteNote(long userId, long noteId) {
    Note note = noteRepository.findByIdAndUserEntityId(noteId, userId);
    if (note != null) {
      noteRepository.delete(note);
      return true;
    }
    throw new OperationFailedException("Note not found!");
  }

  public boolean addNote(NewNoteDTO newNoteDTO) {
    UserEntity user = getUser(newNoteDTO.userId());
    Note note = new Note();
    createNote(newNoteDTO, user, note);
    return true;
  }

  private UserEntity getUser(long id) {
    return userRepository
            .findById(id)
            .orElseThrow(UserNotFoundException::new);
  }

  private void createNote(NewNoteDTO newNoteDTO, UserEntity user, Note note) {
    note.setTitle(newNoteDTO.title());
    note.setText(newNoteDTO.text());
    System.out.println(newNoteDTO.date());
    note.setDate(newNoteDTO.date());
    note.setUser(user);
    note.setFavorite(false);
    noteRepository.save(note);
  }

  public boolean updateNote(long userId, long noteId, NoteDTO noteDTO){
    UserEntity user = getUser(userId);
    Note noteToUpdate = user.getNotes().stream().filter(note -> note.getId() == noteId).toList().getFirst();
    noteToUpdate.setTitle(noteDTO.title());
    noteToUpdate.setText(noteToUpdate.getText());
    noteToUpdate.setFavorite(noteDTO.isFavorite());
    noteRepository.save(noteToUpdate);
    return true;
  }
}
