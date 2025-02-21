package com.codeit.team5.ican.controller;

import com.codeit.team5.ican.controller.dto.todo.TodoCreateRequest;
import com.codeit.team5.ican.controller.dto.todo.TodoDTO;
import com.codeit.team5.ican.controller.dto.todo.TodoUpdateRequest;
import com.codeit.team5.ican.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(
            HttpServletRequest request,
            @RequestBody TodoCreateRequest todoCreateRequest
    ) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                TodoDTO.from(todoService.createTodo(userId, todoCreateRequest))
        );
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoDTO> updateTodo(
            HttpServletRequest request,
            @PathVariable Long todoId,
            @RequestBody TodoUpdateRequest todoUpdateRequest
    ) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok().body(
                TodoDTO.from(todoService.updateTodo(userId, todoId, todoUpdateRequest))
        );
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            HttpServletRequest request,
            @PathVariable Long todoId
    ) {
        Long userId = (Long) request.getAttribute("userId");
        todoService.deleteTodo(userId, todoId);
        return ResponseEntity.noContent().build();
    }

}
