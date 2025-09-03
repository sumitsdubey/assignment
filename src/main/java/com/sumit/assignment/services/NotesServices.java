package com.sumit.assignment.services;

import com.sumit.assignment.dto.ModelMapper;
import com.sumit.assignment.dto.request.NoteRequestDto;
import com.sumit.assignment.entities.Note;
import com.sumit.assignment.entities.User;
import com.sumit.assignment.repository.NoteRepository;
import com.sumit.assignment.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotesServices {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public Note saveNote(NoteRequestDto note, String email) {
        User user = userServices.getUserByEmail(email);
        Note newNote = ModelMapper.toNote(note);
        newNote.setCreatedAt(LocalDateTime.now());
        Note saved = noteRepository.save(newNote);
        List<Note> notes = user.getNotes();
        if (notes != null) {
            notes.add(saved);
        } else {
            notes = List.of(saved);
        }
        user.setNotes(notes);
        userRepository.save(user);
        return saved;
    }

    public Note updateNote(NoteRequestDto note, String noteId, Authentication authentication) {


        Note existingNote = getNoteById(noteId);
        if(existingNote != null) {
            existingNote.setTitle(note.getTitle());
            existingNote.setContent(note.getContent());
            return noteRepository.save(existingNote);
        }
        return null;
    }

    public void deleteNote(String noteId) {
        noteRepository.deleteById(noteId);
    }

    public Note getNoteById(String noteId) {
        return noteRepository.findById(noteId).orElse(null);
    }

    public List<Note> allNotes() {
        return noteRepository.findAll();
    }
}
