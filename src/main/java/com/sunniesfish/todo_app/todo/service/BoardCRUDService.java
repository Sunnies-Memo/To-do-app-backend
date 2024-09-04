package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.global.service.CRUDService;
import com.sunniesfish.todo_app.todo.entity.Board;

import java.util.List;

public interface BoardCRUDService extends CRUDService <Board, Long>{
    List<Board> getBoardsByMemberId(Long memberId);
    void deleteAllBoardsByMemberId(Long memberId);
}
