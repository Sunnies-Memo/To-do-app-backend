package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.global.service.CRUDService;
import com.sunniesfish.todo_app.todo.entity.ToDo;

import java.util.List;

public interface ToDoCRUDService extends CRUDService<ToDo, Long> {
    List<ToDo> getAllToDosByBoardId(Long boardId);
    void deleteAllToDoByBoardId(Long boardId);
}
