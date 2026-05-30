package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.TodoDto;
import com.example.demo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TodoDto>>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(ApiResponse.success(todos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoDto>> getTodoById(@PathVariable Long id) {
        TodoDto todo = todoService.getTodoById(id);
        return ResponseEntity.ok(ApiResponse.success(todo));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TodoDto>> createTodo(
            @Valid @RequestBody TodoDto request) {
        TodoDto todo = todoService.createTodo(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Todo created successfully", todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoDto>> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDto request) {
        TodoDto todo = todoService.updateTodo(id, request);
        return ResponseEntity.ok(ApiResponse.success("Todo updated successfully", todo));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<TodoDto>> toggleTodo(@PathVariable Long id) {
        TodoDto todo = todoService.toggleTodo(id);
        return ResponseEntity.ok(ApiResponse.success("Todo toggled", todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok(ApiResponse.success("Todo deleted successfully", null));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getStats() {
        Map<String, Long> stats = todoService.getStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}