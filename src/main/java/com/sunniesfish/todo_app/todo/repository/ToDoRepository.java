package com.sunniesfish.todo_app.todo.repository;

import com.sunniesfish.todo_app.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findByBoard_BoardIdOrderByOrderIndexAsc(Long boardId);
    List<ToDo> findByBoard_BoardId(Long boardId);
    void deleteAllByBoard_BoardId(Long boardId);
}
