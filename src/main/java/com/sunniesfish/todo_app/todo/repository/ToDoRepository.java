package com.sunniesfish.todo_app.todo.repository;

import com.sunniesfish.todo_app.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
