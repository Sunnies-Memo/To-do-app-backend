package com.sunniesfish.todo_app.todo.controller;

import com.sunniesfish.todo_app.todo.dto.BoardUpdateRequest;
import com.sunniesfish.todo_app.todo.dto.BoardsDTO;
import com.sunniesfish.todo_app.todo.dto.ToDoUpdateRequest;
import com.sunniesfish.todo_app.todo.entity.Board;
import com.sunniesfish.todo_app.todo.entity.ToDo;
import com.sunniesfish.todo_app.todo.service.BoardCRUDService;
import com.sunniesfish.todo_app.todo.service.ToDoCRUDService;
import com.sunniesfish.todo_app.todo.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<BoardsDTO> boardsDTOList = toDoService.getAllBoards(username);
            return new ResponseEntity<>(boardsDTOList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("")
    public ResponseEntity createBoard(@RequestBody Board board) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if(username.equals(board.getUsername())){
                return new ResponseEntity<>(boardCRUDService.create(board),HttpStatus.CREATED);
            }  else  {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("")
    public ResponseEntity updateBoard(@RequestBody BoardUpdateRequest boardUpdateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if(username.equals(boardUpdateRequest.getBoard().getUsername())){
                return new ResponseEntity<>(
                        toDoService.updateBoard(username, boardUpdateRequest),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteBoard(@RequestBody Board board) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if(username.equals(board.getUsername())){
                toDoService.deleteBoardById(board.getBoardId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/todo")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return new ResponseEntity<>(toDoCRUDService.create(toDo),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/todo")
    public ResponseEntity<ToDo> moveToDo(@RequestBody ToDoUpdateRequest toDoUpdateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return new ResponseEntity<>(
                    toDoService.updateToDo(username, toDoUpdateRequest),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            toDoCRUDService.delete(toDo.getTodoId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
