package com.sumit.assignment.controller;

import com.sumit.assignment.dto.request.NoteRequestDto;
import com.sumit.assignment.entities.Note;
import com.sumit.assignment.services.NotesServices;
import com.sumit.assignment.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {


    @Autowired
    private NotesServices notesServices;

    @Autowired
    private UserServices userServices;

    @PostMapping("/create")
    public ResponseEntity<?> createNote(@RequestBody NoteRequestDto note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(notesServices.saveNote(note, authentication.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNote(@RequestBody NoteRequestDto note, @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Note updated = notesServices.updateNote(note, id, authentication);
        if(updated != null){
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/my-notes")
    public ResponseEntity<?> getAllNotesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Note> notes = userServices.getUserByEmail(authentication.getName()).getNotes();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        Note existingNote = notesServices.getNoteById(id);
        if(existingNote != null){
            notesServices.deleteNote(id);
            return new ResponseEntity<>("Note deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
    }


}
