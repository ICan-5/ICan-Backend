package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.config.annotation.LoginUser;
import com.codeit.team5.ican.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteService noteService;

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @LoginUser Long userId,
            @PathVariable Long noteId
    ) {
        noteService.deleteNote(userId, noteId);
        return ResponseEntity.noContent().build();
    }
}
