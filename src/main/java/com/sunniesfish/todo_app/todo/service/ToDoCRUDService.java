package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.global.service.CRUDService;
import com.sunniesfish.todo_app.todo.entity.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoCRUDService extends CRUDService<ToDo, Long> {
    List<ToDo> getAllToDosByBoardId(Long boardId);
    Optional<ToDo> getLastTodoByBoardId(Long boardId);
    void deleteAllToDoByBoardId(Long boardId);
}
