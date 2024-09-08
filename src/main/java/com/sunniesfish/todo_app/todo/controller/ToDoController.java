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
            long memberId = 1L;
            if(memberId==board.getMemberId()){
                return new ResponseEntity<>(boardCRUDService.create(board),HttpStatus.CREATED);
            }  else  {
                return new ResponseEntity<>(HttpStatus.LOCKED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity updateBoard(@RequestBody BoardUpdateRequest boardUpdateRequest) {
        System.out.println("updateBoard");
        try {
            long memberId = 1L;
            System.out.println("board memberId: "+boardUpdateRequest.getBoard().getMemberId());
            System.out.println("board order index: "+boardUpdateRequest.getBoard().getOrderIndex());
            if(memberId==boardUpdateRequest.getBoard().getMemberId()){


                return new ResponseEntity<>(
                        toDoService.updateBoard(memberId, boardUpdateRequest),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(HttpStatus.LOCKED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteBoard(@RequestBody Board board) {
        try {
            long memberId = 1L;
            if(memberId==board.getMemberId()){
                System.out.println("delete board");
                toDoService.deleteBoardById(board.getBoardId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.LOCKED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/todo")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        try {
            long memberId = 1L;
            //memberId 존재 여부 확인 로직
            return new ResponseEntity<>(toDoCRUDService.create(toDo),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/todo")
    public ResponseEntity<ToDo> moveToDo(@RequestBody ToDoUpdateRequest toDoUpdateRequest) {
        try {
            long memberId = 1L;
            System.out.println("todo boardId : "+toDoUpdateRequest.getTodo().getBoard().getBoardId());

            return new ResponseEntity<>(
                    toDoService.updateToDo(memberId, toDoUpdateRequest),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo) {
        try {
            long memberId = 1L;
            //memberId 존재 여부 확인 로직
            toDoCRUDService.delete(toDo.getTodoId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
