package com.sunniesfish.todo_app.todo.controller;

import com.sunniesfish.todo_app.todo.entity.ToDo;
import com.sunniesfish.todo_app.todo.service.BoardCRUDService;
import com.sunniesfish.todo_app.todo.service.ToDoCRUDService;
import com.sunniesfish.todo_app.todo.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
@AllArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;
    private final BoardCRUDService boardCRUDService;
    private final ToDoCRUDService toDoCRUDService;

    @GetMapping("")
    public ResponseEntity<List<ToDo>> getAll() {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<ToDo> create(@RequestBody ToDo toDo) {
        return null;
    }

    @PutMapping("")
    public ResponseEntity<ToDo> update(@RequestBody ToDo toDo) {
        return null;
    }

    @DeleteMapping("")
    public ResponseEntity<ToDo> delete(@RequestBody ToDo toDo) {
        return null;
    }
}
