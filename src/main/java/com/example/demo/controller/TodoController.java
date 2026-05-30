package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.repository.*;
import com.example.demo.modals.*;
@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")   // Allow all origins for development
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    // GET all todos
    @GetMapping
    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    // GET single todo
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable Long id) {
        return todoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create todo
    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody Todo todo) {
        Todo saved = todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT update todo
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody Todo updated) {
        return todoRepository.findById(id).map(todo -> {
            todo.setTitle(updated.getTitle());
            todo.setDescription(updated.getDescription());
            todo.setCompleted(updated.isCompleted());
            return ResponseEntity.ok(todoRepository.save(todo));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH toggle completed
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggle(@PathVariable Long id) {
        return todoRepository.findById(id).map(todo -> {
            todo.setCompleted(!todo.isCompleted());
            return ResponseEntity.ok(todoRepository.save(todo));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!todoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}