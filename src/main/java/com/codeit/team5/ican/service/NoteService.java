package com.codeit.team5.ican.service;

import com.codeit.team5.ican.domain.Todo;
import com.codeit.team5.ican.exception.NoteNotFoundException;
import com.codeit.team5.ican.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final TodoRepository todoRepository;

    @Transactional
    public void deleteNote(Long userId, Long noteId) {
        List<Todo> todos = todoRepository.findAllByUserIdAndNoteId(userId, noteId);

        if(todos.isEmpty()) {
            throw new NoteNotFoundException("노트 아이디 " + noteId + "를 찾을 수 없습니다.");
        }

        todos.forEach(Todo::deleteNote);
    }
}
