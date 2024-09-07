package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.global.service.CRUDService;
import com.sunniesfish.todo_app.todo.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardCRUDService extends CRUDService <Board, Long>{
    List<Board> getBoardsByMemberId(Long memberId);
    Optional<Board> getLastBoardByMemberId(Long memberId);
    void deleteAllBoardsByMemberId(Long memberId);
}
