package com.codecool.men.controller;

import com.codecool.men.controller.dto.note.NewNoteDTO;
import com.codecool.men.controller.dto.note.NoteDTO;
import com.codecool.men.repository.model.Note;
import com.codecool.men.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// userId nems zükséges mivel a jwt token-ből ki lehet szedni az id-t

@RestController
@RequestMapping("/api/note/{userId}")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteDTO> getAllNotes(@PathVariable int userId) {
        return noteService.getAllNotes(userId);
    }

    @GetMapping("/{noteId}")
    public NoteDTO getNote(@PathVariable int userId, @PathVariable int noteId) {
        return noteService.getNote(userId, noteId);
    }
    @GetMapping("/today")
    public List<NoteDTO> getTodaysNotes(@PathVariable long userId){
        return noteService.getNotesOfToday(userId);
    }

    @DeleteMapping("/{noteId}")
    public boolean deleteNote(@PathVariable long userId, @PathVariable int noteId) {
        return noteService.deleteNote(userId, noteId);
    }
    @PutMapping("/{noteId}")
    public boolean updateNote(@PathVariable long userId, @PathVariable int noteId, @RequestBody NoteDTO noteDTO){
        return noteService.updateNote(userId,noteId,noteDTO);
    }

    @PostMapping
    public boolean addNote( @RequestBody NewNoteDTO newNoteDTO) {
        return noteService.addNote(newNoteDTO);
    }


}
