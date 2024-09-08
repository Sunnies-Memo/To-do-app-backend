package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.todo.dto.BoardUpdateRequest;
import com.sunniesfish.todo_app.todo.dto.BoardsDTO;
import com.sunniesfish.todo_app.todo.dto.ToDoUpdateRequest;
import com.sunniesfish.todo_app.todo.entity.Board;
import com.sunniesfish.todo_app.todo.entity.ToDo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ToDoService {

    private final BoardCRUDService boardCRUDService;
    private final ToDoCRUDService toDoCRUDService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<BoardsDTO> getAllBoards(long memberId){
        List<BoardsDTO> list = new ArrayList<>();
        boardCRUDService.getBoardsByMemberId(memberId).forEach(board -> {
            BoardsDTO boardsDTO = new BoardsDTO();
            boardsDTO.setBoardId(board.getBoardId());
            boardsDTO.setTitle(board.getTitle());
            boardsDTO.setMemberId(board.getMemberId());
            boardsDTO.setOrderIndex(board.getOrderIndex());
            boardsDTO.setToDoList(
                    toDoCRUDService.getAllToDosByBoardId(board.getBoardId())
            );
            list.add(boardsDTO);
        });
        return list;
    }

    @Transactional
    public void deleteBoardById(long boardId){
        System.out.println("Delete Board by id: " + boardId);
        toDoCRUDService.deleteAllToDoByBoardId(boardId);
        System.out.println("deleting ToDoS");
        boardCRUDService.delete(boardId);
        System.out.println("deleting Board");
    }


    @Transactional
    public Board updateBoard(long memberId, BoardUpdateRequest boardUpdateRequest){
        Board board = boardCRUDService.update(
                boardUpdateRequest.getBoard().getBoardId(), boardUpdateRequest.getBoard()
        );
        if(boardUpdateRequest.getGap() <= 2) {
            rebalanceBoardsIndex(memberId);
        }
        return board;
    }

    @Transactional
    public ToDo updateToDo(long memberId, ToDoUpdateRequest toDoUpdateRequest){
        ToDo todo = toDoCRUDService.update(
                toDoUpdateRequest.getTodo().getTodoId(), toDoUpdateRequest.getTodo()
        );
        if(toDoUpdateRequest.getGap() <= 2) {
            rebalanceToDosIndex(toDoUpdateRequest.getTodo().getBoard().getBoardId());
        }
        return todo;
    }



    @Transactional
    public void rebalanceBoardsIndex(long memberId){
        List<Board> boards = boardCRUDService.getBoardsByMemberId(memberId);
        for(int i = 1; i <= boards.size(); i++){
            Board board = boards.get(i - 1);
            board.setOrderIndex(i * 40L);
            boardCRUDService.update(board.getBoardId(), board);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void rebalanceToDosIndex(long boardId){
        List<ToDo> todos = toDoCRUDService.getAllToDosByBoardId(boardId);
        for(int i = 1; i <= todos.size(); i++){
            ToDo todo = todos.get(i - 1);
            todo.setOrderIndex(i * 40L);
            toDoCRUDService.update(todo.getTodoId(),todo);
        }
    }
}
