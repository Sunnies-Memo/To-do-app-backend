package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.global.service.CRUDService;
import com.sunniesfish.todo_app.todo.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardCRUDService extends CRUDService <Board, Long>{
    List<Board> getBoardsByUsername(String username);
    Optional<Board> getLastBoardByUsername(String username);
    void deleteAllBoardsByUsername(String username);
}
