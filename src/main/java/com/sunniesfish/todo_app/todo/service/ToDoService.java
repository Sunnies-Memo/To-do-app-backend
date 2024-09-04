package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.todo.dto.BoardsDTO;
import com.sunniesfish.todo_app.todo.entity.Board;
import com.sunniesfish.todo_app.todo.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ToDoService {

    private final BoardCRUDService boardCRUDService;
    private final ToDoCRUDService toDoCRUDService;

    @Transactional
    public List<BoardsDTO> getAllBoards(long memberId){
        List<BoardsDTO> list = new ArrayList<>();
        boardCRUDService.getBoardsByMemberId(memberId).forEach(board -> {
            BoardsDTO boardsDTO = new BoardsDTO();
            boardsDTO.setBoardId(board.getBoardId());
            boardsDTO.setTitle(board.getTitle());
            boardsDTO.setToDoList(
                    toDoCRUDService.getAllToDosByBoardId(board.getBoardId())
            );
        });
        return list;
    }

    public void deleteBoardById(long boardId){
        toDoCRUDService.deleteAllToDoByBoardId(boardId);
        boardCRUDService.delete(boardId);
    }

}
