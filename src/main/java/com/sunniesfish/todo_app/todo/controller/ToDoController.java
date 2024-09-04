package com.sunniesfish.todo_app.todo.controller;

import com.sunniesfish.todo_app.todo.dto.BoardsDTO;
import com.sunniesfish.todo_app.todo.entity.Board;
import com.sunniesfish.todo_app.todo.entity.ToDo;
import com.sunniesfish.todo_app.todo.service.BoardCRUDService;
import com.sunniesfish.todo_app.todo.service.ToDoCRUDService;
import com.sunniesfish.todo_app.todo.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@AllArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;
    private final BoardCRUDService boardCRUDService;
    private final ToDoCRUDService toDoCRUDService;


    @GetMapping("")
    public ResponseEntity getAll() {
        try {
            long memberId = 1L;
            List<BoardsDTO> boardsDTOList = toDoService.getAllBoards(memberId);
            return new ResponseEntity<>(boardsDTOList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("")
    public ResponseEntity createBoard(@RequestBody Board board) {
        try {
            boardCRUDService.create(board);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity updateBoard(@RequestBody Board board) {
        return null;
    }

    @DeleteMapping("")
    public ResponseEntity deleteBoard(@RequestBody Board board) {
        try {
            toDoService.deleteBoardById(board.getBoardId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/todo")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        try {
            toDoCRUDService.create(toDo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/todo")
    public ResponseEntity<ToDo> updateToDo(@RequestBody ToDo toDo) {
        return null;
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo) {
        try {
            toDoCRUDService.delete(toDo.getTodoId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
